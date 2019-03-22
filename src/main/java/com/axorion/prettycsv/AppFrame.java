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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Lee Patterson
 */
public class AppFrame extends JFrame {
    SqlFormatter formatter = new SqlFormatter();

    public AppFrame() {
        initComponents();
    }

    private void convertButtonActionPerformed(ActionEvent e) {
        if(sourceField.getText().trim().length() == 0) {
            destField.setText("");
            sourceField.requestFocus();
        } else {

            String formatted = formatter.format(sourceField.getText());
            destField.setText(formatted);
            destField.setCaretPosition(0);
            if(selectCheckbox.isSelected()) {
                destField.setSelectionStart(0);
                destField.setSelectionEnd(formatted.length());
            }
            destField.requestFocus();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        clearMenuItem = new JMenuItem();
        openMenuItem = new JMenuItem();
        menu2 = new JMenu();
        menuItem3 = new JMenuItem();
        menuItem4 = new JMenuItem();
        menuItem5 = new JMenuItem();
        menuItem6 = new JMenuItem();
        panel1 = new JPanel();
        scrollPane1 = new JScrollPane();
        sourceField = new JTextArea();
        scrollPane2 = new JScrollPane();
        destField = new JTextArea();
        panel2 = new JPanel();
        selectCheckbox = new JCheckBox();
        panel3 = new JPanel();
        convertButton = new JButton();
        exitButton = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("File");

                //---- clearMenuItem ----
                clearMenuItem.setText("Clear");
                menu1.add(clearMenuItem);

                //---- openMenuItem ----
                openMenuItem.setText("Open...");
                menu1.add(openMenuItem);
            }
            menuBar1.add(menu1);

            //======== menu2 ========
            {
                menu2.setText("View");

                //---- menuItem3 ----
                menuItem3.setText("Select Output");
                menu2.add(menuItem3);

                //---- menuItem4 ----
                menuItem4.setText("First Row Heading");
                menu2.add(menuItem4);

                //---- menuItem5 ----
                menuItem5.setText("Heading Uppercase");
                menu2.add(menuItem5);

                //---- menuItem6 ----
                menuItem6.setText("Heading Camel case");
                menu2.add(menuItem6);
            }
            menuBar1.add(menu2);
        }
        setJMenuBar(menuBar1);

        //======== panel1 ========
        {
            panel1.setLayout(new GridLayout(0, 1));

            //======== scrollPane1 ========
            {

                //---- sourceField ----
                sourceField.setFont(new Font("Courier", Font.PLAIN, 13));
                scrollPane1.setViewportView(sourceField);
            }
            panel1.add(scrollPane1);

            //======== scrollPane2 ========
            {

                //---- destField ----
                destField.setFont(new Font("Courier", Font.PLAIN, 13));
                scrollPane2.setViewportView(destField);
            }
            panel1.add(scrollPane2);
        }
        contentPane.add(panel1, BorderLayout.CENTER);

        //======== panel2 ========
        {
            panel2.setLayout(new BorderLayout());

            //---- selectCheckbox ----
            selectCheckbox.setText("Select Output Text");
            panel2.add(selectCheckbox, BorderLayout.WEST);

            //======== panel3 ========
            {
                panel3.setLayout(new FlowLayout());

                //---- convertButton ----
                convertButton.setText("Convert");
                convertButton.setMnemonic('C');
                convertButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        convertButtonActionPerformed(e);
                    }
                });
                panel3.add(convertButton);

                //---- exitButton ----
                exitButton.setText("Exit");
                panel3.add(exitButton);
            }
            panel2.add(panel3, BorderLayout.EAST);
        }
        contentPane.add(panel2, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem clearMenuItem;
    private JMenuItem openMenuItem;
    private JMenu menu2;
    private JMenuItem menuItem3;
    private JMenuItem menuItem4;
    private JMenuItem menuItem5;
    private JMenuItem menuItem6;
    private JPanel panel1;
    private JScrollPane scrollPane1;
    private JTextArea sourceField;
    private JScrollPane scrollPane2;
    private JTextArea destField;
    private JPanel panel2;
    private JCheckBox selectCheckbox;
    private JPanel panel3;
    private JButton convertButton;
    private JButton exitButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
