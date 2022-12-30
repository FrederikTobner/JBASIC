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

package org.jbasic.IO;

import org.jbasic.JBasicBaseTest;
import org.junit.Assert;
import org.junit.Test;

public class IOTest extends JBasicBaseTest {

    @Test
    public void testInput() {
        this.test("IO/input.bas", "JBASIC\n",
                (result) -> Assert.assertEquals("Name:  Hello JBASIC" + System.lineSeparator(), result.output));
    }

    @Test
    public void testPrint() {
        this.test("IO/output.bas", (result) -> Assert.assertEquals("Hello world!" + System.lineSeparator(), result.output));
    }
}
