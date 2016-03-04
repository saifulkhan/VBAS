/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.isc.processcommand;

import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import uk.ac.isc.seisdata.Global;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//uk.ac.isc.processcommand//AllCommands//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "AllCommandsTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "explorer", openAtStartup = true)
@ActionID(category = "Window", id = "uk.ac.isc.processcommand.AllCommandsTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_AllCommandsAction",
        preferredID = "AllCommandsTopComponent"
)
@Messages({
    "CTL_AllCommandsAction=AllCommands",
    "CTL_AllCommandsTopComponent=AllCommands Window",
    "HINT_AllCommandsTopComponent=This is a AllCommands window"
})

public final class ProcessCommandTopComponent extends TopComponent {

    CommandTable commandTable;                  // Action Hostory Table
    AssessedCommandTable assessedCommandTable;

    public ProcessCommandTopComponent() {
        initComponents();
        setName(Bundle.CTL_AllCommandsTopComponent());
        setToolTipText(Bundle.HINT_AllCommandsTopComponent());
        Global.logDebug("Loaded...");

        commandTable = new CommandTable();
        assessedCommandTable = new AssessedCommandTable();

        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, commandTable, assessedCommandTable);
        //sp.setDividerSize(4);
        //sp.setContinuousLayout(true);
        sp.setResizeWeight(0.5);
        this.setLayout(new BorderLayout());
        this.add(sp, BorderLayout.CENTER);
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
