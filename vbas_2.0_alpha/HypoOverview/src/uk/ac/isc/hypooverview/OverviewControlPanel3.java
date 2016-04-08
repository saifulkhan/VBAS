package uk.ac.isc.hypooverview;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import uk.ac.isc.seisdata.VBASLogger;

/**
 *
 * Control panel for controlling the things shown on the main map
 *
 * @author hui
 */
public class OverviewControlPanel3 extends javax.swing.JPanel {

    private final HypoOverviewPanel2 hop;
    private final JCheckBox[] checkBoxRef = new JCheckBox[8];
            
    /**
     * Creates new form OverviewControlPanel3
     */
    public OverviewControlPanel3(final HypoOverviewPanel2 hop) {
        initComponents();

        this.hop = hop;
        
        //get the refs point to the static boxes
        checkBoxRef[0] = jCheckBox1;
        checkBoxRef[1] = jCheckBox2;
        checkBoxRef[2] = jCheckBox3;
        checkBoxRef[3] = jCheckBox4;
        checkBoxRef[4] = jCheckBox5;
        checkBoxRef[5] = jCheckBox6;
        checkBoxRef[6] = jCheckBox7;
        checkBoxRef[7] = jCheckBox8;

        resetToDefault();
    }

    public void resetToDefault() {
        VBASLogger.logDebug("...");

        radioButton_CB.setSelected(true);
        radioButton_Static.setSelected(true);
        setAllBandsSelected();
        hop.setDepthBandOrder(4);
        //slider_bandSelection.setValue(7 - hop.getCurrentBand()); // Solves the Issue #35

        /*ImageIcon map = new ImageIcon(hop.getMiniMap());
        jLabel_miniMap.setIcon(map);
        jLabel_miniMap.revalidate();
        jLabel_miniMap.repaint();*/
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        radioButton_CB = new javax.swing.JRadioButton();
        radioButton_singleSelection = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        slider_bandSelection = new javax.swing.JSlider();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        radioButton_Static = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton9 = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        radioButton_Middle = new javax.swing.JRadioButton();
        jRadioButton12 = new javax.swing.JRadioButton();
        jRadioButton10 = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel_miniMap = new javax.swing.JLabel();

        buttonGroup1.add(jRadioButton2);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton2, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.jRadioButton2.text")); // NOI18N
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton3);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton3, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.jRadioButton3.text")); // NOI18N
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton5);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton5, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.jRadioButton5.text")); // NOI18N
        jRadioButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton5ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton4);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton4, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.jRadioButton4.text")); // NOI18N
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        buttonGroup1.add(radioButton_CB);
        radioButton_CB.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(radioButton_CB, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.radioButton_CB.text")); // NOI18N
        radioButton_CB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButton_CBActionPerformed(evt);
            }
        });

        buttonGroup1.add(radioButton_singleSelection);
        org.openide.awt.Mnemonics.setLocalizedText(radioButton_singleSelection, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.radioButton_singleSelection.text")); // NOI18N
        radioButton_singleSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButton_singleSelectionActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton2)
                    .addComponent(radioButton_CB)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4)
                    .addComponent(jRadioButton5)
                    .addComponent(radioButton_singleSelection)
                    .addComponent(jLabel1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(radioButton_CB)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton2)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton3)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton4)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton5)
                .addGap(18, 18, 18)
                .addComponent(radioButton_singleSelection)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jCheckBox3.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox3, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.jCheckBox3.text")); // NOI18N
        jCheckBox3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });

        jCheckBox6.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox6, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.jCheckBox6.text")); // NOI18N
        jCheckBox6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCheckBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox6ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.jLabel2.text")); // NOI18N

        slider_bandSelection.setMaximum(7);
        slider_bandSelection.setOrientation(javax.swing.JSlider.VERTICAL);
        slider_bandSelection.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        slider_bandSelection.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slider_bandSelectionStateChanged(evt);
            }
        });

        jCheckBox1.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox1, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.jCheckBox1.text")); // NOI18N
        jCheckBox1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jCheckBox5.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox5, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.jCheckBox5.text")); // NOI18N
        jCheckBox5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox5ActionPerformed(evt);
            }
        });

        jCheckBox4.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox4, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.jCheckBox4.text")); // NOI18N
        jCheckBox4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });

        jCheckBox8.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox8, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.jCheckBox8.text")); // NOI18N
        jCheckBox8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCheckBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox8ActionPerformed(evt);
            }
        });

        jCheckBox2.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox2, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.jCheckBox2.text")); // NOI18N
        jCheckBox2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        jCheckBox7.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox7, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.jCheckBox7.text")); // NOI18N
        jCheckBox7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCheckBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(slider_bandSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox1)
                            .addComponent(jCheckBox2)
                            .addComponent(jCheckBox3)
                            .addComponent(jCheckBox4)
                            .addComponent(jCheckBox5)
                            .addComponent(jCheckBox6)
                            .addComponent(jCheckBox7)
                            .addComponent(jCheckBox8)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(16, 16, 16)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox8))
                    .addComponent(slider_bandSelection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.jLabel3.text")); // NOI18N

        buttonGroup2.add(radioButton_Static);
        radioButton_Static.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(radioButton_Static, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.radioButton_Static.text")); // NOI18N
        radioButton_Static.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButton_StaticActionPerformed(evt);
            }
        });

        buttonGroup2.add(jRadioButton7);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton7, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.jRadioButton7.text")); // NOI18N
        jRadioButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton7ActionPerformed(evt);
            }
        });

        buttonGroup2.add(jRadioButton9);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton9, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.jRadioButton9.text")); // NOI18N
        jRadioButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jRadioButton7)
                    .addComponent(radioButton_Static)
                    .addComponent(jRadioButton9))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radioButton_Static)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton9)
                .addContainerGap())
        );

        buttonGroup3.add(radioButton_Middle);
        radioButton_Middle.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(radioButton_Middle, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.radioButton_Middle.text")); // NOI18N
        radioButton_Middle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButton_MiddleActionPerformed(evt);
            }
        });

        buttonGroup3.add(jRadioButton12);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton12, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.jRadioButton12.text")); // NOI18N
        jRadioButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton12ActionPerformed(evt);
            }
        });

        buttonGroup3.add(jRadioButton10);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton10, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.jRadioButton10.text")); // NOI18N
        jRadioButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton10ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.jLabel4.text")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jRadioButton10)
                    .addComponent(radioButton_Middle)
                    .addComponent(jRadioButton12))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radioButton_Middle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton12)
                .addContainerGap())
        );

        org.openide.awt.Mnemonics.setLocalizedText(jLabel_miniMap, org.openide.util.NbBundle.getMessage(OverviewControlPanel3.class, "OverviewControlPanel3.jLabel_miniMap.text")); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel_miniMap, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel_miniMap, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void radioButton_CBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioButton_CBActionPerformed
        // neighbour bands first

        radioButton_CB.setSelected(true);
        setAllBandsSelected();
        hop.setDepthBandOrder(4);
        slider_bandSelection.setValue(7 - hop.getCurrentBand());
    }//GEN-LAST:event_radioButton_CBActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // deep first
        jRadioButton2.setSelected(true);
        setAllBandsSelected();
        hop.setDepthBandOrder(1);
        slider_bandSelection.setValue(7 - hop.getCurrentBand());
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        //shallow first
        jRadioButton3.setSelected(true);
        setAllBandsSelected();
        hop.setDepthBandOrder(2);
        slider_bandSelection.setValue(7 - hop.getCurrentBand());
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        // random
        jRadioButton4.setSelected(true);
        setAllBandsSelected();
        hop.setDepthBandOrder(3);
        slider_bandSelection.setValue(7 - hop.getCurrentBand());
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    private void jRadioButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton5ActionPerformed
        // animation
        jRadioButton5.setSelected(true);
        setAllBandsSelected();
        hop.setDepthBandOrder(5);

        slider_bandSelection.setValue(7 - hop.getCurrentBand());
        //set hypo anim to non
        radioButton_Static.setSelected(true);
        hop.setHypoVisOptions(2);
    }//GEN-LAST:event_jRadioButton5ActionPerformed

    private void radioButton_singleSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioButton_singleSelectionActionPerformed
        // TODO add your handling code here:
        //jSlider2.setValue(7-hop.getCurrentBand());
        radioButton_singleSelection.setSelected(true);

        int fps = (int) slider_bandSelection.getValue();
        boolean[] visible = new boolean[8];
        for (int i = 0; i < 8; i++) {
            if (i == fps) {
                visible[7 - i] = true;
                checkBoxRef[7 - i].setSelected(true);
            } else {
                visible[7 - i] = false;
                checkBoxRef[7 - i].setSelected(false);
            }
        }

        hop.setMultiDepthBandVisible(visible);

        hop.setDepthBandOrder(6);
    }//GEN-LAST:event_radioButton_singleSelectionActionPerformed

    private void slider_bandSelectionStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slider_bandSelectionStateChanged
        // single selection and disable other bands
        JSlider source = (JSlider) evt.getSource();
        if (!source.getValueIsAdjusting()) {
            int fps = (int) source.getValue();
            boolean[] visible = new boolean[8];
            for (int i = 0; i < 8; i++) {
                if (i == fps) {
                    visible[7 - i] = true;
                    checkBoxRef[7 - i].setSelected(true);
                } else {
                    visible[7 - i] = false;
                    checkBoxRef[7 - i].setSelected(false);
                }
            }

            hop.setMultiDepthBandVisible(visible);
        }

        radioButton_singleSelection.setSelected(true);
    }//GEN-LAST:event_slider_bandSelectionStateChanged

    private void setAllBandsSelected() {
        jCheckBox1.setSelected(true);
        jCheckBox2.setSelected(true);
        jCheckBox3.setSelected(true);
        jCheckBox4.setSelected(true);
        jCheckBox5.setSelected(true);
        jCheckBox6.setSelected(true);
        jCheckBox7.setSelected(true);
        jCheckBox8.setSelected(true);
    }

    //this set of buttons for setting the dot size of seismicity
    private void jRadioButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton10ActionPerformed
        // TODO add your handling code here:
        hop.setPixelSize(6);
    }//GEN-LAST:event_jRadioButton10ActionPerformed

    private void radioButton_MiddleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioButton_MiddleActionPerformed
        // TODO add your handling code here:
        hop.setPixelSize(10);
    }//GEN-LAST:event_radioButton_MiddleActionPerformed

    private void jRadioButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton12ActionPerformed
        // TODO add your handling code here:
        hop.setPixelSize(14);
    }//GEN-LAST:event_jRadioButton12ActionPerformed

    //this set of buttons for setting the hypocentre visualization options 
    private void jRadioButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton7ActionPerformed
        // TODO add your handling code here:
        hop.setHypoVisOptions(1);
    }//GEN-LAST:event_jRadioButton7ActionPerformed

    private void radioButton_StaticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioButton_StaticActionPerformed
        // TODO add your handling code here:
        hop.setHypoVisOptions(2);
    }//GEN-LAST:event_radioButton_StaticActionPerformed

    private void jRadioButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton9ActionPerformed
        // TODO add your handling code here:

        radioButton_CB.setSelected(true);
        hop.setDepthBandOrder(4);

        hop.setHypoVisOptions(3);
    }//GEN-LAST:event_jRadioButton9ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed

        if (hop.getDepthBandVisible(0) == true) {
            hop.setSingleDepthBandVisible(0, false);
        } else {
            hop.setSingleDepthBandVisible(0, true);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed

        if (hop.getDepthBandVisible(1) == true) {
            hop.setSingleDepthBandVisible(1, false);
        } else {
            hop.setSingleDepthBandVisible(1, true);
        }
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed

        if (hop.getDepthBandVisible(2) == true) {
            hop.setSingleDepthBandVisible(2, false);
        } else {
            hop.setSingleDepthBandVisible(2, true);
        }
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed

        if (hop.getDepthBandVisible(3) == true) {
            hop.setSingleDepthBandVisible(3, false);
        } else {
            hop.setSingleDepthBandVisible(3, true);
        }
    }//GEN-LAST:event_jCheckBox4ActionPerformed

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox5ActionPerformed

        if (hop.getDepthBandVisible(4) == true) {
            hop.setSingleDepthBandVisible(4, false);
        } else {
            hop.setSingleDepthBandVisible(4, true);
        }
    }//GEN-LAST:event_jCheckBox5ActionPerformed

    private void jCheckBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox6ActionPerformed

        if (hop.getDepthBandVisible(5) == true) {
            hop.setSingleDepthBandVisible(5, false);
        } else {
            hop.setSingleDepthBandVisible(5, true);
        }
    }//GEN-LAST:event_jCheckBox6ActionPerformed

    private void jCheckBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox7ActionPerformed

        if (hop.getDepthBandVisible(6) == true) {
            hop.setSingleDepthBandVisible(6, false);
        } else {
            hop.setSingleDepthBandVisible(6, true);
        }
    }//GEN-LAST:event_jCheckBox7ActionPerformed

    private void jCheckBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox8ActionPerformed

        if (hop.getDepthBandVisible(7) == true) {
            hop.setSingleDepthBandVisible(7, false);
        } else {
            hop.setSingleDepthBandVisible(7, true);
        }
    }//GEN-LAST:event_jCheckBox8ActionPerformed

    public void resetOrderHypoOption() {
        radioButton_CB.setSelected(true);
        radioButton_Static.setSelected(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel_miniMap;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButton10;
    private javax.swing.JRadioButton jRadioButton12;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton9;
    private javax.swing.JRadioButton radioButton_CB;
    private javax.swing.JRadioButton radioButton_Middle;
    private javax.swing.JRadioButton radioButton_Static;
    private javax.swing.JRadioButton radioButton_singleSelection;
    private javax.swing.JSlider slider_bandSelection;
    // End of variables declaration//GEN-END:variables

}
