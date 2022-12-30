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

package org.jbasic.operators;

import org.jbasic.JBasicBaseTest;
import org.junit.Assert;
import org.junit.Test;

import java.time.format.DecimalStyle;

public class BinaryOperatorsTest extends JBasicBaseTest {

    @Test
    public void testAdd() {
        this.test("operators/binary/add.bas",
                (result) -> Assert.assertEquals("3" + System.lineSeparator() +
                                "foobar" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testDivide() {
        this.test("operators/binary/divide.bas",
                (result) -> Assert.assertEquals("2" + System.lineSeparator() +
                                "3" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() + "5" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testModulo() {
        this.test("operators/binary/modulo.bas",
                (result) -> Assert.assertEquals("2" + System.lineSeparator(), result.output));
    }

    @Test
    public void testMultiply() {
        this.test("operators/binary/multiply.bas",
                (result) -> Assert.assertEquals("6" + System.lineSeparator(), result.output));
    }

    @Test
    public void testSubtract() {
        this.test("operators/binary/subtract.bas",
                (result) -> Assert.assertEquals("1" + System.lineSeparator(), result.output));
    }
}
