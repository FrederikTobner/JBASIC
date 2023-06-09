package org.jbasic.arrays;

import org.jbasic.JBasicEndToEndTest;
import org.junit.Assert;
import org.junit.Test;

public class ArrayTest extends JBasicEndToEndTest {

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
