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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SqlFormatterTest {

    @Test
    public void pad() {
        SqlFormatter target = new SqlFormatter();
        assertEquals("    ",target.pad("",      4));
        assertEquals("AB  ",target.pad("AB",    4));
        assertEquals("AB  ",target.pad("AB ",   4));
        assertEquals("ABC ",target.pad("ABC",   4));
        assertEquals("ABCD",target.pad("ABCD",  4));
    }

    @Test
    public void parse() {
        SqlFormatter target = new SqlFormatter();
        String commaFixture = "id,name,selected,amount";
        String[] result = target.parse(commaFixture,",");
        assertEquals(4,         result.length);
        assertEquals("id",      result[0]);
        assertEquals("name",    result[1]);
        assertEquals("selected",result[2]);
        assertEquals("amount",  result[3]);
    }
    @Test
    void format() {
        SqlFormatter target = new SqlFormatter();
        String commaFixture =
                "ID,NAME,SELECTED,AMOUNT\n"+
                "1,Foobar,Y,3.14";
        String resultFixture =
                "ID NAME   SELECTED AMOUNT \n"+
                "1  Foobar Y        3.14   \n";

        String result = target.format(commaFixture);
        assertEquals(resultFixture,result);
    }
    @Test
    void formatUppercaseHeading() {
        SqlFormatter target = new SqlFormatter();
        target.headingType = HeadingTypeEnum.HEADING_UPPERCASE;
        String commaFixture =
                "Id,Name,Selected,Amount\n"+
                "1,Foobar,Y,3.14";
        String resultFixture =
                "ID NAME   SELECTED AMOUNT \n"+
                "1  Foobar Y        3.14   \n";

        String result = target.format(commaFixture);
        assertEquals(resultFixture,result);
    }
    @Test
    void formatLowercaseHeading() {
        SqlFormatter target = new SqlFormatter();
        target.headingType = HeadingTypeEnum.HEADING_LOWERCASE;
        String commaFixture =
                "Id,Name,Selected,Amount\n"+
                "1,Foobar,Y,3.14";
        String resultFixture =
                "id name   selected amount \n"+
                "1  Foobar Y        3.14   \n";

        String result = target.format(commaFixture);
        assertEquals(resultFixture,result);
    }
    @Test
    void formatCapitalizedHeading() {
        SqlFormatter target = new SqlFormatter();
        target.headingType = HeadingTypeEnum.HEADING_CAPITALIZED;
        String commaFixture =
                "ID,NAME,SELECTED,AMOUNT\n"+
                "1,Foobar,Y,3.14";
        String resultFixture =
                "Id Name   Selected Amount \n"+
                "1  Foobar Y        3.14   \n";

        String result = target.format(commaFixture);
        assertEquals(resultFixture,result);
    }
    @Test
    void formatHeadingNotChanged() {
        SqlFormatter target = new SqlFormatter();
        target.headingType = HeadingTypeEnum.HEADING_NOT_CHANGED;
        String commaFixture =
                "ID,NamE,SelecteD,AmounT\n"+
                "1,Foobar,Y,3.14";
        String resultFixture =
                "ID NamE   SelecteD AmounT \n"+
                "1  Foobar Y        3.14   \n";

        String result = target.format(commaFixture);
        assertEquals(resultFixture,result);
    }
}