package uk.ac.isc.phaseview;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import javax.swing.JPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Second;
import uk.ac.isc.seisdata.Phase;
import uk.ac.isc.seisdata.PhasesList;
import uk.ac.isc.seisdata.SeisDataChangeEvent;
import uk.ac.isc.seisdata.SeisDataChangeListener;
import uk.ac.isc.seisdata.VBASLogger;

/**
 * PhaseDetailView is the right panel for showing the zoom-in version of the
 * phase view
 *
 */
public class PhaseDetailViewPanel extends JPanel implements SeisDataChangeListener {

    private final int imageWidth = 500;
    private final int imageHeight = 1000;

    private final PhasesList detailPList;

    //reference of travel view in order to get range information 
    private final PhaseTravelViewPanel phaseTVPanel;

    private double residualCutoffLevel = 0.0;

    private DuplicateUnorderTimeSeries detailPhaseSeries;
    private final DuplicateUnorderTimeSeriesCollection detailDataset = new DuplicateUnorderTimeSeriesCollection();

    private JFreeChart freechart = null;

    private BufferedImage phasesImage = null;

    private double zoomMinTime;
    private double zoomMaxTime;
    private double zoomMinDist;
    private double zoomMaxDist;

    //curve data
    private DuplicateUnorderTimeSeriesCollection ttdData = null;

    public PhaseDetailViewPanel(PhaseTravelViewPanel phaseTVPanel) {
        this.phaseTVPanel = phaseTVPanel;
        this.detailPList = phaseTVPanel.getDetailedPList();
        this.ttdData = phaseTVPanel.getTtdData();

        setPreferredSize(new Dimension(500, 1000));

        detailPhaseSeries = new DuplicateUnorderTimeSeries("");

        //put phases into the dataseries
        for (Phase p : detailPList.getPhases()) {
            if (p.getArrivalTime() != null)// && ((p.getTimeResidual()!=null && Math.abs(p.getTimeResidual())>residualCutoffLevel)||(p.getTimeResidual()==null)))
            {
                RegularTimePeriod rp = new Second(p.getArrivalTime());
                detailPhaseSeries.add(rp, p.getDistance());
            }
        }
        detailDataset.addSeries(detailPhaseSeries);

        detailPList.addChangeListener(this);

        setRange(phaseTVPanel.getRange());
        createTravelImage();
    }

    //repaint when the data changes
    @Override
    public void SeisDataChanged(SeisDataChangeEvent event) {
        updateData();
    }

    public void setRange(double[] range) {
        zoomMinTime = range[0];
        zoomMaxTime = range[1];
        zoomMinDist = range[2];
        zoomMaxDist = range[3];
    }

    public void updateData() {
        
       // Issue #43: Crashes when event 607202578 is selected
       // When there are no phase in the selcted SiesEvent just draw an empty image. 
       if(detailPList.getPhases().isEmpty()) {
            phasesImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
            repaint();
            return;
       }
       
        setRange(phaseTVPanel.getRange());

        //this.detailPList = pList;
        detailDataset.removeAllSeries();
        detailPhaseSeries = new DuplicateUnorderTimeSeries("");

        VBASLogger.logDebug("range=" + Arrays.toString(phaseTVPanel.getRange()) + ", #detailPList: " + detailPList.getPhases().size());
        //put phases into the dataseries
        if (detailPList != null) {
            for (Phase p : detailPList.getPhases()) {

                if (p.getArrivalTime() != null) // && ((p.getTimeResidual()!=null && Math.abs(p.getTimeResidual())>residualCutoffLevel)||(p.getTimeResidual()==null)))
                {
                    if (residualCutoffLevel == 0) {
                        RegularTimePeriod rp = new Second(p.getArrivalTime());
                        detailPhaseSeries.add(rp, p.getDistance());
                    } else {
                        if (p.getTimeResidual() != null) {
                            if (Math.abs(p.getTimeResidual()) > residualCutoffLevel) {
                                RegularTimePeriod rp = new Second(p.getArrivalTime());
                                detailPhaseSeries.add(rp, p.getDistance());
                            }
                        } else {
                            RegularTimePeriod rp = new Second(p.getArrivalTime());
                            detailPhaseSeries.add(rp, p.getDistance());
                        }
                    }
                }
            }
            //VBASLogger.logDebug("#detailPhaseSeries: " + detailPhaseSeries.);

            detailDataset.addSeries(detailPhaseSeries);
        }
        createTravelImage();
        repaint();
    }

    /**
     * the range won't change just filter the data
     */
    public void filterData() {
        Long minTime = detailPhaseSeries.getMinX();
        Long maxTime = detailPhaseSeries.getMaxX();
        double minDist = detailPhaseSeries.getMinY();
        double maxDist = detailPhaseSeries.getMaxY();

        detailDataset.removeAllSeries();
        detailPhaseSeries = new DuplicateUnorderTimeSeries("");

        //put phases into the dataseries
        for (Phase p : detailPList.getPhases()) {

            if (p.getArrivalTime() != null) {
                if (residualCutoffLevel == 0) {
                    RegularTimePeriod rp = new Second(p.getArrivalTime());
                    detailPhaseSeries.add(rp, p.getDistance());
                } else {
                    if (p.getTimeResidual() != null) {
                        if (Math.abs(p.getTimeResidual()) > residualCutoffLevel) {
                            RegularTimePeriod rp = new Second(p.getArrivalTime());
                            detailPhaseSeries.add(rp, p.getDistance());

                        }
                    } else //show phases with null residual
                    {
                        RegularTimePeriod rp = new Second(p.getArrivalTime());
                        detailPhaseSeries.add(rp, p.getDistance());
                    }
                }

            }
        }

        detailPhaseSeries.setMinX(minTime);
        detailPhaseSeries.setMaxX(maxTime);
        detailPhaseSeries.setMinY(minDist);
        detailPhaseSeries.setMaxY(maxDist);
        /*
         double firstTopTime = minTime + (maxTime - minTime) / pageNumber;
         Double firstTopDistance = minDist + (maxDist) / (double) pageNumber;
         for(Phase p:pList.getPhases())
         {
         if(p.getArrivalTime()!=null && (double)p.getArrivalTime().getTime()<firstTopTime && p.getDistance()<firstTopDistance)
         {
         detailedPList.getPhases().add(p);
         }
         }*/

        detailDataset.addSeries(detailPhaseSeries);
        VBASLogger.logDebug("detailPhaseSeries=" + detailPhaseSeries);

        createTravelImage();
        repaint();
    }

    public void setResidualCutoffLevel(double level) {
        this.residualCutoffLevel = level;
    }

    public double getResidualCutoffLevel() {
        return this.residualCutoffLevel;
    }

    /**
     * helper function to use jfreechart to generate the bufferedimage
     */
    private void createTravelImage() {
        //define first axis
        DateAxis timeAxis = new DateAxis("");
        VBASLogger.logDebug("zoomMinTime=" + zoomMinTime + ", zoomMaxTime=" + zoomMaxTime);
       
        timeAxis.setRange((double) (zoomMinTime - 2), (double) (zoomMaxTime + 2));
        timeAxis.setLowerMargin(0.02);  // reduce the default margins
        timeAxis.setUpperMargin(0.02);
        timeAxis.setDateFormatOverride(new SimpleDateFormat("HH:mm:ss"));

        NumberAxis valueAxis = new NumberAxis("");

        
        VBASLogger.logDebug("zoomMinDist=" + zoomMinDist + ", zoomMaxDist=" + zoomMaxDist);
        valueAxis.setRange(Math.max(0, zoomMinDist - 1), Math.min(180, zoomMaxDist + 1));
        XYDotRenderer xyDotRend = new DetailGlyphRenderer(detailPList.getPhases());//new XYDotRenderer();

        //xyDotRend.setDotWidth(6);
        //xyDotRend.setDotHeight(6);
        PhasesWithCurvePlot plot = new PhasesWithCurvePlot(detailDataset, timeAxis, valueAxis, xyDotRend, ttdData);
        //XYPlot plot = new XYPlot(detailDataset, timeAxis, valueAxis, xyDotRend);
        plot.setOrientation(PlotOrientation.HORIZONTAL);

        freechart = new JFreeChart(plot);

        freechart.removeLegend();

        phasesImage = freechart.createBufferedImage(imageWidth, imageHeight);
        // Saiful: Idea
        //backgroundImage = freechart.createBufferedImage(imageWidth, imageHeight);
        //phasesImage = backgroundImage.copy

    }

    //paint the detail view on the right side
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        int xOffset = Math.max((getWidth() - imageWidth) / 2, 0);
        int yOffset = Math.max((getHeight() - imageHeight) / 2, 0);

        g2.drawImage(phasesImage, xOffset, yOffset, this);

        /*// TEST: BufferedImage to png
         //Global.logDebug("Write BufferedImage.");
         try {
         ImageIO.write(phasesImage, "png",
         new File("/export/home/saiful/assess/temp/phaseDetailView.png"));
         } catch (Exception e) {
         Global.logSevere("Error creating a png.");
         }*/
    }

    public BufferedImage getBufferedImage() {
        return phasesImage;
    }

}
