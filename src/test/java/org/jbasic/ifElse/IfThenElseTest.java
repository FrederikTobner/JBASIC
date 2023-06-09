package org.jbasic.ifElse;

import org.jbasic.JBasicEndToEndTest;
import org.junit.Assert;
import org.junit.Test;

public class IfThenElseTest extends JBasicEndToEndTest {

    @Test
    public void testSimpleIfTrue() {
        this.test("ifThenElse/simple_if_true.bas",
                (result) -> Assert.assertEquals("one" + System.lineSeparator() +
                                "two" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testSimpleIfFalse() {
        this.test("ifThenElse/simple_if_false.bas",
                (result) -> Assert.assertEquals("three" + System.lineSeparator(), result.output));
    }

    @Test
    public void testIfElse() {
        this.test("ifThenElse/if_else.bas",
                (result) -> Assert.assertEquals("true" + System.lineSeparator() +
                                "false" + System.lineSeparator(),
                        result.output));
    }
}
