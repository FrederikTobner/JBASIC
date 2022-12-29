package org.jbasic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GotoTest extends JBasicBaseTest {

    @Test
    public void testSimple() {
        this.test("goto/simple.bas",
                (result) -> assertEquals("1" + System.lineSeparator() +
                        "2" + System.lineSeparator() +
                        "3" + System.lineSeparator(), result.output));
    }
}
