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
