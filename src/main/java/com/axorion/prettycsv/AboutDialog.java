/* *****************************************************************************
 * Copyright 2018 Lee Patterson <https://github.com/abathur8bit>
 *
 * You may use and modify at will. Please credit me in the source.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ******************************************************************************/

package com.axorion.prettycsv;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Lee Patterson
 */
public class AboutDialog extends JDialog {
    public AboutDialog(AppFrame owner) {
        super(owner);
        initComponents();
        iconLabel.setIcon(new ImageIcon(owner.loadImage("icon.png")));
        iconLabel.setText("");
    }

    public AboutDialog(Dialog owner) {
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
        iconLabel = new JLabel();
        panel1 = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        label4 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        buttonBar = new JPanel();
        okButton = new JButton();

        //======== this ========
        setMinimumSize(new Dimension(400, 200));
        setResizable(false);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //---- iconLabel ----
            iconLabel.setText("128x128");
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
            iconLabel.setPreferredSize(new Dimension(128, 128));
            dialogPane.add(iconLabel, BorderLayout.NORTH);

            //======== panel1 ========
            {
                panel1.setLayout(new FlowLayout());

                //======== contentPanel ========
                {
                    contentPanel.setLayout(new GridLayout(0, 1));

                    //---- label1 ----
                    label1.setText("PrettyCSV");
                    label1.setHorizontalAlignment(SwingConstants.CENTER);
                    label1.setFont(new Font(".SF NS Text", Font.PLAIN, 26));
                    contentPanel.add(label1);

                    //---- label4 ----
                    label4.setText("Version B.1");
                    label4.setHorizontalAlignment(SwingConstants.CENTER);
                    contentPanel.add(label4);

                    //---- label2 ----
                    label2.setText("By Lee Patterson");
                    label2.setHorizontalTextPosition(SwingConstants.CENTER);
                    label2.setHorizontalAlignment(SwingConstants.CENTER);
                    contentPanel.add(label2);

                    //---- label3 ----
                    label3.setText("http://8BitCoder.com");
                    label3.setHorizontalAlignment(SwingConstants.CENTER);
                    label3.setFont(new Font(".SF NS Text", Font.PLAIN, 10));
                    contentPanel.add(label3);
                }
                panel1.add(contentPanel);
            }
            dialogPane.add(panel1, BorderLayout.CENTER);

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
        setSize(400, 350);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JPanel dialogPane;
    private JLabel iconLabel;
    private JPanel panel1;
    private JPanel contentPanel;
    private JLabel label1;
    private JLabel label4;
    private JLabel label2;
    private JLabel label3;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
