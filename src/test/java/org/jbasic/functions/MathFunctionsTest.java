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

package org.jbasic.functions;

import org.jbasic.JBasicBaseTest;
import org.junit.Assert;
import org.junit.Test;

import java.time.format.DecimalStyle;

public class MathFunctionsTest extends JBasicBaseTest {

    @Test
    public void testAbsFunction() {
        this.test("functions/math/absolute.bas",
                (result) -> Assert.assertEquals("2" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "5" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testAcsFunction() {
        this.test("functions/math/arc_cosine.bas",
                (result) -> Assert.assertEquals("1" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testAsnFunction() {
        this.test("functions/math/arc_sine.bas",
                (result) -> Assert.assertEquals("1" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testAthFunction() {
        this.test("functions/math/area_tangent_hyperbolicus.bas",
                (result) -> Assert.assertEquals("0" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "5" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testAtnFunction() {
        this.test("functions/math/arc_tangent.bas",
                (result) -> Assert.assertEquals("0" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "5" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testCosFunction() {
        this.test("functions/math/cosine.bas",
                (result) -> Assert.assertEquals("-0" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "4" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testExpFunction() {
        this.test("functions/math/exponential.bas",
                (result) -> Assert.assertEquals("22026" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "5" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testMaxFunction() {
        this.test("functions/math/max.bas",
                (result) -> Assert.assertEquals("15" + System.lineSeparator(), result.output));
    }

    @Test
    public void testMinFunction() {
        this.test("functions/math/min.bas",
                (result) -> Assert.assertEquals("7" + System.lineSeparator(), result.output));
    }

    @Test
    public void testNaturalLogFunction() {
        this.test("functions/math/natural_logarithm.bas",
                (result) -> Assert.assertEquals("2" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "3" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testSinFunction() {
        this.test("functions/math/sine.bas",
                (result) -> Assert.assertEquals("0" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "9" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testSqrFunction() {
        this.test("functions/math/square_root.bas",
                (result) -> Assert.assertEquals("2" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testSumFunction() {
        this.test("functions/math/sum.bas",
                (result) -> Assert.assertEquals("10" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testTanFunction() {
        this.test("functions/math/tangent.bas",
                (result) -> Assert.assertEquals("0" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "6" + System.lineSeparator(),
                        result.output));
    }

}
