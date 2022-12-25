/****************************************************************************
 * Copyright (C) 2022 by Frederik Tobner                                    *
 *                                                                          *
 * This file is part of JBASIC.                                             *
 *                                                                          *
 * Permission to use, copy, modify, and distribute this software and its    *
 * documentation under the terms of the GNU General Public License is       *
 * hereby granted.                                                          *
 * No representations are made about the suitability of this software for   *
 * any purpose.                                                             *
 * It is provided "as is" without express or implied warranty.              *
 * See the <"https://www.gnu.org/licenses/gpl-3.0.html">GNU General Public  *
 * License for more details.                                                *
 ****************************************************************************/

package org.jbasic;

import org.junit.Test;

import java.time.format.DecimalStyle;

import static org.junit.Assert.assertEquals;

public class FunctionTests extends JBasicTest{

    @Test
    public void testLenFunction() {
        test("function/length.bas",
                (result) -> assertEquals("6" + System.lineSeparator(), result.output));
    }

    @Test
    public void testValFunction() {
        test("function/value.bas",
                (result) -> assertEquals("1" + System.lineSeparator() +
                                "3" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testIsNotANumberFunction() {
        test("function/is_not_a_number.bas",
                (result) -> assertEquals("1" + System.lineSeparator(), result.output));
    }

    @Test
    public void testAbsFunction() {
        test("function/absolute.bas",
                (result) -> assertEquals("2" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                        "5" + System.lineSeparator(),
                        result.output));
    }
}
