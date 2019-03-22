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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.StringTokenizer;

public class SqlFormatter {
    protected String delim;
    protected int gap;

    public SqlFormatter() {
        this(",",1);
    }

    public SqlFormatter(String delim,int gap) {
        this.delim = delim;
        this.gap = gap;
    }

    public String format(String csv) {
        try {
            BufferedReader in = new BufferedReader(new StringReader(csv));
            String header = in.readLine();
            String[] headings = parse(header,",");
            int[] colWidths = new int[headings.length];
            for(int i=0; i<headings.length; i++) {
                colWidths[i] = headings[i].length();
            }

            //now find the longest column lengths
            String row;
            while((row = in.readLine()) != null) {
                String[] cols = parse(row,",");
                for(int i=0; i<colWidths.length; i++) {
                    final int width = cols[i].length();
                    if(width > colWidths[i]) {
                        colWidths[i] = width;
                    }
                }
            }

            //format output nicely
            StringBuilder out = new StringBuilder();
            in = new BufferedReader(new StringReader(csv)); //reset the reader
            while((row = in.readLine()) != null) {
                String[] cols = parse(row,delim);
                for(int i=0; i<cols.length; i++) {
                    out.append(pad(cols[i],colWidths[i]+gap));
                }
                out.append('\n');
            }
//            out.deleteCharAt(out.length()-1);
            return out.toString();
        } catch(IOException e) {
            e.printStackTrace();
            return csv;
        }
    }

    protected String[] parse(String s,String delim) {
        StringTokenizer tok = new StringTokenizer(s,delim);
        int colCount = tok.countTokens();
        String[] cols = new String[colCount];
        int i=0;
        while(tok.hasMoreTokens()) {
            cols[i++] = tok.nextToken();
        }
        return cols;
    }

    protected String pad(String s,int len) {
        if(s.length() >= len) {
            return s;
        } else {
            StringBuilder buff = new StringBuilder(s);
            while(buff.length() < len) {
                buff.append(' ');
            }
            return buff.toString();
        }
    }
}