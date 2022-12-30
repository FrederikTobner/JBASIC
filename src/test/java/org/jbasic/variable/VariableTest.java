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

package org.jbasic.variable;

import org.jbasic.JBasicBaseTest;
import org.junit.Assert;
import org.junit.Test;

public class VariableTest extends JBasicBaseTest {

    @Test
    public void testUndefinedVariableError() {
        this.test("variable/undefined_variable.bas",
                (result) -> Assert.assertEquals("Error at [1, 6]: undefined is not defined",
                        result.error.trim()));
    }
}
