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

/**
 * Pretty CSV PrettyCSV main entry point.
 */
public class PrettyCSV
{
    static AppFrame instance;

    public static void main( String[] args ) throws Exception {
        instance = new AppFrame("Pretty CSV");
        if(instance.prefs.getBounds() != null) {
            Rectangle maxBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
            if(maxBounds.contains(instance.prefs.getBounds())) {
                instance.setBounds(instance.prefs.getBounds());
            } else {
                setDefaultSize();   //part or all of the window is positioned outside of the screen
            }
        } else {
            setDefaultSize();
        }
        instance.setVisible(true);
    }

    public static void setDefaultSize() {
        instance.setSize(640,480);
        instance.setLocationRelativeTo(null);
    }

    public static void handleError(String msg,Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(instance,msg,"Error",JOptionPane.ERROR_MESSAGE);
    }
}
