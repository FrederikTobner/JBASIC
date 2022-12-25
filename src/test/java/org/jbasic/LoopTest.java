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

import static org.junit.Assert.assertEquals;

public class LoopTest extends JBasicTest {

    @Test
    public void testForContinue() {
        test("loop/for_continue.bas",
                (result) -> assertEquals("3" + System.lineSeparator() +
                                "4" + System.lineSeparator() +
                                "5" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testForExit() {
        test("loop/for_exit.bas",
                (result) -> assertEquals("1" + System.lineSeparator() +
                                "2" + System.lineSeparator() +
                                "3" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testRepeat() {
        test("loop/repeat.bas",
                (result) -> assertEquals("1" + System.lineSeparator() +
                                "2" + System.lineSeparator() +
                                "3" + System.lineSeparator() +
                                "4" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testSimpleFor() {
        test("loop/simple_for.bas",
                (result) -> assertEquals("1" + System.lineSeparator() + "2" + System.lineSeparator() + "3"
                        + System.lineSeparator() +"4" + System.lineSeparator() +"5" + System.lineSeparator(), result.output));
    }

    @Test
    public void testWhile() {
        test("loop/while.bas",
                (result) -> assertEquals("1" + System.lineSeparator() +
                                "2" + System.lineSeparator() +
                                "3" + System.lineSeparator() +
                                "4"+ System.lineSeparator(),
                        result.output));
    }
}