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

package org.jbasic.arrays;

import org.jbasic.JBasicBaseTest;
import org.junit.Assert;
import org.junit.Test;

public class ArrayTest extends JBasicBaseTest {

    @Test
    public void testArrayDimensionsMismatchError() {
        this.test("arrays/array_dimensions_mismatch.bas",
                (result) -> Assert.assertEquals("Error at [2, 6]: The dimensions that were specified do not match the dimensions of the array",
                        result.error.trim()));
    }

    @Test
    public void testArrayDimensionsUnsupportedError() {
        this.test("arrays/array_dimension_unsupported.bas",
                (result) -> Assert.assertEquals("Error at [1, 10]: Dimensions can not have numbers after the digit",
                        result.error.trim()));
    }

    @Test
    public void testSimple() {
        this.test("arrays/simple.bas",
                (result) -> Assert.assertEquals("Hello World!" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testTwoDimensions() {
        this.test("arrays/two_dimensions.bas",
                (result) -> Assert.assertEquals("Hello World!" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testThreeDimensions() {
        this.test("arrays/three_dimensions.bas",
                (result) -> Assert.assertEquals("Hello World!" + System.lineSeparator(),
                        result.output));
    }
}
