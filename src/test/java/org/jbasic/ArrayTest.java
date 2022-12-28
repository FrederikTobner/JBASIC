package org.jbasic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArrayTest extends JBasicBaseTest {

    @Test
    public void testSimple() {
        this.test("arrays/simple.bas",
                (result) -> assertEquals("Hello World!" + System.lineSeparator(),
                        result.output));
    }
}
