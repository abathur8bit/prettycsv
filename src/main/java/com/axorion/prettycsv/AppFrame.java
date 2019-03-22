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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Lee Patterson
 */
public class AppFrame extends JFrame implements InvocationHandler {
    SqlFormatter formatter = new SqlFormatter();
    JFileChooser fileChooser;   //used by windows
    FileDialog fileDialog;      //used by mac
    boolean isMac = false;

    public AppFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        String lcOSName = System.getProperty("os.name").toLowerCase();
        isMac = lcOSName.startsWith("mac os x");
        if(isMac) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();

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
            e.printStackTrace();
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

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        menuBar1 = new JMenuBar();
        fileMenu = new JMenu();
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

            //======== fileMenu ========
            {
                fileMenu.setText("File");

                //---- clearMenuItem ----
                clearMenuItem.setText("Clear");
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
                exitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        exitButtonActionPerformed(e);
                    }
                });
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
    private JMenu fileMenu;
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
