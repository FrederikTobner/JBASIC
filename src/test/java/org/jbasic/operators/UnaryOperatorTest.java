package org.jbasic.operators;

import org.jbasic.JBasicBaseTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnaryOperatorTest extends JBasicBaseTest {

    @Test
    public void testNegate() {
        test("operators/unary/negate.bas",
                (result) -> assertEquals("-5" + System.lineSeparator(), result.output));
    }

    @Test
    public void testNot() {
        test("operators/unary/not.bas",
                (result) -> assertEquals("1" + System.lineSeparator(), result.output));
    }
}
