package org.jbasic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IfThenElseTests extends JBasicTest {

    @Test
    public void testSimpleIfTrue() {
        test("ifThenElse/simple_if_true.bas",
                (result) -> assertEquals("one" + System.lineSeparator() +
                                "two" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testSimpleIfFalse() {
        test("ifThenElse/simple_if_false.bas",
                (result) -> assertEquals("three" + System.lineSeparator(), result.output));
    }

    @Test
    public void testIfElse() {
        test("ifThenElse/if_else.bas",
                (result) -> assertEquals("true" + System.lineSeparator() +
                                "false" + System.lineSeparator(),
                        result.output));
    }
}
