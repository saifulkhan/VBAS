package uk.ac.isc.processcommand;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;
import uk.ac.isc.hypodepthview.HypoDepthViewPanel;
import uk.ac.isc.hypooverview.HypoOverviewPanel2;
import uk.ac.isc.seisdata.VBASLogger;
import uk.ac.isc.seisdata.Hypocentre;
import uk.ac.isc.seisdata.HypocentresList;
import uk.ac.isc.seisdata.PhasesList;
import uk.ac.isc.seisdata.SeisEvent;
// NOTE: Required to avoid compilation error.
import org.openstreetmap.gui.jmapviewer.interfaces.TileLoaderListener;
import uk.ac.isc.agencypiechartview.AgencyPieChartView;
import uk.ac.isc.hypomagnitudeview.HypoMagnitudeViewPanel;
import uk.ac.isc.hypooverview.OverviewControlPanel3;
import uk.ac.isc.phaseview.PhaseTravelViewPanel;
import uk.ac.isc.seisdatainterface.Global;
import uk.ac.isc.seisdatainterface.SeisDataDAO;
import uk.ac.isc.stationazimuthview.StationAzimuthView;
import uk.ac.isc.stationmagnitudeview.StationMagnitudeView;
import uk.ac.isc.textview.HypocentreTableModel;
import uk.ac.isc.textview.PhaseTextViewTableModel;

public class GenerateReport {

    private static final SeisEvent selectedSeisEvent = Global.getSelectedSeisEvent();
    // New (relocator generated) Hypocentre & Phase data for the selected SeisEvent.
    private final HypocentresList hypocentresList = new HypocentresList();
    private final PhasesList phasesList = new PhasesList();
    private final TreeMap<String, String> stations = new TreeMap<String, String>();

    private static final String[] tables = {"hypocentre_table", "phase_table"};
    private static final String[] views = {"hypocentre_siesmicity",
        "phase_travel_time",
        "hypocentre_depths",
        "hypocentre_magnitudes",
        "station_geometry",
        "station_magnitudes",
        "agency_summary"};

    private final Path assessDir;
    private final int assessID;
    private final String cmd1, cmd2;
    private final Boolean isAssess;

    private final JDialog popupDialog;
    private File htmlFile;

    public GenerateReport(Path assessDir, int assessID, String cmd1, String cmd2, Boolean isAssess) {

        VBASLogger.logDebug("assessDir: " + assessDir + ", assessID:" + assessID);
        this.assessDir = assessDir;
        this.assessID = assessID;
        this.cmd1 = cmd1;
        this.cmd2 = cmd2;
        this.isAssess = isAssess;
                
        popupDialog = new JDialog();
        popupDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popupDialog.setModal(true);
        popupDialog.setLayout(new BorderLayout());

        this.readData();
        this.createHTML();
        this.createTables();
        this.createViews();
        this.logCommands();
    }

    
    private void readData() {

        System.out.println(VBASLogger.debugAt() + "Load list of Hypocentre and Phase for SeisEvent: "
                + selectedSeisEvent.getEvid());

        
        /*Hypocentre*/
        
        SeisDataDAO.retrieveHypos(selectedSeisEvent.getEvid(), hypocentresList.getHypocentres(), isAssess);
        SeisDataDAO.retrieveHyposMagnitude(hypocentresList.getHypocentres(), isAssess);
        // as I remove all the hypos when clicking an event to retrieve the hypos,
        // so need reset prime hypo every time
        // TODO: Saiful, What is this?
        for (Hypocentre hypo : hypocentresList.getHypocentres()) {
            if (hypo.getIsPrime() == true) {
                selectedSeisEvent.setPrimeHypo(hypo);
            }
        }

        
        /* Phase */
        SeisDataDAO.retrieveAllPhases(selectedSeisEvent.getEvid(), phasesList.getPhases(), isAssess);
        SeisDataDAO.retrieveAllPhasesAmpMag(selectedSeisEvent.getEvid(), phasesList.getPhases(), isAssess);
        SeisDataDAO.retrieveAllStationsWithRegions(stations, isAssess);
        // load the correspondent map into the stataions
        // put the region name into the pahseList
        for (int i = 0; i < phasesList.getPhases().size(); i++) {
            phasesList.getPhases()
                    .get(i)
                    .setRegionName(stations
                            .get(phasesList
                                    .getPhases()
                                    .get(i)
                                    .getReportStation()));
        }

        VBASLogger.logDebug("SeisEvent=" + selectedSeisEvent.getEvid()
                + ", #Hypocentres:" + hypocentresList.getHypocentres().size()
                + ", #Phases:" + phasesList.getPhases().size());
    }

    private File createHTML() {

        // Read the html file- a resourse file inside jar.
        if (getClass().getClassLoader().getResource("resources" + File.separator + "index.html") == null) {
            VBASLogger.logDebug("Resource does not exist. resource: "
                    + getClass().getClassLoader().getResource("resources" + File.separator + "index.html"));
        }

        InputStream inSream = getClass().getClassLoader().getResourceAsStream("resources"
                + File.separator
                + "index.html");

        if (inSream != null) {
            // Create a directory in the current directory and copy the content of the html schema in the resource folder.
            try {
                if (!new File(assessDir.toString()).exists()) {
                    boolean success = (new File(assessDir.toString())).mkdirs();
                    if (!success) {
                        String message = "Error creating " + assessDir + " directory.";
                        VBASLogger.logSevere(message);
                        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                htmlFile = new File(assessDir + File.separator + assessID + ".html");
                VBASLogger.logDebug("Assess (html) report = " + htmlFile.toPath());
                Files.copy(inSream, htmlFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                inSream.close();

                if (htmlFile.exists()) {
                    htmlFile.setReadable(true, true);
                    htmlFile.setWritable(true, true);
                }

            } catch (IOException e) {
                // TODO
            }
        } else {
            VBASLogger.logDebug("Null 'inStream', resource: "
                    + getClass().getClassLoader().getResource("resources" + File.separator + "ttimes.pl").toString());
        }

        return htmlFile;
    }

    private void createTables() {

        File hypocentresCSV = new File(assessDir + File.separator + "hypocentres.csv");
        File phasesCSV = new File(assessDir + File.separator + "phases.csv");

        VBASLogger.logDebug("hypocentresCSV: " + hypocentresCSV.toString() + ", phasesCSV: " + phasesCSV);

        FileWriter fileWritter = null;
        BufferedWriter bufferedWriter = null;

        try {
            // if the file doesnt exists, then create it
            if (!hypocentresCSV.exists()) {
                hypocentresCSV.createNewFile();
                hypocentresCSV.setReadable(true, false);
                hypocentresCSV.setWritable(true, false);
            }
            if (!phasesCSV.exists()) {
                phasesCSV.createNewFile();
                phasesCSV.setReadable(true, false);
                phasesCSV.setWritable(true, false);
            }

            for (String table : tables) {
                VBASLogger.logDebug(table);

                try {
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                } catch (IOException e) {
                    VBASLogger.logSevere("Error releasing resources.");
                    e.printStackTrace();
                }

                AbstractTableModel model = null;
                switch (table) {
                    case "hypocentre_table":
                        fileWritter = new FileWriter(hypocentresCSV, false);
                        bufferedWriter = new BufferedWriter(fileWritter);
                        model = new HypocentreTableModel(hypocentresList.getHypocentres());
                        break;

                    case "phase_table":
                        fileWritter = new FileWriter(phasesCSV, false);
                        bufferedWriter = new BufferedWriter(fileWritter);
                        model = new PhaseTextViewTableModel(phasesList.getPhases());
                        break;
                }

                // write column names
                for (int c = 0; c < model.getColumnCount(); ++c) {
                    bufferedWriter.write(model.getColumnName(c));
                    if (c != model.getColumnCount() - 1) {
                        bufferedWriter.write(",");
                    }
                }
                // write rows or column data 
                for (int r = 0; r < model.getRowCount(); ++r) {
                    bufferedWriter.newLine();
                    for (int c = 0; c < model.getColumnCount(); ++c) {
                        bufferedWriter.write(model.getValueAt(r, c) == null
                                ? "" : model.getValueAt(r, c).toString());

                        if (c != model.getColumnCount() - 1) {
                            bufferedWriter.write(",");
                        }
                    }
                }
            }

            VBASLogger.logDebug("Complete...");

        } catch (IOException e) {
            VBASLogger.logSevere("Error writing to csv file.");
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                VBASLogger.logSevere("Error releasing resources.");
                e.printStackTrace();
            }
        }
    }

    private void createViews() {

        VBASLogger.logDebug("Assessed Data: "
                + "#Hypocentres=" + hypocentresList.getHypocentres().size()
                + "#Phases=" + phasesList.getPhases().size());

        /*
         * Generate the PNG files
         */
        for (final String view : views) {
            VBASLogger.logDebug("Generating: " + view + ".png");

            switch (view) {
                case "hypocentre_siesmicity":
                    HypoOverviewPanel2 hop = new HypoOverviewPanel2(hypocentresList);
                    OverviewControlPanel3 controlPanel = new OverviewControlPanel3(hop);
                    genetarePNG(view, hop, hop.getMapWidth(), hop.getMapHeight());
                    break;

                case "phase_travel_time":
                    PhaseTravelViewPanel phaseTVPanel = new PhaseTravelViewPanel(phasesList, hypocentresList);
                    genetarePNG("phase_travel_time", phaseTVPanel, phaseTVPanel.getImageWidth(), phaseTVPanel.getImageHeight());
                    //PhaseDetailViewPanel phaseDVPanel = new PhaseDetailViewPanel(phaseTVPanel);
                    //genetarePNG("PhaseDetailView", phaseDVPanel, phaseDVPanel.getWidth(), phaseDVPanel.getHeight());
                    break;

                case "hypocentre_depths":
                    HypoDepthViewPanel hdv = new HypoDepthViewPanel(hypocentresList.getHypocentres());
                    genetarePNG(view, hdv, hdv.getViewWidth(), hdv.getViewHeight());
                    break;

                case "hypocentre_magnitudes":
                    HypoMagnitudeViewPanel hmag = new HypoMagnitudeViewPanel(hypocentresList.getHypocentres());
                    genetarePNG(view, hmag, hmag.getViewWidth() * 2, hmag.getViewHeight());
                    break;

                case "station_geometry":
                    StationAzimuthView saView = new StationAzimuthView(hypocentresList, phasesList, isAssess);
                    genetarePNG(view, saView, saView.getViewWidth(), saView.getViewHeight());
                    break;

                case "station_magnitudes":
                    StationMagnitudeView smView = new StationMagnitudeView(hypocentresList, isAssess);
                    genetarePNG(view, smView, smView.getViewWidth(), smView.getViewHeight());
                    break;

                case "agency_summary":
                    AgencyPieChartView apcView = new AgencyPieChartView();
                    apcView.setData(phasesList);
                    genetarePNG(view, apcView, apcView.getViewWidth(), apcView.getViewHeight());
                    break;
            }

        }

        popupDialog.dispose();
    }

    private void genetarePNG(final String view, final JPanel panel, final int width, final int height) {

        popupDialog.setPreferredSize(new Dimension(width + 40, height + 40));
        popupDialog.add(panel, BorderLayout.CENTER);
        popupDialog.pack();

        // Note: see tutorial
        // http://stackoverflow.com/questions/1306868/can-i-set-a-timer-on-a-java-swing-jdialog-box-to-close-after-a-number-of-millise
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                popupDialog.remove(panel);
                popupDialog.setVisible(false);
            }
        });
        timer.setRepeats(false);
        timer.start();
        popupDialog.setVisible(true);

        File outputFile = new File(assessDir + File.separator + view + ".png");
        BufferedImage bi = null;

        switch (view) {
            case "hypocentre_siesmicity":
                HypoOverviewPanel2 hop = (HypoOverviewPanel2) panel;
                bi = hop.getBufferedImage();
                break;

            case "phase_travel_time":
                PhaseTravelViewPanel phaseTVPanel = (PhaseTravelViewPanel) panel;
                bi = phaseTVPanel.getBufferedImage();
                break;

            case "hypocentre_depths":
                HypoDepthViewPanel hdp = (HypoDepthViewPanel) panel;
                bi = hdp.getDepthHistImg();
                break;

            case "hypocentre_magnitudes":
                HypoMagnitudeViewPanel hmag = (HypoMagnitudeViewPanel) panel;
                bi = hmag.getBufferedImage();
                break;

            case "station_geometry":
                StationAzimuthView saView = (StationAzimuthView) panel;
                bi = saView.getBufferedImage();
                break;

            case "station_magnitudes":
                StationMagnitudeView smView = (StationMagnitudeView) panel;
                bi = smView.getBufferedImage();
                break;

            case "agency_summary":
                AgencyPieChartView apcView = (AgencyPieChartView) panel;
                bi = apcView.getBufferedImage();
                break;
        }

        try {
            ImageIO.write(bi, "png", outputFile);
            if (outputFile.exists()) {
                outputFile.setReadable(true, false);
                outputFile.setWritable(true, false);
            }
        } catch (Exception e) {
            VBASLogger.logSevere("Error creating a png file: " + outputFile.toString());
            e.printStackTrace();
        }

    }

    private void logCommands() {

        File file = new File(assessDir + File.separator + "analystRedableCommand.txt");

        FileWriter fileWritter = null;
        BufferedWriter bufferedWriter = null;

        try {
            // if the file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
                file.setReadable(true, false);
                file.setWritable(true, false);
            }

            fileWritter = new FileWriter(file, false);
            bufferedWriter = new BufferedWriter(fileWritter);
            bufferedWriter.write(cmd1);

        } catch (IOException e) {
            VBASLogger.logSevere("Error writing to json file.");
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                VBASLogger.logSevere("Error releasing resources.");
                e.printStackTrace();
            }
        }

        File cmdJson = new File(assessDir + File.separator + "systemCommand.json");

        try {
            // if the file doesnt exists, then create it
            if (!cmdJson.exists()) {
                cmdJson.createNewFile();
                cmdJson.setReadable(true, false);
                cmdJson.setWritable(true, false);
            }

            fileWritter = new FileWriter(cmdJson, false);
            bufferedWriter = new BufferedWriter(fileWritter);
            bufferedWriter.write(cmd2);

        } catch (IOException e) {
            VBASLogger.logSevere("Error writing to json file.");
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                VBASLogger.logSevere("Error releasing resources.");
                e.printStackTrace();
            }
        }
        
        
        File fileLocatorMsg = new File(assessDir + File.separator + "locatorMsg.txt");

             try {
            // if the file doesnt exists, then create it
            if (!fileLocatorMsg.exists()) {
                fileLocatorMsg.createNewFile();
                fileLocatorMsg.setReadable(true, false);
                fileLocatorMsg.setWritable(true, false);
            }

            fileWritter = new FileWriter(fileLocatorMsg, false);
            bufferedWriter = new BufferedWriter(fileWritter);
            bufferedWriter.write("Locator Message:<br/>" + selectedSeisEvent.getLocatorMessage() + "<br/>");

        } catch (IOException e) {
            VBASLogger.logSevere("Error writing to json file.");
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                VBASLogger.logSevere("Error releasing resources.");
                e.printStackTrace();
            }
        }
      
    }
}
