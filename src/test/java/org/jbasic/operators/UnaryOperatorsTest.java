package org.jbasic.operators;

import org.jbasic.JBasicBaseTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnaryOperatorsTest extends JBasicBaseTest {

    @Test
    public void testNegate() {
        this.test("operators/unary/negate.bas",
                (result) -> assertEquals("-5" + System.lineSeparator(), result.output));
    }

    @Test
    public void testNot() {
        this.test("operators/unary/not.bas",
                (result) -> assertEquals("1" + System.lineSeparator(), result.output));
    }
}
