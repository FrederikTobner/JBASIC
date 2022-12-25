package org.jbasic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IOTests extends JBasicTest {

    @Test
    public void testInput() {
        test("IO/input.bas", "JBASIC\n",
                (result) -> assertEquals("Name:  Hello JBASIC" + System.lineSeparator(), result.output));
    }

    @Test
    public void testPrint() {
        test("IO/print.bas", (result) -> assertEquals("Hello world!" + System.lineSeparator(), result.output));
    }
}
