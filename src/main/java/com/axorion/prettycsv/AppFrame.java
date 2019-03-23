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
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;

/**
 * @author Lee Patterson
 */
public class AppFrame extends JFrame implements InvocationHandler {
    SqlFormatter formatter;
    JFileChooser fileChooser;   //used by windows
    FileDialog fileDialog;      //used by mac
    boolean isMac = false;
    PrettyPrefs prefs;

    public AppFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, BackingStoreException, InvalidPreferencesFormatException {
        prefs = new PrettyPrefs();
        prefs.loadPrefs();
        formatter = new SqlFormatter(",",prefs.getColumnGap());

        String lcOSName = System.getProperty("os.name").toLowerCase();
        isMac = lcOSName.startsWith("mac os x");
        if(isMac) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();

        selectCheckbox.setSelected(prefs.isSelectOutput());

        if(!isMac) {
            customizeNonMac();
        }

        fileDialog = new FileDialog(this);

        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new OpenFileFilter("csv","Comma Separated") );
        fileChooser.addChoosableFileFilter(new OpenFileFilter("txt","Tab Separated") );

        try {
            Class quitHandlerClass = Class.forName("com.apple.mrj.MRJQuitHandler");
            Class aboutHandlerClass = Class.forName("com.apple.mrj.MRJAboutHandler");
            Class prefHandlerClass = Class.forName("com.apple.mrj.MRJPrefsHandler");

            Class mrjapputilsClass = Class.forName("com.apple.mrj.MRJApplicationUtils");
            Object methodHandler = Proxy.newProxyInstance(quitHandlerClass.getClassLoader(),new Class[] {quitHandlerClass,aboutHandlerClass,prefHandlerClass},this);

            Method appUtilsObj = mrjapputilsClass.getMethod("registerQuitHandler",new Class[] {quitHandlerClass});
            appUtilsObj.invoke(null,new Object[] {methodHandler});

            appUtilsObj = mrjapputilsClass.getMethod("registerAboutHandler",new Class[] {aboutHandlerClass});
            appUtilsObj.invoke(null,new Object[] {methodHandler});

            appUtilsObj = mrjapputilsClass.getMethod("registerPrefsHandler",new Class[] {prefHandlerClass});
            appUtilsObj.invoke(null,new Object[] {methodHandler});

        } catch(Exception e) {
            App.handleError("Error during application initialization",e);
        }
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

    private void clearMenuItemActionPerformed(ActionEvent e) {
        sourceField.setText("");
        destField.setText("");
        sourceField.requestFocus();
    }

    private void exitButtonActionPerformed(ActionEvent e) {
        System.exit(0);
//            int retval = JOptionPane.showConfirmDialog(this,"Are you sure you want to quit?","Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
//            if(retval == JOptionPane.YES_OPTION) {
//            }
    }

    protected void customizeNonMac() {
        final JFrame parent = this;
        JMenuItem item = new JMenuItem("Options...");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optionsSelected();
            }
        });
        fileMenu.add(item);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitButtonActionPerformed(e);
            }
        });
        fileMenu.add(exitMenuItem);
    }

    /**
     * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
     *      java.lang.reflect.Method, java.lang.Object[])
     */
    public Object invoke(Object proxy,Method meth,Object[] args) throws Throwable {
        if (meth.getName().equals("handleQuit")) {
            exitButtonActionPerformed(null);
        } else if (meth.getName().equals("handleAbout")) {
            AboutDialog dlg = new AboutDialog(this);
            dlg.setVisible(true);
        } else if (meth.getName().equals("handlePrefs")) {
            optionsSelected();
        }

        return null;
    }

    private void optionsSelected() {
        JOptionPane.showMessageDialog(this,"No yet implemented");
//            JDialog dlg = new OptionsDialog(this);
//            dlg.setVisible(true);
    }

    private void openFileDialog() {
        if(isMac) {
            fileDialog.setVisible(true);
        } else {
            fileChooser.showOpenDialog(this);
        }
    }

    private void openMenuItemActionPerformed(ActionEvent e) {
        openFileDialog();
    }

    private void selectCheckboxActionPerformed(ActionEvent e) {
        prefs.setSelectOutput(selectCheckbox.isSelected());
        prefs.savePrefs();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        menuBar1 = new JMenuBar();
        fileMenu = new JMenu();
        clearMenuItem = new JMenuItem();
        openMenuItem = new JMenuItem();
        menu2 = new JMenu();
        selectOutputMenuItem = new JMenuItem();
        noHeadingMenuItem = new JMenuItem();
        firstRowHeadingMenuItem = new JMenuItem();
        headingUppercaseMenuItem = new JMenuItem();
        headingCamelcaseMenuItem = new JMenuItem();
        sourcePanel = new JPanel();
        panel2 = new JPanel();
        label1 = new JLabel();
        sourceScrollPane = new JScrollPane();
        sourceField = new JTextArea();
        panel3 = new JPanel();
        label2 = new JLabel();
        destScrollPane = new JScrollPane();
        destField = new JTextArea();
        controlsPanel = new JPanel();
        selectCheckbox = new JCheckBox();
        buttonPanel = new JPanel();
        convertButton = new JButton();
        exitButton = new JButton();

        //======== this ========
        setTitle("Pretty CSV");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== menuBar1 ========
        {

            //======== fileMenu ========
            {
                fileMenu.setText("File");

                //---- clearMenuItem ----
                clearMenuItem.setText("Clear");
                clearMenuItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        clearMenuItemActionPerformed(e);
                    }
                });
                fileMenu.add(clearMenuItem);

                //---- openMenuItem ----
                openMenuItem.setText("Open...");
                openMenuItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        openMenuItemActionPerformed(e);
                    }
                });
                fileMenu.add(openMenuItem);
            }
            menuBar1.add(fileMenu);

            //======== menu2 ========
            {
                menu2.setText("View");

                //---- selectOutputMenuItem ----
                selectOutputMenuItem.setText("Select Output");
                menu2.add(selectOutputMenuItem);

                //---- noHeadingMenuItem ----
                noHeadingMenuItem.setText("No Headings");
                menu2.add(noHeadingMenuItem);

                //---- firstRowHeadingMenuItem ----
                firstRowHeadingMenuItem.setText("Heading Lowercase");
                menu2.add(firstRowHeadingMenuItem);

                //---- headingUppercaseMenuItem ----
                headingUppercaseMenuItem.setText("Heading Uppercase");
                menu2.add(headingUppercaseMenuItem);

                //---- headingCamelcaseMenuItem ----
                headingCamelcaseMenuItem.setText("Heading Camel case");
                menu2.add(headingCamelcaseMenuItem);
            }
            menuBar1.add(menu2);
        }
        setJMenuBar(menuBar1);

        //======== sourcePanel ========
        {
            sourcePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
            sourcePanel.setLayout(new GridLayout(0, 1));

            //======== panel2 ========
            {
                panel2.setLayout(new BorderLayout());

                //---- label1 ----
                label1.setText("Source");
                panel2.add(label1, BorderLayout.NORTH);

                //======== sourceScrollPane ========
                {

                    //---- sourceField ----
                    sourceField.setFont(new Font("Courier", Font.PLAIN, 13));
                    sourceScrollPane.setViewportView(sourceField);
                }
                panel2.add(sourceScrollPane, BorderLayout.CENTER);
            }
            sourcePanel.add(panel2);

            //======== panel3 ========
            {
                panel3.setLayout(new BorderLayout());

                //---- label2 ----
                label2.setText("Converted");
                panel3.add(label2, BorderLayout.NORTH);

                //======== destScrollPane ========
                {

                    //---- destField ----
                    destField.setFont(new Font("Courier", Font.PLAIN, 13));
                    destScrollPane.setViewportView(destField);
                }
                panel3.add(destScrollPane, BorderLayout.CENTER);
            }
            sourcePanel.add(panel3);
        }
        contentPane.add(sourcePanel, BorderLayout.CENTER);

        //======== controlsPanel ========
        {
            controlsPanel.setLayout(new BorderLayout());

            //---- selectCheckbox ----
            selectCheckbox.setText("Select Output Text");
            selectCheckbox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    selectCheckboxActionPerformed(e);
                }
            });
            controlsPanel.add(selectCheckbox, BorderLayout.WEST);

            //======== buttonPanel ========
            {
                buttonPanel.setLayout(new FlowLayout());

                //---- convertButton ----
                convertButton.setText("Format");
                convertButton.setMnemonic('C');
                convertButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        convertButtonActionPerformed(e);
                    }
                });
                buttonPanel.add(convertButton);

                //---- exitButton ----
                exitButton.setText("Exit");
                exitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        exitButtonActionPerformed(e);
                    }
                });
                buttonPanel.add(exitButton);
            }
            controlsPanel.add(buttonPanel, BorderLayout.EAST);
        }
        contentPane.add(controlsPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JMenuBar menuBar1;
    private JMenu fileMenu;
    private JMenuItem clearMenuItem;
    private JMenuItem openMenuItem;
    private JMenu menu2;
    private JMenuItem selectOutputMenuItem;
    private JMenuItem noHeadingMenuItem;
    private JMenuItem firstRowHeadingMenuItem;
    private JMenuItem headingUppercaseMenuItem;
    private JMenuItem headingCamelcaseMenuItem;
    private JPanel sourcePanel;
    private JPanel panel2;
    private JLabel label1;
    private JScrollPane sourceScrollPane;
    private JTextArea sourceField;
    private JPanel panel3;
    private JLabel label2;
    private JScrollPane destScrollPane;
    private JTextArea destField;
    private JPanel controlsPanel;
    private JCheckBox selectCheckbox;
    private JPanel buttonPanel;
    private JButton convertButton;
    private JButton exitButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
