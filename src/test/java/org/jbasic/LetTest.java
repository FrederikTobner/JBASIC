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
import static org.junit.Assert.assertTrue;

public class LetTest extends JBasicBaseTest {

    @Test
    public void testNumeric() {
        this.test("let/numeric.bas", (result) -> {
            assertTrue(result.interpreter.getState().getVariableValue("numeric").isANumericalValue());
            assertEquals(123.0, result.interpreter.getState().getVariableValue("numeric").underlyingNumber(), 0.0001f);
        });
    }

    @Test
    public void testString() {
        this.test("let/string.bas", (result) -> {
            assertTrue(result.interpreter.getState().getVariableValue("string").isAStringValue());
            assertEquals("foo", result.interpreter.getState().getVariableValue("string").underlyingString());
        });
    }
}
