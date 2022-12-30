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

import org.junit.Assert;
import org.junit.Test;

public class ErrorTest extends JBasicBaseTest {

    @Test
    public void testSyntaxError() {
        this.test("error/syntax_error.bas",
                (result) -> Assert.assertEquals("Error at [2, 4]: Syntax error",
                        result.error.trim()));
    }

    @Test
    public void testTypeError() {
        this.test("error/type_error.bas",
                (result) -> Assert.assertEquals("Error at [2, 6]: Could not evaluate arithmetic expression. Value is not a number",
                        result.error.trim()));
    }
}
