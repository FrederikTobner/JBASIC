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

package org.jbasic.label;

import org.jbasic.JBasicBaseTest;
import org.junit.Assert;
import org.junit.Test;

public class LabelTest extends JBasicBaseTest {

    @Test
    public void testInvalidLabelError() {
        this.test("label/invalid_label.bas",
                (result) -> Assert.assertEquals("Error at [1, 0]: Digits are not allowed in a label",
                        result.error.trim()));
    }

    @Test
    public void testUndefinedLabelError() {
        this.test("label/undefined_label.bas",
                (result) -> Assert.assertEquals("Error at [1, 0]: A label called UNDEFINED is not defined",
                        result.error.trim()));
    }

}
