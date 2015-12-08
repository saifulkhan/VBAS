
package uk.ac.isc.textview;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.TreeMap;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import uk.ac.isc.eventscontrolview.EventsControlViewTopComponent;
import uk.ac.isc.seisdata.PhasesList;
import uk.ac.isc.seisdata.SeisDataChangeEvent;
import uk.ac.isc.seisdata.SeisDataChangeListener;

/**
 * Top component which displays phase table.
 */
@ConvertAsProperties(
        dtd = "-//uk.ac.isc.textview//PhaseTextView//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "PhaseTextViewTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "phasetext", openAtStartup = true)
@ActionID(category = "Window", id = "uk.ac.isc.textview.PhaseTextViewTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_PhaseTextViewAction",
        preferredID = "PhaseTextViewTopComponent"
)
@Messages({
    "CTL_PhaseTextViewAction=PhaseTextView",
    "CTL_PhaseTextViewTopComponent=PhaseTextView Window",
    "HINT_PhaseTextViewTopComponent=This is a PhaseTextView window"
})


public final class PhaseTextViewTopComponent extends TopComponent implements SeisDataChangeListener {

    //reference of phase list
    private final PhasesList phasesList;

    private final TreeMap<String, String> stations;

    // phase table model for the table
    private PhaseTextViewTableModel ptvtModel = null;
    
    //we have two format, one is short list and the other is long list. They are switchable in the JTabbedPane
    private JTable shortTable = null;
    private JTable longTable = null;
    private JScrollPane shortScrollPane = null;
    private JScrollPane longScrollPane = null;
    
    //This bit is for setting panes for full list representationa dn short list representation
    private JTabbedPane tabPane = new JTabbedPane();
    
    //get control window to retrieve data
    private final TopComponent tc = WindowManager.getDefault().findTopComponent("EventsControlViewTopComponent");
    
    public PhaseTextViewTopComponent() {
        initComponents();
        setName(Bundle.CTL_PhaseTextViewTopComponent());
        setToolTipText(Bundle.HINT_PhaseTextViewTopComponent());
        //this.setOpaque(true);
        
        //set up the table model
        phasesList = ((EventsControlViewTopComponent) tc).getControlPanel().getPhasesList();
        stations = ((EventsControlViewTopComponent) tc).getControlPanel().getStationsForRegion();
        
        ptvtModel = new PhaseTextViewTableModel(phasesList.getPhases());
        
        //set up the two table with the same model

        shortTable = new JTable(ptvtModel);
        setupShortTableVisualAttributes(shortTable);
        
        longTable = new JTable(ptvtModel);
        setupLongTableVisualAttributes(longTable);
        
        shortScrollPane = new JScrollPane(shortTable);
        longScrollPane = new JScrollPane(longTable);
        
        tabPane.addTab("Full List", longScrollPane);
        tabPane.addTab("Short List", shortScrollPane);
        
        this.setLayout(new BorderLayout());
        this.add(tabPane, BorderLayout.CENTER);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
        phasesList.addChangeListener(this);
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
        phasesList.removeChangeListener(this);
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
    
    /*@Override
    public void paint(Graphics g) {
        Paint p = Color.WHITE;//new GradientPaint(0, 0, Color.BLUE, getWidth(), 0, Color.RED);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(p);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }*/

    //repaint when data changes
        @Override
    public void SeisDataChanged(SeisDataChangeEvent event) {
        
        ptvtModel = new PhaseTextViewTableModel(phasesList.getPhases());
        //phasesTable = new JTable(ptvtModel);
                //put the region name into the pahseList
        for(int i = 0; i<phasesList.getPhases().size();i++)
        {
            phasesList.getPhases().get(i).setRegionName(stations.get(phasesList.getPhases().get(i).getReportStation()));
        }
        
        shortTable.setModel(ptvtModel);
        shortTable.clearSelection();
        setupShortTableVisualAttributes(shortTable);
        
        longTable.setModel(ptvtModel);
        longTable.clearSelection();
        setupLongTableVisualAttributes(longTable);
        
        shortScrollPane.setViewportView(shortTable);
        longScrollPane.setViewportView(longTable);
        
        //phasesTable.repaint();
        //this.repaint();
        tabPane.repaint();
    }

    //set short table format, e.g. column width etc.
    private void setupShortTableVisualAttributes(JTable sTable)
    {
        sTable.setRowHeight(40);
        sTable.setFont(new Font("monospaced",Font.PLAIN, 16));
        
        TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        sTable.getColumn("Edit").setCellRenderer(buttonRenderer);
        sTable.addMouseListener(new JTableButtonMouseListener(shortTable));
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        //DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        //leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        
        sTable.setShowGrid(false);
        sTable.setShowVerticalLines(false);
        sTable.setShowHorizontalLines(false);
        sTable.setAutoCreateRowSorter(true);
        sTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        sTable.getColumnModel().getColumn(0).setResizable(false);
        sTable.getColumnModel().getColumn(0).setMinWidth(50);
        sTable.getColumnModel().getColumn(0).setMaxWidth(50);
        sTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        sTable.getColumnModel().getColumn(2).setPreferredWidth(80); //station code
        sTable.getColumnModel().getColumn(3).setPreferredWidth(120); //time
        sTable.getColumnModel().getColumn(4).setPreferredWidth(200); //region name
        
        sTable.getColumnModel().getColumn(5).setPreferredWidth(60);
        sTable.getColumnModel().getColumn(6).setPreferredWidth(60);
        
        sTable.getColumnModel().getColumn(7).setPreferredWidth(60); //phase name
        sTable.getColumnModel().getColumn(8).setPreferredWidth(60); //isc phase name
        
        sTable.getColumnModel().getColumn(10).setMinWidth(40);
        sTable.getColumnModel().getColumn(10).setMaxWidth(40);
        
        sTable.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
        sTable.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
        sTable.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
        sTable.getColumnModel().getColumn(10).setCellRenderer(rightRenderer);
        sTable.getColumnModel().getColumn(11).setCellRenderer(rightRenderer);
        
        //hide the columns
        sTable.getColumnModel().getColumn(12).setMinWidth(0);
        sTable.getColumnModel().getColumn(12).setMaxWidth(0);
        sTable.getColumnModel().getColumn(13).setMinWidth(0);
        sTable.getColumnModel().getColumn(13).setMaxWidth(0);
        sTable.getColumnModel().getColumn(14).setMinWidth(0);
        sTable.getColumnModel().getColumn(14).setMaxWidth(0);
        sTable.getColumnModel().getColumn(15).setMinWidth(0);
        sTable.getColumnModel().getColumn(15).setMaxWidth(0);
        sTable.getColumnModel().getColumn(16).setMinWidth(0);
        sTable.getColumnModel().getColumn(16).setMaxWidth(0);
        
    }
    
    //set long table format, e.g. column width etc.
    private void setupLongTableVisualAttributes(JTable longTable) {
        longTable.setRowHeight(40);
        longTable.setFont(new Font("monospaced",Font.PLAIN, 16));
        
        TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        longTable.getColumn("Edit").setCellRenderer(buttonRenderer);
        longTable.addMouseListener(new JTableButtonMouseListener(longTable));
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        //DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        //leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        
        longTable.setShowGrid(false);
        longTable.setShowVerticalLines(false);
        longTable.setShowHorizontalLines(false);
        longTable.setAutoCreateRowSorter(true);
        longTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        longTable.getColumnModel().getColumn(0).setResizable(false);
        longTable.getColumnModel().getColumn(0).setMinWidth(50);
        longTable.getColumnModel().getColumn(0).setMaxWidth(50);
        longTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        longTable.getColumnModel().getColumn(2).setPreferredWidth(80); //station code
        longTable.getColumnModel().getColumn(3).setPreferredWidth(120); //time
        longTable.getColumnModel().getColumn(4).setPreferredWidth(200); //region name
        
        longTable.getColumnModel().getColumn(5).setPreferredWidth(60);
        longTable.getColumnModel().getColumn(6).setPreferredWidth(60);
        
        longTable.getColumnModel().getColumn(7).setPreferredWidth(60); //phase name
        longTable.getColumnModel().getColumn(8).setPreferredWidth(60); //isc phase name
        
        longTable.getColumnModel().getColumn(10).setMinWidth(40);
        longTable.getColumnModel().getColumn(10).setMaxWidth(40);
        
        longTable.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
        longTable.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
        longTable.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
        longTable.getColumnModel().getColumn(10).setCellRenderer(rightRenderer);
        longTable.getColumnModel().getColumn(11).setCellRenderer(rightRenderer);
        
        for(int i = 9; i<13; i++)
        {
            longTable.getColumnModel().getColumn(i).setPreferredWidth(60);
        }
        longTable.getColumnModel().getColumn(13).setPreferredWidth(120);
        
   }
}