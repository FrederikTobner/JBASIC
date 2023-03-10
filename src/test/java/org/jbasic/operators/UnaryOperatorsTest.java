package org.jbasic.operators;

import org.jbasic.JBasicBaseTest;
import org.junit.Assert;
import org.junit.Test;

public class UnaryOperatorsTest extends JBasicBaseTest {

    @Test
    public void testNegate() {
        this.test("operators/unary/negate.bas",
                (result) -> Assert.assertEquals("-5" + System.lineSeparator(), result.output));
    }

    @Test
    public void testNot() {
        this.test("operators/unary/not.bas",
                (result) -> Assert.assertEquals("1" + System.lineSeparator(), result.output));
    }
}
