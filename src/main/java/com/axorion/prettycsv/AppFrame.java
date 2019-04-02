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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
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
    OptionsDialog optionsDialog;
    HelpDialog helpDialog;
    AboutDialog aboutDialog;
    MediaTracker mediaTracker;
    Color[] colors = {
            Color.green,
            Color.yellow,
            Color.blue,
            Color.red,
            Color.white,
            Color.cyan,
            Color.magenta,
            Color.orange,
    };
    int colorIndex=0;

    public AppFrame(String title) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, BackingStoreException, InvalidPreferencesFormatException {
        super(title);
        prefs = new PrettyPrefs(this);
        prefs.loadPrefs();

        formatter = new SqlFormatter(",\t",prefs.getColumnGap());
        mediaTracker = new MediaTracker(this);
        String lcOSName = System.getProperty("os.name").toLowerCase();
        isMac = lcOSName.startsWith("mac os x");
        if(isMac) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();

        selectCheckbox.setSelected(prefs.isSelectOutput());
        selectOutputMenuItem.setSelected(prefs.isSelectOutput());
        selectHeadingMenu();

        if(!isMac) {
            customizeNonMac();
        }

        fileDialog = new FileDialog(this);

        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new OpenFileFilter("csv","Comma Separated") );
        fileChooser.addChoosableFileFilter(new OpenFileFilter("txt","Tab Separated") );

        if(isMac) {
            helpMenu.remove(aboutMenuItem); //no about shown in the help menu, shows under app name menu at left of screen

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
                PrettyCSV.handleError("Error during application initialization",e);
            }
        }
    }

    public PrettyPrefs getPrefs() {
        return prefs;
    }

    private void convertButtonActionPerformed(ActionEvent e) {
        if(sourceField.getText().trim().length() == 0) {
            destField.setText("");
            sourceField.requestFocus();
        } else {
            formatter.headingType = prefs.getHeadingType();
            formatter.gap = prefs.getColumnGap();
            String formatted = formatter.format(sourceField.getText());
            destField.setText(formatted);
            if(selectCheckbox.isSelected()) {
                destField.setSelectionEnd(formatted.length());
                destField.setSelectionStart(0);
            } else {
                destField.setCaretPosition(0);
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
            aboutMenuItemActionPerformed(null);
        } else if (meth.getName().equals("handlePrefs")) {
            optionsSelected();
        }

        return null;
    }

    private void optionsSelected() {
        OptionsDialog dlg = getOptionsDialog();

        if(!dlg.isVisible()) {
            dlg.setVisible(true);
            selectCheckbox.setSelected(prefs.isSelectOutput());
            selectOutputMenuItem.setSelected(prefs.isSelectOutput());
            selectHeadingMenu();
        }
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
        selectOutputMenuItemActionPerformed(e);
    }

    private void selectOutputMenuItemActionPerformed(ActionEvent e) {
        boolean selected = !prefs.isSelectOutput();
        prefs.setSelectOutput(selected);
        selectCheckbox.setSelected(selected);
        selectOutputMenuItem.setSelected(selected);

        prefs.savePrefs();
    }

    public Image loadImage(String filename) {
        String target = "/"+filename;
        URL url = AppFrame.class.getResource(target);
        System.out.println("ImageUtil filename="+target+" url="+url);
        if(url == null) {
            return createPlaceholder(128,128,Color.RED);
        }
        Image im = Toolkit.getDefaultToolkit().getImage(url);
        mediaTracker.addImage(im,0);
        System.out.println("Waiting for "+filename+" to load");
        try {
            long start = System.currentTimeMillis();
            mediaTracker.waitForAll();
            long end = System.currentTimeMillis();
            System.out.println(filename+" loaded in "+(end-start)+"ms");
        } catch(InterruptedException e) {
            //ignore
        }
        return im;
    }

    public Image createPlaceholder(int width,int height,Color c) {
        BufferedImage bi;
        bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        g2.setPaint(colors[colorIndex++]);
        if(colorIndex >= colors.length) {
            colorIndex = 0;
        }
        g2.fillRect(0,0,width,height);
        return bi;
    }

    private void selectHeadingMenu() {
        switch(prefs.getHeadingType()) {
            case HEADING_LOWERCASE:
                onlySelect(headingLowercaseMenuItem);
                break;
            case HEADING_UPPERCASE:
                onlySelect(headingUppercaseMenuItem);
                break;
            case HEADING_CAPITALIZED:
                onlySelect(headingTitlecaseMenuItem);
                break;
            case HEADING_NOT_CHANGED:
                onlySelect(noHeadingMenuItem);
                break;
        }
    }
    private void onlySelect(JMenuItem item) {
        item.setSelected(true);
        JMenuItem[] items = {noHeadingMenuItem,headingLowercaseMenuItem,headingUppercaseMenuItem,headingTitlecaseMenuItem};
        for(JMenuItem m : items) {
            if(m != item) {
                m.setSelected(false);
            }
        }
    }

    private void noHeadingMenuItemActionPerformed(ActionEvent e) {
        prefs.setHeadingType(HeadingTypeEnum.HEADING_NOT_CHANGED);
        onlySelect(noHeadingMenuItem);
        prefs.savePrefs();
    }

    private void headingLowercaseMenuItemActionPerformed(ActionEvent e) {
        prefs.setHeadingType(HeadingTypeEnum.HEADING_LOWERCASE);
        onlySelect(headingLowercaseMenuItem);
        prefs.savePrefs();
    }

    private void headingUppercaseMenuItemActionPerformed(ActionEvent e) {
        prefs.setHeadingType(HeadingTypeEnum.HEADING_UPPERCASE);
        onlySelect(headingUppercaseMenuItem);
        prefs.savePrefs();
    }

    private void headingTitlecaseMenuItemActionPerformed(ActionEvent e) {
        prefs.setHeadingType(HeadingTypeEnum.HEADING_CAPITALIZED);
        onlySelect(headingTitlecaseMenuItem);
        prefs.savePrefs();
    }

    private void windowMoved(ComponentEvent e) {
        prefs.savePrefs();
    }

    private void windowResized(ComponentEvent e) {
        prefs.savePrefs();
    }

    private void prettycsvHelpMenuItemActionPerformed(ActionEvent e) {
        if(!getHelpDialog().isVisible()) {
            getHelpDialog().setVisible(true);
        }
    }

    private void aboutMenuItemActionPerformed(ActionEvent e) {
        AboutDialog dlg = getAboutDialog();
        if(!dlg.isVisible()) {
            dlg.setVisible(true);
        }
    }

    public JDialog getHelpDialog() {
        if(helpDialog == null) {
            helpDialog = new HelpDialog(this);
        }
        return helpDialog;
    }

    public OptionsDialog getOptionsDialog() {
        if(optionsDialog == null) {
            optionsDialog = new OptionsDialog(this,prefs);
        }
        return optionsDialog;
    }

    public AboutDialog getAboutDialog() {
        if(aboutDialog == null) {
            aboutDialog = new AboutDialog(this);
        }
        return aboutDialog;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        menuBar1 = new JMenuBar();
        fileMenu = new JMenu();
        clearMenuItem = new JMenuItem();
        menu2 = new JMenu();
        selectOutputMenuItem = new JCheckBoxMenuItem();
        headingUppercaseMenuItem = new JCheckBoxMenuItem();
        headingLowercaseMenuItem = new JCheckBoxMenuItem();
        headingTitlecaseMenuItem = new JCheckBoxMenuItem();
        noHeadingMenuItem = new JCheckBoxMenuItem();
        helpMenu = new JMenu();
        prettycsvHelpMenuItem = new JMenuItem();
        aboutMenuItem = new JMenuItem();
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
        setTitle("PrettyCSV");
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                windowMoved(e);
            }
            @Override
            public void componentResized(ComponentEvent e) {
                windowResized(e);
            }
        });
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
            }
            menuBar1.add(fileMenu);

            //======== menu2 ========
            {
                menu2.setText("View");

                //---- selectOutputMenuItem ----
                selectOutputMenuItem.setText("Select Output");
                selectOutputMenuItem.setSelectedIcon(UIManager.getIcon("FileChooser.detailsViewIcon"));
                selectOutputMenuItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        selectOutputMenuItemActionPerformed(e);
                    }
                });
                menu2.add(selectOutputMenuItem);
                menu2.addSeparator();

                //---- headingUppercaseMenuItem ----
                headingUppercaseMenuItem.setText("Heading Uppercase");
                headingUppercaseMenuItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        headingUppercaseMenuItemActionPerformed(e);
                    }
                });
                menu2.add(headingUppercaseMenuItem);

                //---- headingLowercaseMenuItem ----
                headingLowercaseMenuItem.setText("Heading Lowercase");
                headingLowercaseMenuItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        headingLowercaseMenuItemActionPerformed(e);
                    }
                });
                menu2.add(headingLowercaseMenuItem);

                //---- headingTitlecaseMenuItem ----
                headingTitlecaseMenuItem.setText("Heading Title Case");
                headingTitlecaseMenuItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        headingTitlecaseMenuItemActionPerformed(e);
                    }
                });
                menu2.add(headingTitlecaseMenuItem);

                //---- noHeadingMenuItem ----
                noHeadingMenuItem.setText("No Heading Change");
                noHeadingMenuItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        noHeadingMenuItemActionPerformed(e);
                    }
                });
                menu2.add(noHeadingMenuItem);
            }
            menuBar1.add(menu2);

            //======== helpMenu ========
            {
                helpMenu.setText("Help");

                //---- prettycsvHelpMenuItem ----
                prettycsvHelpMenuItem.setText("PrettyCSV Help");
                prettycsvHelpMenuItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        prettycsvHelpMenuItemActionPerformed(e);
                    }
                });
                helpMenu.add(prettycsvHelpMenuItem);

                //---- aboutMenuItem ----
                aboutMenuItem.setText("About...");
                aboutMenuItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        aboutMenuItemActionPerformed(e);
                    }
                });
                helpMenu.add(aboutMenuItem);
            }
            menuBar1.add(helpMenu);
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
                panel3.setBorder(new EmptyBorder(10, 0, 0, 0));
                panel3.setLayout(new BorderLayout());

                //---- label2 ----
                label2.setText("Formatted");
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
    private JMenu menu2;
    private JCheckBoxMenuItem selectOutputMenuItem;
    private JCheckBoxMenuItem headingUppercaseMenuItem;
    private JCheckBoxMenuItem headingLowercaseMenuItem;
    private JCheckBoxMenuItem headingTitlecaseMenuItem;
    private JCheckBoxMenuItem noHeadingMenuItem;
    private JMenu helpMenu;
    private JMenuItem prettycsvHelpMenuItem;
    private JMenuItem aboutMenuItem;
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
