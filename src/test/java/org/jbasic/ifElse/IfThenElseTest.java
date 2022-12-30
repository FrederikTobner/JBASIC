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

package org.jbasic.ifElse;

import org.jbasic.JBasicBaseTest;
import org.junit.Assert;
import org.junit.Test;

public class IfThenElseTest extends JBasicBaseTest {

    @Test
    public void testSimpleIfTrue() {
        this.test("ifThenElse/simple_if_true.bas",
                (result) -> Assert.assertEquals("one" + System.lineSeparator() +
                                "two" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testSimpleIfFalse() {
        this.test("ifThenElse/simple_if_false.bas",
                (result) -> Assert.assertEquals("three" + System.lineSeparator(), result.output));
    }

    @Test
    public void testIfElse() {
        this.test("ifThenElse/if_else.bas",
                (result) -> Assert.assertEquals("true" + System.lineSeparator() +
                                "false" + System.lineSeparator(),
                        result.output));
    }
}
