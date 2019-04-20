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
import java.io.*;
import java.util.prefs.Preferences;

public class PrettyPrefs {
    AppFrame parent;
    Preferences prefs;
    boolean selectOutput = true;
    boolean copyToClipboard = false;
    HeadingTypeEnum headingType = HeadingTypeEnum.HEADING_UPPERCASE;
    int columnGap = 1;
    Rectangle bounds;
    File prefsFile; //lazy load in #getPrefsFile

    public PrettyPrefs(AppFrame parent) {
        prefs = Preferences.userRoot().node(this.getClass().getName());
        this.parent = parent;
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
                InputStream in = new FileInputStream(getPrefsFile());
                Preferences.importPreferences(in);
                selectOutput = prefs.getBoolean("selectOutput",selectOutput);
                copyToClipboard = prefs.getBoolean("copyToClipboard",copyToClipboard);
                columnGap = prefs.getInt("columnGap",columnGap);
                headingType = HeadingTypeEnum.valueOf(prefs.get("headingType",HeadingTypeEnum.HEADING_UPPERCASE.toString()));
                String position = prefs.get("window",null);
                if(position != null) {
                    String[] nums = position.split(",");
                    bounds = new Rectangle(Integer.parseInt(nums[0]),Integer.parseInt(nums[1]),Integer.parseInt(nums[2]),Integer.parseInt(nums[3]));
                }
            } catch(Exception e) {
                PrettyCSV.handleError("Unable to load preferences from ["+getPrefsFile().getAbsolutePath()+"]",e);
            }
        }
    }

    public void savePrefs() {
        try {
            prefs.put("selectOutput",Boolean.toString(selectOutput));   //select output after format
            prefs.put("copyToClipboard",Boolean.toString(copyToClipboard));   //copy text to the clipboard after formatting
            prefs.put("columnGap",Integer.toString(columnGap));         //spaces between columns
            prefs.put("headingType",headingType.toString());            //Headings Name
            if(parent != null) {
                String position = String.format("%d,%d,%d,%d",parent.getX(),parent.getY(),parent.getWidth(),parent.getHeight());
                prefs.put("window",position);
            }

            OutputStream os = new FileOutputStream(getPrefsFile());
            prefs.exportNode(os);
        } catch(Exception e) {
            PrettyCSV.handleError("Unable to load prefs from ["+getPrefsFile().getAbsolutePath()+"]",e);
        }
    }

    public boolean isSelectOutput() {
        return selectOutput;
    }

    public void setSelectOutput(boolean selectOutput) {
        this.selectOutput = selectOutput;
    }

    public boolean isCopyToClipboard() {
        return copyToClipboard;
    }

    public void setCopyToClipboard(boolean copyToClipboard) {
        this.copyToClipboard = copyToClipboard;
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

    public Rectangle getBounds() {
        return bounds;
    }
}

