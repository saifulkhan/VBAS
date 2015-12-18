package uk.ac.isc.eventscontrolview;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays the events control list that the analyst can
 * select for the review. multi-selection is disabled as the current review is
 * based on single selection use this class to load data and make changes and
 * the view cannot be closed
 */
@ConvertAsProperties(
        dtd = "-//uk.ac.isc.eventscontrolview//EventsControlView//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "EventsControlViewTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "explorer", openAtStartup = true)
@ActionID(category = "Window", id = "uk.ac.isc.eventscontrolview.EventsControlViewTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_EventsControlViewAction",
        preferredID = "EventsControlViewTopComponent"
)
@Messages({
    "CTL_EventsControlViewAction=EventsControlView",
    "CTL_EventsControlViewTopComponent=EventsControlView Window",
    "HINT_EventsControlViewTopComponent=This is a EventsControlView window" 
})

public final class EventsControlViewTopComponent extends TopComponent {

    EventsControlPanel eventsTable;         // Event Table
    EventSearchPanel eventsSearchPanel;     // Search Panel
    ActionHistoryTable actionHistoryTable;  // Action Hostory Table

    public EventsControlViewTopComponent() {
        initComponents();
        setName(Bundle.CTL_EventsControlViewTopComponent());
        setToolTipText(Bundle.HINT_EventsControlViewTopComponent());
        putClientProperty(TopComponent.PROP_CLOSING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_SLIDING_DISABLED, Boolean.TRUE);

        eventsTable = new EventsControlPanel();
        eventsSearchPanel = new EventSearchPanel(eventsTable);
        actionHistoryTable = new ActionHistoryTable();

        JScrollPane scrollPane = new JScrollPane(eventsTable.getTable());
        
        /*
        this.setLayout(new BorderLayout());
        this.add(eventsSearchPanel, BorderLayout.PAGE_START);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(actionHistoryTable, BorderLayout.PAGE_END);
        */
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(eventsSearchPanel);
        this.add(scrollPane);
        this.add(actionHistoryTable);     
    }

    /* 
     * return of the control panel, all the data is in this class.
     */
    public EventsControlPanel getControlPanel() {
        return this.eventsTable;
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
        UIManager.put("ToolTip.font", new Font("Arial", Font.BOLD, 18));

    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
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

}