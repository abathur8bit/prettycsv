/*
 * Created by JFormDesigner on Sun Mar 31 13:52:19 EDT 2019
 */

package com.axorion.prettycsv;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Lee Patterson
 */
public class HelpDialog extends JDialog {
    public HelpDialog(Frame owner) {
        super(owner);
        initComponents();
        helpText.setText("<h1 id=\"prettycsv\">PrettyCSV</h1>  <p><strong>PrettyCSV</strong> is an open source tool that formats CSV text into neat columns, much like a spreadsheet, suitable for pasting into email or textual documents. </p>  <h1 id=\"convertingtext\">Converting text</h1>  <ol> <li>Paste CSV text into the <em>Source</em> field.</li> <li>Click the <strong>Format</strong> button.</li> <li>The neatly formatted text appears in the <em>Formatted</em> field below.</li> </ol>  <h1 id=\"downloading\">Downloading</h1>  <p>You can get the latest version at http://8BitCoder.com/prettycsv</p>");
        helpText.setCaretPosition(0);
    }

    public HelpDialog(Dialog owner) {
        super(owner);
        initComponents();
    }

    private void okButtonActionPerformed(ActionEvent e) {
        setVisible(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        scrollPane1 = new JScrollPane();
        helpText = new JEditorPane();
        buttonBar = new JPanel();
        okButton = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setPreferredSize(new Dimension(350, 450));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new BorderLayout());

                //======== scrollPane1 ========
                {

                    //---- helpText ----
                    helpText.setText("<h1 id=\"prettycsv\">PrettyCSV</h1>  <p><strong>PrettyCSV</strong> is an open source tool by Lee Patterson that formats CSV text into neat columns, much like a spreadsheet, suitable for pasting into email or textual documents. </p>  <h1 id=\"convertingtext\">Converting text</h1>  <ol> <li>Paste CSV text into the <em>Source</em> field.</li> <li>Click the <strong>Format</strong> button.</li> <li>The neatly formatted text appears in the <em>Formatted</em> field below.</li> </ol>  <h1 id=\"downloading\">Downloading</h1>  <p>You can get the latest version at http://8BitCoder.com/prettycsv</p>");
                    helpText.setContentType("text/html");
                    helpText.setEditable(false);
                    scrollPane1.setViewportView(helpText);
                }
                contentPanel.add(scrollPane1, BorderLayout.CENTER);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

                //---- okButton ----
                okButton.setText("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        okButtonActionPerformed(e);
                    }
                });
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JScrollPane scrollPane1;
    private JEditorPane helpText;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
