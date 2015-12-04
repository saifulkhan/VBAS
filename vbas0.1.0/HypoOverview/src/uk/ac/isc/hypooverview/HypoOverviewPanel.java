package uk.ac.isc.hypooverview;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.jfree.text.TextUtilities;
import org.jfree.ui.TextAnchor;
import org.openide.util.Exceptions;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JobDispatcher;
import org.openstreetmap.gui.jmapviewer.MemoryTileCache;
import org.openstreetmap.gui.jmapviewer.OsmMercator;
import org.openstreetmap.gui.jmapviewer.Tile;
import org.openstreetmap.gui.jmapviewer.TileController;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.TileCache;
import org.openstreetmap.gui.jmapviewer.interfaces.TileLoaderListener;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OfflineOsmTileSource;
import uk.ac.isc.seisdata.ColorUtils;
import uk.ac.isc.seisdata.HistoricEvent;
import uk.ac.isc.seisdata.Hypocentre;
import uk.ac.isc.seisdata.HypocentresList;
import uk.ac.isc.seisdata.SeisDataDAO;
import uk.ac.isc.seisdata.SeisUtils;

/**
 * This the main map panel to show the seismicity, hypocentres and minimap for
 * the location
 *
 * @author hui
 * @deprecated
 */
public final class HypoOverviewPanel extends JPanel implements TileLoaderListener {

    //build a paint array for drawing with band colors
    public static final Paint[] seisPaints = ColorUtils.createOldSeismicityPaintArray();
    //public static final Paint[] seisNewPaints = ColorUtils.createSeismicityPaintArray2();
    public static final Paint[] seisNewPaints = ColorUtils.createSeismicityPaintArray3();
    //base map size
    private final int mapWidth = 900;
    private final int mapHeight = 900;

    //mini map size
    private final int miniWidth = 200;
    private final int miniHeight = 200;

    //tile controller for the base map
    private final TileController tileController;

    private final TileSource tileSource;

    //hypocentre lists + the range of the seismicity map
    private final HypocentresList hyposList;

    private double cenLat, cenLon;

    private int cenDepth;

    /**
     * Vectors for clock-wise tile painting
     */
    protected static final Point[] move = {new Point(1, 0), new Point(0, 1), new Point(-1, 0), new Point(0, -1)};

    public static final int MAX_ZOOM = 7;
    public static final int MIN_ZOOM = 0;

    protected boolean scrollWrapEnabled = true;

    /**
     * x- and y-position of the center of this map-panel on the world map
     * denoted in screen pixel regarding the current zoom level.
     */
    protected Point center;

    /**
     * Current zoom level
     */
    protected int zoom;

    /**
     * rangeDelta: it define the range for loading seismicity
     */
    private int rangeDelta = 8;

    /**
     * a depth cutoff value to show seismicity
     */
    private int depthCutoff = 8;

    private BufferedImage baseMap = new BufferedImage(mapWidth, mapHeight, BufferedImage.TYPE_INT_ARGB);

    /**
     * rawMiniMap for the mini base map miniMap labelled with current prime
     * hypocentre
     */
    private BufferedImage rawMiniMap = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);

    /**
     * mini map with the prime hypo on top to show the position of the event
     */
    private final BufferedImage miniMap = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);

    /**
     * seisList for loading the historic data to show the seismicity map
     */
    ArrayList<HistoricEvent> seisList;

    /**
     * Border attributes: border width
     *
     */
    int borderWidth = 12;

    int textToBorder = 30;

    //longitude is allowed to smaller than -180 or bigger than 180, we norm it when annotating it
    //annotation range
    double latLow, latHigh, lonLeft, lonRight;

    int latAnnotNumber, lonAnnotNumber;
    /**
     * Flags to show different features, i.e. Seismicity, Mini Map, Hypocentres,
     * reversed Seismicity, Border and force to fit all features
     */
    private boolean showSeismicity = true;

    private boolean showMiniMap = true;

    private boolean showHypos = true;

    private boolean seisReversed = false;

    private boolean showBorder = true;

    private boolean forceFit = false;

    public HypoOverviewPanel(HypocentresList hyposList) {

        //new DefaultMapController(this);
        JobDispatcher.setMaxWorkers(2);

        //tileSource = new OsmTileSource.Mapnik();  
        tileSource = new OfflineOsmTileSource("file:/export/home/hui/perl", 0, 7);
        tileController = new TileController(tileSource, new MemoryTileCache(), this);
        tileController.setTileSource(tileSource);

        //setMinimumSize(new Dimension(tileSource.getTileSize(), tileSource.getTileSize()));
        //setPreferredSize(new Dimension(mapWidth, mapHeight));
        //this.setIgnoreRepaint(true);
        this.hyposList = hyposList;

        //there must be one prime, so center won't be null
        for (Hypocentre hypo : hyposList.getHypocentres()) {
            if (hypo.getIsPrime() == true) {
                this.cenLat = hypo.getLat();
                this.cenLon = hypo.getLon();
                this.cenDepth = hypo.getDepth();
            }
        }

        //initialise the array for saving seismicity data
        seisList = new ArrayList<HistoricEvent>();

        loadSeisData(this.cenLat, this.cenLon, this.rangeDelta);

        try {
            rawMiniMap = ImageIO.read(new File("/export/home/hui/perl/0/0/0.png"));
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        repaint();
        //try border
        //this.setBorder(BorderFactory.createEtchedBorder(1, Color.lightGray, Color.yellow));        
    }

    public void setRangeDelta(int rangeDelta) {
        this.rangeDelta = rangeDelta;
    }

    public int getRangeDelta() {
        return this.rangeDelta;
    }

    public void setDepthCutOff(int cutoff) {
        this.depthCutoff = cutoff;
        this.repaint();
    }

    public int getDepthCutOff() {
        return this.depthCutoff;
    }

    public void setShowSeismicity(boolean showSeismicity) {
        this.showSeismicity = showSeismicity;
        this.repaint();
    }

    public boolean getShowSeismicity() {
        return this.showSeismicity;
    }

    public void setShowMiniMap(boolean showMiniMap) {
        this.showMiniMap = showMiniMap;
        this.repaint();
    }

    public boolean getShowMiniMap() {
        return this.showMiniMap;
    }

    public void setShowHypos(boolean showHypos) {
        this.showHypos = showHypos;
        this.repaint();
    }

    public boolean getShowHypos() {
        return this.showHypos;
    }

    public void setSeisReversed(boolean seisReversed) {
        this.seisReversed = seisReversed;

        this.repaint();
    }

    public boolean getSeisReversed() {
        return this.seisReversed;
    }

    public void setShowBorder(boolean showBorder) {
        this.showBorder = showBorder;
        this.repaint();
    }

    public boolean getShowBorder() {
        return this.showBorder;
    }

    public void setForceFit(boolean forceFit) {
        this.forceFit = forceFit;
        this.repaint();
    }

    public boolean getForceFit() {
        return this.forceFit;
    }

    public void loadSeisData(double cenLat, double cenLon, double rangeDelta) {

        getZoomToFitSeis(cenLat, cenLon, rangeDelta); //get zoom level and save it in the zoom
        setDisplayPosition(new Coordinate(cenLat, cenLon), this.zoom);
        calculateAnnotation();
        /**
         * get the seismicity data based on the visible range from zoom level
         */
        SeisDataDAO.retrieveHistEvents(seisList, latHigh, latLow, lonLeft, lonRight);
    }

    /**
     * Changes the map pane so that it is centered on the specified coordinate
     * at the given zoom level.
     *
     * @param to specified coordinate
     * @param zoom {@link #MIN_ZOOM} &lt;= zoom level &lt;= {@link #MAX_ZOOM}
     */
    public void setDisplayPosition(Coordinate to, int zoom) {
        //setDisplayPosition(new Point(getWidth() / 2, getHeight() / 2), to, zoom);
        setDisplayPosition(new Point(mapWidth / 2, mapHeight / 2), to, zoom);
    }

    /**
     * Changes the map pane so that the specified coordinate at the given zoom
     * level is displayed on the map at the screen coordinate
     * <code>mapPoint</code>.
     *
     * @param mapPoint point on the map denoted in pixels where the coordinate
     * should be set
     * @param to specified coordinate
     * @param zoom {@link #MIN_ZOOM} &lt;= zoom level &lt;=
     * {@link TileSource#getMaxZoom()}
     */
    public void setDisplayPosition(Point mapPoint, Coordinate to, int zoom) {
        int x = tileSource.LonToX(to.getLon(), zoom);
        int y = tileSource.LatToY(to.getLat(), zoom);
        setDisplayPosition(mapPoint, x, y, zoom);
    }

    public void setDisplayPosition(int x, int y, int zoom) {
        //setDisplayPosition(new Point(getWidth() / 2, getHeight() / 2), x, y, zoom);
        setDisplayPosition(new Point(mapWidth / 2, mapHeight / 2), x, y, zoom);
    }

    public void setDisplayPosition(Point mapPoint, int x, int y, int zoom) {
        if (zoom > tileController.getTileSource().getMaxZoom() || zoom < MIN_ZOOM) {
            return;
        }

        // Get the plain tile number
        Point p = new Point();
        //p.x = x - mapPoint.x + getWidth() / 2;
        p.x = x - mapPoint.x + mapWidth / 2;
        //p.y = y - mapPoint.y + getHeight() / 2;
        p.y = y - mapPoint.y + mapHeight / 2;
        center = p;
        setIgnoreRepaint(true);
        try {
            int oldZoom = this.zoom;
            this.zoom = zoom;
            //if (oldZoom != zoom) {
            //    zoomChanged(oldZoom);
            //}
            //if (zoomSlider.getValue() != zoom) {
            //    zoomSlider.setValue(zoom);
            //}
        } finally {
            //setIgnoreRepaint(true);
            repaint();
        }
    }

    /**
     * Sets the displayed map pane and zoom level so that all chosen map
     * elements are visible.
     */
    public void setDisplayToFitMapElements(boolean hypos, boolean seis) {

        int x_min = Integer.MAX_VALUE;
        int y_min = Integer.MAX_VALUE;
        int x_max = Integer.MIN_VALUE;
        int y_max = Integer.MIN_VALUE;
        int mapZoomMax = tileController.getTileSource().getMaxZoom();

        /*if (markers) {
         for (MapMarker marker : mapMarkerList) {
         if(marker.isVisible()){
         int x = tileSource.LonToX(marker.getLon(), mapZoomMax);
         int y = tileSource.LatToY(marker.getLat(), mapZoomMax);
         x_max = Math.max(x_max, x);
         y_max = Math.max(y_max, y);
         x_min = Math.min(x_min, x);
         y_min = Math.min(y_min, y);
         }
         }
         }

         if (rectangles) {
         for (MapRectangle rectangle : mapRectangleList) {
         if(rectangle.isVisible()){
         x_max = Math.max(x_max, tileSource.LonToX(rectangle.getBottomRight().getLon(), mapZoomMax));
         y_max = Math.max(y_max, tileSource.LatToY(rectangle.getTopLeft().getLat(), mapZoomMax));
         x_min = Math.min(x_min, tileSource.LonToX(rectangle.getTopLeft().getLon(), mapZoomMax));
         y_min = Math.min(y_min, tileSource.LatToY(rectangle.getBottomRight().getLat(), mapZoomMax));
         }
         }
         }

         if (polygons) {
         for (MapPolygon polygon : mapPolygonList) {
         if(polygon.isVisible()){
         for (ICoordinate c : polygon.getPoints()) {
         int x = tileSource.LonToX(c.getLon(), mapZoomMax);
         int y = tileSource.LatToY(c.getLat(), mapZoomMax);
         x_max = Math.max(x_max, x);
         y_max = Math.max(y_max, y);
         x_min = Math.min(x_min, x);
         y_min = Math.min(y_min, y);
         }
         }
         }
         }*/
        int height = Math.max(0, getHeight());
        int width = Math.max(0, getWidth());
        int newZoom = mapZoomMax;
        int x = x_max - x_min;
        int y = y_max - y_min;
        while (x > width || y > height) {
            newZoom--;
            x >>= 1;
            y >>= 1;
        }
        x = x_min + (x_max - x_min) / 2;
        y = y_min + (y_max - y_min) / 2;
        int z = 1 << (mapZoomMax - newZoom);
        x /= z;
        y /= z;
        setDisplayPosition(x, y, newZoom);
    }

    /**
     * Sets the displayed map pane and zoom level so that all map markers are
     * visible.
     */
    public void setDisplayToFitHypos() {
        setDisplayToFitMapElements(true, false);
    }

    /**
     * Sets the displayed map pane and zoom level so that all map rectangles are
     * visible.
     */
    public void setDisplayToFitSeismicity() {
        setDisplayToFitMapElements(false, true);
    }

    /**
     * Sets the displayed map pane and zoom level so that all map polygons are
     * visible.
     */
    public void setDisplayToFitAll() {
        setDisplayToFitMapElements(true, true);
    }

    /**
     * @return the center
     */
    public Point getCenter() {
        return center;
    }

    /**
     * @param center the center to set
     */
    public void setCenter(Point center) {
        this.center = center;
    }

    public void setCentLatLon(double lat, double lon) {
        this.cenLat = lat;
        this.cenLon = lon;
    }

    public void setCentDepth(int depth) {
        this.cenDepth = depth;
    }

    /**
     * Calculates the latitude/longitude coordinate of the center of the
     * currently displayed map area.
     *
     * @return latitude / longitude
     */
    public Coordinate getPosition() {
        double lon = tileSource.XToLon(center.x, zoom);
        double lat = tileSource.YToLat(center.y, zoom);
        return new Coordinate(lat, lon);
    }

    /**
     * Converts the relative pixel coordinate (regarding the top left corner of
     * the displayed map) into a latitude / longitude coordinate
     *
     * @param mapPoint relative pixel coordinate regarding the top left corner
     * of the displayed map
     * @return latitude / longitude
     */
    public Coordinate getPosition(Point mapPoint) {
        return getPosition(mapPoint.x, mapPoint.y);
    }

    /**
     * Converts the relative pixel coordinate (regarding the top left corner of
     * the displayed map) into a latitude / longitude coordinate
     *
     * @param mapPointX
     * @param mapPointY
     * @return latitude / longitude
     */
    public Coordinate getPosition(int mapPointX, int mapPointY) {
        //int x = center.x + mapPointX - getWidth() / 2;
        //int y = center.y + mapPointY - getHeight() / 2;
        int x = center.x + mapPointX - mapWidth / 2;
        int y = center.y + mapPointY - mapHeight / 2;

        double lon = tileSource.XToLon(x, zoom);
        double lat = tileSource.YToLat(y, zoom);
        return new Coordinate(lat, lon);
    }

    /**
     * Calculates the position on the map of a given coordinate
     *
     * @param lat
     * @param lon
     * @param checkOutside
     * @return point on the map or <code>null</code> if the point is not visible
     * and checkOutside set to <code>true</code>
     */
    public Point getMapPosition(double lat, double lon, boolean checkOutside) {
        int x = tileSource.LonToX(lon, zoom);
        int y = tileSource.LatToY(lat, zoom);
        //x -= center.x - getWidth() / 2;
        //y -= center.y - getHeight() / 2;
        //if (checkOutside) {
        //    if (x < 0 || y < 0 || x > getWidth() || y > getHeight())
        //        return null;
        //}
        x -= center.x - mapWidth / 2;
        y -= center.y - mapHeight / 2;
        if (checkOutside) {
            if (x < 0 || y < 0 || x > mapWidth || y > mapHeight) {
                return null;
            }
        }

        return new Point(x, y);
    }

    /**
     * Calculates the position on the map of a given coordinate
     *
     * @param lat Latitude
     * @param offset Offset respect Latitude
     * @param checkOutside
     * @return Integer the radius in pixels
     */
    public Integer getLatOffset(double lat, double offset, boolean checkOutside) {
        int y = tileSource.LatToY(lat + offset, zoom);
        //y -= center.y - getHeight() / 2;
        //if (checkOutside) {
        //    if (y < 0 || y > getHeight())
        //        return null;
        //}
        y -= center.y - mapHeight / 2;
        if (checkOutside) {
            if (y < 0 || y > mapHeight) {
                return null;
            }
        }
        return y;
    }

    /**
     * Calculates the position on the map of a given coordinate
     *
     * @param lat
     * @param lon
     * @return point on the map or <code>null</code> if the point is not visible
     */
    public Point getMapPosition(double lat, double lon) {
        return getMapPosition(lat, lon, true);
    }

    /**
     * Calculates the position on the map of a given coordinate
     *
     * @param coord
     * @return point on the map or <code>null</code> if the point is not visible
     */
    public Point getMapPosition(Coordinate coord) {
        if (coord != null) {
            return getMapPosition(coord.getLat(), coord.getLon());
        } else {
            return null;
        }
    }

    /**
     * Calculates the position on the map of a given coordinate
     *
     * @param coord
     * @return point on the map or <code>null</code> if the point is not visible
     * and checkOutside set to <code>true</code>
     */
    public Point getMapPosition(ICoordinate coord, boolean checkOutside) {
        if (coord != null) {
            return getMapPosition(coord.getLat(), coord.getLon(), checkOutside);
        } else {
            return null;
        }
    }

    /**
     * Get the zoom level to fit all the seismicity visible.
     */
    private void getZoomToFitSeis(double cenLat, double cenLon, double rangeDelta) {

        int mapZoomMax = tileController.getTileSource().getMaxZoom();

        //get the seis region
        //double latN = cenLat + rangeDelta;
        //double latS = cenLat - rangeDelta;
        //double lonW = SeisUtils.LonFromAziDelta(cenLat, cenLon, 270, rangeDelta);
        //double lonE = SeisUtils.LonFromAziDelta(cenLat, cenLon, 90, rangeDelta);
        int x_min = tileSource.LonToX(SeisUtils.LonFromAziDelta(cenLat, cenLon, 270, rangeDelta), mapZoomMax);
        int y_min = tileSource.LatToY(cenLat + rangeDelta, mapZoomMax);
        int x_max = tileSource.LonToX(SeisUtils.LonFromAziDelta(cenLat, cenLon, 90, rangeDelta), mapZoomMax);
        int y_max = tileSource.LatToY(cenLat - rangeDelta, mapZoomMax);

        //int height = Math.max(0, getHeight());
        //int width = Math.max(0, getWidth());
        //should be changed to the size of map window
        int height = mapHeight;
        int width = mapWidth;
        int newZoom = mapZoomMax;
        int x = x_max - x_min;
        int y = y_max - y_min;
        while (x > width || y > height) {
            newZoom--;
            x >>= 1;
            y >>= 1;
        }
        x = x_min + (x_max - x_min) / 2;
        y = y_min + (y_max - y_min) / 2;
        int z = 1 << (mapZoomMax - newZoom);
        x /= z;
        y /= z;

        /**
         * make it in a detailed level instead of showing all the seismicity
         * data
         */
        newZoom = Math.min(newZoom + 1, mapZoomMax);
        this.zoom = newZoom;

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        int xOffset = (getWidth() - mapWidth) / 2;
        int yOffset = (getHeight() - mapHeight) / 2;

        //draw the base map, seismicity map and hypocentres
        drawBaseMap();
        g2.drawImage(baseMap, xOffset, yOffset, mapWidth, mapHeight, this);

        //draw mini map
        int miniXOffset = 0, miniYOffset = 0;
        if (showMiniMap == true) {
            //minimap on the right side
            if (getWidth() > mapWidth) {
                miniXOffset = Math.max(0, (getWidth() + mapWidth) / 2 - miniWidth);
            } else {
                miniXOffset = Math.max(0, getWidth() - miniWidth);
            }

            //minimap at the top
            if (getHeight() > mapHeight) {
                miniYOffset = (getHeight() - mapHeight) / 2;
            } else {
                miniYOffset = 0;
            }
            //minimap at the bottom
            /*if(getHeight()>panelHeight)
             {
             miniYOffset = Math.max(0,(int)(getHeight()+panelHeight)/2 - miniHeight);
             }
             else
             {
             miniYOffset = Math.max(0,getHeight()-miniHeight);
             }*/
            drawMiniMap();
            g2.drawImage(miniMap, miniXOffset, miniYOffset, miniWidth, miniHeight, this);
        }

        if (showBorder == true) {
            Stroke savedStroke = g2.getStroke();
            Font savedFont = g2.getFont();

            //drawing minimap border
            if (showMiniMap == true) {
                g2.setStroke(new BasicStroke(3));
                g2.drawRect(miniXOffset, miniYOffset, miniWidth, miniHeight);
            }

            //drawing the big border, if the space is smaller, just draw simple black border
            //one for border one for annotation
            if (((getWidth() - mapWidth) / 2 > 2 * borderWidth)) {
                g2.setStroke(new BasicStroke(3));
                if ((getHeight() - mapHeight) / 2 > 0) {
                    //left
                    g2.drawRect((getWidth() - mapWidth) / 2 - borderWidth, (getHeight() - mapHeight) / 2, borderWidth, mapHeight);
                    //right
                    g2.drawRect((getWidth() + mapWidth) / 2, (getHeight() - mapHeight) / 2, borderWidth, mapHeight);
                } else {
                    //left
                    g2.drawRect((getWidth() - mapWidth) / 2 - borderWidth, 0, borderWidth, getHeight());
                    //right
                    g2.drawRect((getWidth() + mapWidth) / 2, 0, borderWidth, getHeight());
                }
            }

            if (((getHeight() - mapHeight) / 2 > 2 * borderWidth)) {
                g2.setStroke(new BasicStroke(3));
                if ((getWidth() - mapWidth) / 2 > 0) {
                    //top
                    g2.drawRect((getWidth() - mapWidth) / 2, (getHeight() - mapHeight) / 2 - borderWidth, mapWidth, borderWidth);
                    //bottom
                    g2.drawRect((getWidth() - mapWidth) / 2, (getHeight() + mapHeight) / 2, mapWidth, borderWidth);
                } else {
                    //top
                    g2.drawRect(0, (getHeight() - mapHeight) / 2 - borderWidth, getHeight(), borderWidth);
                    //bottom
                    g2.drawRect(0, (getHeight() + mapHeight) / 2, getHeight(), borderWidth);
                }
            }

            //calculate the boundary lat and lon for annotation
            //calculateAnnotation(); //results are saved in latLow, latHigh, lonLeft and lonRight
            //double[] annLatRatio = new double[latAnnotNumber];
            double[] annLonRatio = new double[lonAnnotNumber];

            int labelY, barYPos;
            int count;

            if ((getHeight() - mapHeight) > 0) //set map between latitude labels and border ratio 
            {
                for (int i = (int) Math.round(latLow + 0.5); i <= (int) latHigh; i += 4) {

                    labelY = (int) (getHeight() / 2 + (tileSource.LatToY((double) i, zoom) - center.y));
                    if ((i + 2) < latHigh) {
                        barYPos = (int) (getHeight() / 2 + (tileSource.LatToY((double) (i + 2), zoom) - center.y));
                    } else {
                        barYPos = (int) (getHeight() / 2 + (tileSource.LatToY(latHigh, zoom) - center.y));
                    }

                        //draw the rail rectangle
                    //left
                    g2.fillRect((getWidth() - mapWidth) / 2 - borderWidth, barYPos,
                            borderWidth, labelY - barYPos);
                    //right
                    g2.fillRect((getWidth() + mapWidth) / 2, barYPos,
                            borderWidth, labelY - barYPos);

                    //label the latitude
                    int labelXLeft = (getWidth() - mapWidth) / 2 - borderWidth - textToBorder;
                    int labelXRight = (getWidth() + mapWidth) / 2 + borderWidth + textToBorder;
                    //int labelY = (int)((getHeight()-mapHeight)/2 +(1-annLatRatio[count-2])*mapHeight);
                    String label;
                    if (i >= 0) {
                        label = ((Integer) (i)).toString() + "N";
                    } else {
                        label = ((Integer) Math.abs(i)).toString() + "S";
                    }

                    //set font
                    g2.setFont(new Font("SansSerif", Font.BOLD, 16));
                    if ((getWidth() - mapWidth) / 2 >= (borderWidth + textToBorder * 2)) {
                        TextUtilities.drawRotatedString(label, g2, labelXLeft, labelY, TextAnchor.CENTER, 0, TextAnchor.CENTER);
                        TextUtilities.drawRotatedString(label, g2, labelXRight, labelY, TextAnchor.CENTER, 0, TextAnchor.CENTER);
                    }

                }

            }

            if ((getWidth() - mapWidth) > 0) {
                count = 0;
                int step;
                if ((lonRight - lonLeft) > 50) {
                    step = 4;
                } else {
                    step = 2;
                }

                for (int i = (int) Math.round(lonLeft + 0.5); i <= (int) lonRight; i += step) {
                    annLonRatio[count++] = Math.min(1.0, ((double) i - lonLeft) / (lonRight - lonLeft));
                    if (count % 2 == 0) {
                        //draw the rail rectangle
                        //top
                        g2.fillRect((int) ((getWidth() - mapWidth) / 2 + annLonRatio[count - 2] * mapWidth), (getHeight() - mapHeight) / 2 - borderWidth,
                                (int) ((annLonRatio[count - 1] - annLonRatio[count - 2]) * (double) mapWidth), borderWidth);
                        //bottom
                        g2.fillRect((int) ((getWidth() - mapWidth) / 2 + annLonRatio[count - 2] * mapWidth), (getHeight() + mapHeight) / 2,
                                (int) ((annLonRatio[count - 1] - annLonRatio[count - 2]) * (double) mapWidth), borderWidth);

                        //label the longitude
                        int labelX = (int) ((getWidth() - mapWidth) / 2 + annLonRatio[count - 2] * mapWidth);
                        int labelYTop = (int) ((getHeight() - mapHeight) / 2) - borderWidth - textToBorder;
                        int labelYBottom = (int) ((getHeight() + mapHeight) / 2) + borderWidth + textToBorder;
                        int normLon;
                        String label;
                        if ((i - 2) < -180) {
                            normLon = i - 2 + 360;
                        } else if ((i - 1) > 180) {
                            normLon = i - 2 - 360;
                        } else {
                            normLon = i - 2;
                        }

                        if (normLon >= 0) {
                            label = ((Integer) normLon).toString() + "E";
                        } else {
                            label = ((Integer) Math.abs(normLon)).toString() + "W";
                        }

                        //set font
                        g2.setFont(new Font("SansSerif", Font.BOLD, 16));
                        if ((getHeight() - mapHeight) / 2 >= (borderWidth + textToBorder * 2)) {
                            TextUtilities.drawRotatedString(label, g2, labelX, labelYTop, TextAnchor.CENTER, 0, TextAnchor.CENTER);
                            TextUtilities.drawRotatedString(label, g2, labelX, labelYBottom, TextAnchor.CENTER, 0, TextAnchor.CENTER);
                        }

                    }
                }
            }

            g2.setStroke(savedStroke);
            g2.setFont(savedFont);
        }
    }

    private void drawBaseMap() {

        baseMap = new BufferedImage(mapWidth, mapHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = baseMap.createGraphics();

        int iMove = 0;

        int tilesize = tileSource.getTileSize();
        int tilex = center.x / tilesize;
        int tiley = center.y / tilesize;
        int off_x = (center.x % tilesize);
        int off_y = (center.y % tilesize);

        //int w2 = getWidth() / 2;
        //int h2 = getHeight() / 2;
        int w2 = mapWidth / 2;
        int h2 = mapHeight / 2;
        int posx = w2 - off_x;
        int posy = h2 - off_y;

        int diff_left = off_x;
        int diff_right = tilesize - off_x;
        int diff_top = off_y;
        int diff_bottom = tilesize - off_y;

        boolean start_left = diff_left < diff_right;
        boolean start_top = diff_top < diff_bottom;

        if (start_top) {
            if (start_left) {
                iMove = 2;
            } else {
                iMove = 3;
            }
        } else {
            if (start_left) {
                iMove = 1;
            } else {
                iMove = 0;
            }
        } // calculate the visibility borders

        int x_min = -tilesize;
        int y_min = -tilesize;
        int x_max = getWidth();
        int y_max = getHeight();

        // calculate the length of the grid (number of squares per edge)
        int gridLength = 1 << this.zoom;

        // paint the tiles in a spiral, starting from center of the map
        boolean painted = true;
        int x = 0;
        while (painted) {
            painted = false;
            for (int i = 0; i < 4; i++) {
                if (i % 2 == 0) {
                    x++;
                }
                for (int j = 0; j < x; j++) {
                    if (x_min <= posx && posx <= x_max && y_min <= posy && posy <= y_max) {
                        // tile is visible
                        Tile tile;
                        if (scrollWrapEnabled) {
                            // in case tilex is out of bounds, grab the tile to use for wrapping
                            int tilexWrap = (((tilex % gridLength) + gridLength) % gridLength);
                            tile = tileController.getTile(tilexWrap, tiley, zoom);
                        } else {
                            tile = tileController.getTile(tilex, tiley, zoom);
                        }
                        if (tile != null) {
                            tile.paint(g2, posx, posy);
                        }
                        painted = true;
                    }
                    Point p = move[iMove];
                    posx += p.x * tilesize;
                    posy += p.y * tilesize;
                    tilex += p.x;
                    tiley += p.y;
                }
                iMove = (iMove + 1) % move.length;
            }
        }
        // outer border of the map
        int mapSize = tilesize << zoom;
        //if (scrollWrapEnabled) {
        //    g.drawLine(0, h2 - center.y, getWidth(), h2 - center.y);
        //    g.drawLine(0, h2 - center.y + mapSize, getWidth(), h2 - center.y + mapSize);
        //} else {
        //    g.drawRect(w2 - center.x, h2 - center.y, mapSize, mapSize);
        //}

        // g.drawString("Tiles in cache: " + tileCache.getTileCount(), 50, 20);
        // keep x-coordinates from growing without bound if scroll-wrap is enabled
        if (scrollWrapEnabled) {
            center.x = center.x % mapSize;
        }

        /**
         * will load the database when draw the seis map
         */
        //if(showSeismicity)
        //{
        //    int leftX, rightX, topY, bottomY;
        //    if(center.x-mapWidth/2<0)
        //    {
        //    }
        //double latN =
        //double latS = cenLat - rangeDelta;
        //double lonW = tileSource.XToLon(center.x-, x);
        //double lonE = SeisUtils.LonFromAziDelta(cenLat, cenLon, 90, rangeDelta);
        /**
         * get the seismicity data based on the visible range from zoom level
         */
            //SeisDataDAO.retrieveHistEvents(seisList, latN, latS, lonW, lonE);           
        //}
        Paint savedPaint = g2.getPaint();
        int xpos, ypos;
        if (showSeismicity == true && seisReversed == false) {

            for (HistoricEvent he : seisList) {
                //set depth band
                int depthBand;
                if (forceFit) {
                    depthBand = SeisUtils.getNewDepthBand(he.getDepth());
                } else {
                    depthBand = SeisUtils.getOldDepthBand(he.getDepth());
                }

                if (depthBand <= this.depthCutoff) {
                    if (forceFit) {
                        g2.setPaint(seisNewPaints[depthBand]);
                    } else {
                        g2.setPaint(seisPaints[depthBand]);
                    }

                    xpos = tileSource.LonToX(he.getLon(), this.zoom) - center.x + w2;
                    if (xpos > mapSize) {
                        xpos -= mapSize;
                    } else if (xpos < 0) {
                        xpos += mapSize;
                    }

                    ypos = tileSource.LatToY(he.getLat(), this.zoom) - center.y + h2;
                    //g2.fillOval(xpos-6, ypos-6 , 12, 12);
                    g2.fillOval(xpos - 3, ypos - 3, 6, 6);
                }
            }

            g2.setPaint(savedPaint);
        } else if (showSeismicity == true && seisReversed == true) {
            for (int i = seisList.size() - 1; i >= 0; i--) {
                HistoricEvent he = seisList.get(i);

                //set depth band
                int depthBand;
                if (forceFit) {
                    depthBand = SeisUtils.getNewDepthBand(he.getDepth());
                } else {
                    depthBand = SeisUtils.getOldDepthBand(he.getDepth());
                }

                if (depthBand >= this.depthCutoff) {
                    if (forceFit) {
                        g2.setPaint(seisNewPaints[depthBand]);
                    } else {
                        g2.setPaint(seisPaints[depthBand]);
                    }

                    xpos = tileSource.LonToX(he.getLon(), this.zoom) - center.x + w2;
                    if (xpos > mapSize) {
                        xpos -= mapSize;
                    } else if (xpos < 0) {
                        xpos += mapSize;
                    }

                    ypos = tileSource.LatToY(he.getLat(), this.zoom) - center.y + h2;
                    //g2.fillOval(xpos-6, ypos-6 , 12, 12);
                    g2.fillOval(xpos - 3, ypos - 3, 6, 6);
                }
            }

            g2.setPaint(savedPaint);
        }

        //drawing hypocentres over the seismicty
        if (showHypos) {
            drawHypoGlyphs(g2, mapSize, this.zoom);
        }

    }

    private void drawHypoGlyphs(Graphics2D g2, int mapSize, int seisZoom) {
        int xpos, ypos;
        int w2 = mapWidth / 2;
        int h2 = mapHeight / 2;

        //line thickness for drawing the shape
        g2.setStroke(new BasicStroke(4));

        for (Hypocentre h : hyposList.getHypocentres()) {
            if (h.getIsPrime() != true) {
                g2.setPaint(new Color(128, 128, 128));
            } else {
                g2.setPaint(Color.BLACK);
            }

            xpos = tileSource.LonToX(h.getLon(), seisZoom) - center.x + w2;
            if (xpos > mapSize) {
                xpos -= mapSize;
            } else if (xpos < 0) {
                xpos += mapSize;
            }

            ypos = tileSource.LatToY(h.getLat(), seisZoom) - center.y + h2;
            if (h.getIsPrime() == true) {
                g2.drawRect(xpos - 12, ypos - 12, 24, 24);
            }
        }

        g2.setStroke(new BasicStroke(1));
    }

    private void drawMiniMap() {

        Graphics2D g2 = miniMap.createGraphics();
        g2.drawImage(rawMiniMap, 0, 0, null);

        Paint savedPaint = g2.getPaint();
        Stroke savedStroke = g2.getStroke();

        //calculate the position and draw all the stations first       
        //g2.setPaint(seisPaints[SeisUtils.getOldDepthBand(cenDepth)]);
        //g2.setPaint(seisPaints[SeisUtils.getNewDepthBand(cenDepth)]);
        if (forceFit) {
            g2.setPaint(seisNewPaints[SeisUtils.getNewDepthBand(cenDepth)]);
        } else {
            g2.setPaint(seisPaints[SeisUtils.getOldDepthBand(cenDepth)]);
        }

        g2.setStroke(new BasicStroke(3));

        int px = (int) OsmMercator.LonToX(cenLon, 0);
        int py = (int) OsmMercator.LatToY(cenLat, 0);
        //g2.drawRect(Math.max(0,px-4), Math.max(0,py-4), 8, 8);
        g2.fillRect(Math.max(0, px - 5), Math.max(0, py - 5), 10, 10);

        g2.setPaint(savedPaint);
        g2.setStroke(savedStroke);
    }

    private void calculateAnnotation() {
        //getLat boundary
        latHigh = OsmMercator.YToLat(Math.max(0, center.y - mapHeight / 2), zoom);
        latLow = OsmMercator.YToLat(Math.min(center.y + mapHeight / 2, (tileSource.getTileSize() << zoom)), zoom);

        //calculate longitude per pix
        lonLeft = cenLon - 180.0 * (double) mapWidth / (double) (tileSource.getTileSize() << zoom);
        lonRight = cenLon + 180.0 * (double) mapWidth / (double) (tileSource.getTileSize() << zoom);

        latAnnotNumber = (int) (Math.floor(latHigh) - Math.ceil(latLow) + 2);
        lonAnnotNumber = (int) (Math.floor(lonRight) - Math.ceil(lonLeft) + 2);
    }

    @Override
    public void tileLoadingFinished(Tile tile, boolean bln) {
        repaint();
    }

    @Override
    public TileCache getTileCache() {
        return tileController.getTileCache();
    }

}
