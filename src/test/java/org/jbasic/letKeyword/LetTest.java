package org.jbasic.letKeyword;

import org.jbasic.JBasicBaseTest;
import org.junit.Assert;
import org.junit.Test;

public class LetTest extends JBasicBaseTest {

    @Test
    public void testNumeric() {
        this.test("let/numeric.bas", (result) -> {
            Assert.assertTrue(result.interpreter.getState().getVariableValue("numeric", null).isANumericalValue());
            Assert.assertEquals(123.0, result.interpreter.getState().getVariableValue("numeric", null).underlyingNumber(), 0.0001f);
        });
    }

    @Test
    public void testString() {
        this.test("let/string.bas", (result) -> {
            Assert.assertTrue(result.interpreter.getState().getVariableValue("string", null).isAStringValue());
            Assert.assertEquals("foo", result.interpreter.getState().getVariableValue("string", null).underlyingString());
        });
    }
}
