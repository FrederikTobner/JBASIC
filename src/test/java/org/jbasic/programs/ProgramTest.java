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

package org.jbasic.programs;

import org.jbasic.JBasicBaseTest;
import org.junit.Assert;
import org.junit.Test;

public class ProgramTest extends JBasicBaseTest {

    @Test
    public void testFibonacci() {
        this.test("program/fibonacci_numbers.bas", "8" + System.lineSeparator(),
                (result) -> Assert.assertEquals("Amount= 0" + System.lineSeparator() +
                                "1" + System.lineSeparator() +
                                "1" + System.lineSeparator() +
                                "2" + System.lineSeparator() +
                                "3" + System.lineSeparator() +
                                "5" + System.lineSeparator() +
                                "8" + System.lineSeparator() +
                                "13" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testGreatestCommonDivisor() {
        this.test("program/greatest_common_divisor.bas", "9" + System.lineSeparator() + "12" + System.lineSeparator(),
                (result) -> Assert.assertEquals("First= Second= 3" + System.lineSeparator(), result.output));
    }

    @Test
    public void testPrintStars() {
        this.test("program/print_stars.bas", "5" + System.lineSeparator(),
                (result) -> Assert.assertEquals("Amount= *****" + System.lineSeparator(), result.output));
    }
}
