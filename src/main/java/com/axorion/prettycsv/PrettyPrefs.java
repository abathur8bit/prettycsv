package com.axorion.prettycsv;

import java.io.*;
import java.util.prefs.Preferences;

public class PrettyPrefs {
    Preferences prefs;
    boolean selectOutput = true;
    HeadingTypeEnum headingType = HeadingTypeEnum.HEADING_UPPERCASE;
    int columnGap = 1;
    File prefsFile; //lazy load in #getPrefsFile

    public PrettyPrefs() {
        prefs = Preferences.userRoot().node(this.getClass().getName());
    }

    private File getPrefsFile() {
        if(prefsFile == null) {
            prefsFile = new File(System.getProperty("user.home")+File.separator+".prettycsv");
        }
        return prefsFile;
    }

    public void loadPrefs() {
        if(getPrefsFile().exists()) {
            try {
                System.out.println("Loading prefs from ["+getPrefsFile().getAbsolutePath()+"]");
                InputStream in = new FileInputStream(getPrefsFile());
                Preferences.importPreferences(in);
                selectOutput = prefs.getBoolean("selectOutput",selectOutput);
                columnGap = prefs.getInt("columnGap",columnGap);
                headingType = HeadingTypeEnum.valueOf(prefs.get("headingType",HeadingTypeEnum.HEADING_UPPERCASE.toString()));
            } catch(Exception e) {
                App.handleError("Unable to load preferences from ["+getPrefsFile().getAbsolutePath()+"]",e);
            }
        }
    }

    public void savePrefs() {
        try {
            prefs.put("selectOutput",Boolean.toString(selectOutput));   //select output after format
            prefs.put("columnGap",Integer.toString(columnGap));               //spaces between columns
            prefs.put("headingType",headingType.toString());   //Headings Name

            System.out.println("Saving prefs to ["+getPrefsFile().getAbsolutePath()+"]");
            OutputStream os = new FileOutputStream(getPrefsFile());
            prefs.exportNode(os);
        } catch(Exception e) {
            App.handleError("Unable to load prefs from ["+getPrefsFile().getAbsolutePath()+"]",e);
        }
    }

    public boolean isSelectOutput() {
        return selectOutput;
    }

    public void setSelectOutput(boolean selectOutput) {
        this.selectOutput = selectOutput;
    }

    public HeadingTypeEnum getHeadingType() {
        return headingType;
    }

    public void setHeadingType(HeadingTypeEnum headingType) {
        this.headingType = headingType;
    }

    public int getColumnGap() {
        return columnGap;
    }

    public void setColumnGap(int columnGap) {
        this.columnGap = columnGap;
    }
}

enum HeadingTypeEnum {
    NO_HEADING,
    HEADING_UPPERCASE,
    HEADING_LOWERCASE,
    HEADING_CAPITALIZED
}