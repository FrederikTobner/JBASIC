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

public class FunctionTest extends JBasicTest{

    @Test
    public void testAbsFunction() {
        test("function/absolute.bas",
                (result) -> assertEquals("2" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "5" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testAcsFunction() {
        test("function/arc_cosine.bas",
                (result) -> assertEquals("1" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testAsnFunction() {
        test("function/arc_sine.bas",
                (result) -> assertEquals("1" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testAthFunction() {
        test("function/arc_hyperbolic_tangent.bas",
                (result) -> assertEquals("0" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "5" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testAtnFunction() {
        test("function/arc_tangent.bas",
                (result) -> assertEquals("0" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "5" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testCosFunction() {
        test("function/cosine.bas",
                (result) -> assertEquals("-0" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "4" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testExpFunction() {
        test("function/exponential.bas",
                (result) -> assertEquals("22026" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "5" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testIsNotANumberFunction() {
        test("function/is_not_a_number.bas",
                (result) -> assertEquals("1" + System.lineSeparator(), result.output));
    }

    @Test
    public void testLenFunction() {
        test("function/length.bas",
                (result) -> assertEquals("6" + System.lineSeparator(), result.output));
    }

    @Test
    public void testLogFunction() {
        test("function/natural_logarithm.bas",
                (result) -> assertEquals("2" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "3" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testSinFunction() {
        test("function/sine.bas",
                (result) -> assertEquals("0" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "9" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testSqrFunction() {
        test("function/square_root.bas",
                (result) -> assertEquals("2" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testTanFunction() {
        test("function/tangent.bas",
                (result) -> assertEquals("0" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "6" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testValFunction() {
        test("function/value.bas",
                (result) -> assertEquals("1" + System.lineSeparator() +
                                "3" + System.lineSeparator(),
                        result.output));
    }
}
