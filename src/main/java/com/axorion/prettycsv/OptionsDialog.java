/*
 * Created by JFormDesigner on Fri Mar 29 21:10:19 EDT 2019
 */

package com.axorion.prettycsv;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Lee Patterson
 */
public class OptionsDialog extends JDialog {
    PrettyPrefs prefs;
    SqlFormatter formatter;
    String sample =
            "iD,nAmE,AmOuNt\n" +
            "1,Twinkie,2.45";

    public OptionsDialog(Frame owner,PrettyPrefs prefs) {
        super(owner);
        this.prefs = prefs;
        formatter = new SqlFormatter(",",prefs.getColumnGap());
        formatter.gap = prefs.getColumnGap();
        formatter.headingType = prefs.getHeadingType();

        initComponents();

        spacesSpinner.setValue(prefs.getColumnGap());
        selectHeadingType();
        selectOutputCheckBox.setSelected(prefs.isSelectOutput());
    }

    private void selectHeadingType() {
        switch(prefs.getHeadingType()) {
            case HEADING_LOWERCASE:
                lowercaseRadioButton.setSelected(true);
                break;
            case HEADING_UPPERCASE:
                uppercaseRadioButton.setSelected(true);
                break;
            case HEADING_CAPITALIZED:
                titlecaseRadioButton.setSelected(true);
                break;
            case HEADING_NOT_CHANGED:
                nochangeRadioButton.setSelected(true);
                break;
        }
        exampleTextArea.setText(formatter.format(sample));
    }

    private void setHeadingType(HeadingTypeEnum headingType) {
        prefs.setHeadingType(headingType);
        formatter.headingType = prefs.getHeadingType();
        exampleTextArea.setText(formatter.format(sample));
    }

    private void uppercaseRadioButtonActionPerformed(ActionEvent e) {
        setHeadingType(HeadingTypeEnum.HEADING_UPPERCASE);
    }


    private void lowercaseRadioButtonActionPerformed(ActionEvent e) {
        setHeadingType(HeadingTypeEnum.HEADING_LOWERCASE);
    }

    private void titlecaseRadioButtonActionPerformed(ActionEvent e) {
        setHeadingType(HeadingTypeEnum.HEADING_CAPITALIZED);
    }

    private void nochangeRadioButtonActionPerformed(ActionEvent e) {
        setHeadingType(HeadingTypeEnum.HEADING_NOT_CHANGED);
    }

    private void okButtonActionPerformed(ActionEvent e) {
        setVisible(false);
        prefs.savePrefs();
    }

    private void spacesSpinnerStateChanged(ChangeEvent e) {
        prefs.setColumnGap(Integer.valueOf(spacesSpinner.getValue().toString()));
        formatter.gap = prefs.getColumnGap();
        exampleTextArea.setText(formatter.format(sample));
    }

    private void selectOutputCheckBoxActionPerformed(ActionEvent e) {
        boolean selected = selectOutputCheckBox.isSelected();
        prefs.setSelectOutput(selected);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        optionsPanel = new JPanel();
        panel2 = new JPanel();
        spacesSpinner = new JSpinner();
        label1 = new JLabel();
        headingTypePanel = new JPanel();
        headingPanel = new JPanel();
        uppercaseRadioButton = new JRadioButton();
        lowercaseRadioButton = new JRadioButton();
        titlecaseRadioButton = new JRadioButton();
        nochangeRadioButton = new JRadioButton();
        panel3 = new JPanel();
        exampleTextArea = new JTextArea();
        copyToClipboardCheckbox = new JCheckBox();
        selectOutputCheckBox = new JCheckBox();
        buttonBar = new JPanel();
        okButton = new JButton();

        //======== this ========
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new BorderLayout());

                //======== optionsPanel ========
                {
                    optionsPanel.setBorder(new TitledBorder("Spacing"));
                    optionsPanel.setLayout(new BorderLayout());

                    //======== panel2 ========
                    {
                        panel2.setLayout(new BorderLayout());

                        //---- spacesSpinner ----
                        spacesSpinner.setMaximumSize(new Dimension(100, 2147483647));
                        spacesSpinner.setMinimumSize(new Dimension(100, 30));
                        spacesSpinner.setPreferredSize(new Dimension(100, 30));
                        spacesSpinner.setModel(new SpinnerNumberModel(1, 1, 3, 1));
                        spacesSpinner.addChangeListener(new ChangeListener() {
                            public void stateChanged(ChangeEvent e) {
                                spacesSpinnerStateChanged(e);
                            }
                        });
                        panel2.add(spacesSpinner, BorderLayout.LINE_START);

                        //---- label1 ----
                        label1.setText("  Number of spaces between headings");
                        panel2.add(label1, BorderLayout.CENTER);
                    }
                    optionsPanel.add(panel2, BorderLayout.NORTH);
                }
                contentPanel.add(optionsPanel, BorderLayout.PAGE_START);

                //======== headingTypePanel ========
                {
                    headingTypePanel.setLayout(new GridLayout());

                    //======== headingPanel ========
                    {
                        headingPanel.setBorder(new TitledBorder("Heading Type"));
                        headingPanel.setLayout(new BoxLayout(headingPanel, BoxLayout.Y_AXIS));

                        //---- uppercaseRadioButton ----
                        uppercaseRadioButton.setText("Uppercase");
                        uppercaseRadioButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                uppercaseRadioButtonActionPerformed(e);
                            }
                        });
                        headingPanel.add(uppercaseRadioButton);

                        //---- lowercaseRadioButton ----
                        lowercaseRadioButton.setText("Lowercase");
                        lowercaseRadioButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                lowercaseRadioButtonActionPerformed(e);
                            }
                        });
                        headingPanel.add(lowercaseRadioButton);

                        //---- titlecaseRadioButton ----
                        titlecaseRadioButton.setText("Title Case");
                        titlecaseRadioButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                titlecaseRadioButtonActionPerformed(e);
                            }
                        });
                        headingPanel.add(titlecaseRadioButton);

                        //---- nochangeRadioButton ----
                        nochangeRadioButton.setText("No change");
                        nochangeRadioButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                nochangeRadioButtonActionPerformed(e);
                            }
                        });
                        headingPanel.add(nochangeRadioButton);
                    }
                    headingTypePanel.add(headingPanel);

                    //======== panel3 ========
                    {
                        panel3.setBorder(new TitledBorder("Sample"));
                        panel3.setLayout(new GridLayout());

                        //---- exampleTextArea ----
                        exampleTextArea.setFont(new Font("Courier", Font.PLAIN, 10));
                        exampleTextArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        exampleTextArea.setEditable(false);
                        exampleTextArea.setFocusable(false);
                        exampleTextArea.setBorder(new EtchedBorder());
                        panel3.add(exampleTextArea);
                    }
                    headingTypePanel.add(panel3);
                }
                contentPanel.add(headingTypePanel, BorderLayout.CENTER);
            }
            dialogPane.add(contentPanel, BorderLayout.PAGE_START);

            //---- copyToClipboardCheckbox ----
            copyToClipboardCheckbox.setText("Copy to clipboard");
            dialogPane.add(copyToClipboardCheckbox, BorderLayout.CENTER);

            //---- selectOutputCheckBox ----
            selectOutputCheckBox.setText("Select output");
            selectOutputCheckBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    selectOutputCheckBoxActionPerformed(e);
                }
            });
            dialogPane.add(selectOutputCheckBox, BorderLayout.LINE_START);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- okButton ----
                okButton.setText("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        okButtonActionPerformed(e);
                    }
                });
                buttonBar.add(okButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.PAGE_END);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        setSize(400, 300);
        setLocationRelativeTo(getOwner());

        //---- buttonGroup1 ----
        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(uppercaseRadioButton);
        buttonGroup1.add(lowercaseRadioButton);
        buttonGroup1.add(titlecaseRadioButton);
        buttonGroup1.add(nochangeRadioButton);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPanel optionsPanel;
    private JPanel panel2;
    private JSpinner spacesSpinner;
    private JLabel label1;
    private JPanel headingTypePanel;
    private JPanel headingPanel;
    private JRadioButton uppercaseRadioButton;
    private JRadioButton lowercaseRadioButton;
    private JRadioButton titlecaseRadioButton;
    private JRadioButton nochangeRadioButton;
    private JPanel panel3;
    private JTextArea exampleTextArea;
    private JCheckBox copyToClipboardCheckbox;
    private JCheckBox selectOutputCheckBox;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
