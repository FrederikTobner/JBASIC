package org.jbasic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoopTests extends JBasicTest {

    @Test
    public void testForContinue() {
        test("loop/for_continue.bas",
                (result) -> assertEquals("3" + System.lineSeparator() +
                                "4" + System.lineSeparator() +
                                "5" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testForExit() {
        test("loop/for_exit.bas",
                (result) -> assertEquals("1" + System.lineSeparator() +
                                "2" + System.lineSeparator() +
                                "3" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testRepeat() {
        test("loop/repeat.bas",
                (result) -> assertEquals("1" + System.lineSeparator() +
                                "2" + System.lineSeparator() +
                                "3" + System.lineSeparator() +
                                "4" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testSimpleFor() {
        test("loop/simple_for.bas",
                (result) -> assertEquals("1" + System.lineSeparator() + "2" + System.lineSeparator() + "3"
                        + System.lineSeparator() +"4" + System.lineSeparator() +"5" + System.lineSeparator(), result.output));
    }

    @Test
    public void testWhile() {
        test("loop/while.bas",
                (result) -> assertEquals("1" + System.lineSeparator() +
                                "2" + System.lineSeparator() +
                                "3" + System.lineSeparator() +
                                "4"+ System.lineSeparator(),
                        result.output));
    }
}
