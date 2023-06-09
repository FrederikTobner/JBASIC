package org.jbasic.letKeyword;

import org.jbasic.JBasicEndToEndTest;
import org.junit.Assert;
import org.junit.Test;

public class LetTest extends JBasicEndToEndTest {

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
