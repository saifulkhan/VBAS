/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytable;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;

/**
 *
 * @author saiful
 */
public class MultipleTabletest extends javax.swing.JFrame {

    /**
     * Creates new form RelocateFrame
     */
    public MultipleTabletest() {
        initComponents();
        
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        eventSearchPanel = new javax.swing.JPanel();
        eventSearchPanel1 = new javax.swing.JPanel();
        button_ok = new javax.swing.JButton();
        button_cancel = new javax.swing.JButton();
        jSplitPane2 = new javax.swing.JSplitPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Relocate Event");

        eventSearchPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout eventSearchPanelLayout = new javax.swing.GroupLayout(eventSearchPanel);
        eventSearchPanel.setLayout(eventSearchPanelLayout);
        eventSearchPanelLayout.setHorizontalGroup(
            eventSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        eventSearchPanelLayout.setVerticalGroup(
            eventSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        eventSearchPanel1.setBackground(new java.awt.Color(255, 255, 255));

        button_ok.setBackground(new java.awt.Color(45, 137, 239));
        button_ok.setForeground(new java.awt.Color(255, 255, 255));
        button_ok.setText("OK");
        button_ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_okActionPerformed(evt);
            }
        });

        button_cancel.setBackground(new java.awt.Color(45, 137, 239));
        button_cancel.setForeground(new java.awt.Color(255, 255, 255));
        button_cancel.setText("Cancel");
        button_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_cancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout eventSearchPanel1Layout = new javax.swing.GroupLayout(eventSearchPanel1);
        eventSearchPanel1.setLayout(eventSearchPanel1Layout);
        eventSearchPanel1Layout.setHorizontalGroup(
            eventSearchPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, eventSearchPanel1Layout.createSequentialGroup()
                .addContainerGap(414, Short.MAX_VALUE)
                .addComponent(button_ok, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_cancel)
                .addContainerGap())
        );
        eventSearchPanel1Layout.setVerticalGroup(
            eventSearchPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, eventSearchPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(eventSearchPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_ok)
                    .addComponent(button_cancel))
                .addContainerGap())
        );

        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(eventSearchPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSplitPane2)
                            .addComponent(eventSearchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(eventSearchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                .addGap(97, 97, 97)
                .addComponent(eventSearchPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
   
    
    /*
     * OK / Cancel
     */
    private void button_okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_okActionPerformed
        // TODO add your handling code here:
        
                    
            //System.out.print("Depth value: " + this.textFieldDepth.getText().equals(""));
            
            //if(this.text_depth.getText().equals(" ") && this.radioButtonFix.isSelected()) {
            //    JOptionPane.showMessageDialog(null, "Enter Depth.");
            //}
            
        //double area, perimeter, length, width;
        //length = Double.parseDouble(this.jTextFieldLength.getText());
        //this.jTextFieldArea.setText(String.format("%f", length));               
    }//GEN-LAST:event_button_okActionPerformed

    private void button_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_cancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_button_cancelActionPerformed

    
    
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_cancel;
    private javax.swing.JButton button_ok;
    private javax.swing.JPanel eventSearchPanel;
    private javax.swing.JPanel eventSearchPanel1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    // End of variables declaration//GEN-END:variables
}
