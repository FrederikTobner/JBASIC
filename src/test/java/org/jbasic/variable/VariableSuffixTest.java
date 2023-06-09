package org.jbasic.variable;

import org.jbasic.JBasicEndToEndTest;
import org.junit.Assert;
import org.junit.Test;

public class VariableSuffixTest extends JBasicEndToEndTest {

    @Test
    public void testViolatedNumericalSuffixError() {
        this.test("variable/violated_numerical_suffix.bas",
                (result) -> Assert.assertEquals("Error at [1, 8]: Type suffix does not match specified type",
                        result.error.trim()));
    }

    @Test
    public void testViolatedStringSuffixError() {
        this.test("variable/violated_string_suffix.bas",
                (result) -> Assert.assertEquals("Error at [1, 8]: Type suffix does not match specified type",
                        result.error.trim()));
    }
}
