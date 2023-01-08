package org.jbasic.precedence;

import org.jbasic.JBasicBaseTest;
import org.junit.Assert;
import org.junit.Test;

public class PrecedenceTest extends JBasicBaseTest {

    @Test
    public void testSimple() {
        this.test("precedence/simple.bas",
                (result) -> Assert.assertEquals("11" + System.lineSeparator() +
                                "11" + System.lineSeparator(),
                        result.output));
    }
}
