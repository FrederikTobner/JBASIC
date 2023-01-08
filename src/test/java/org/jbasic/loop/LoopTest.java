package org.jbasic.loop;

import org.jbasic.JBasicBaseTest;
import org.junit.Assert;
import org.junit.Test;

public class LoopTest extends JBasicBaseTest {

    @Test
    public void testForContinue() {
        this.test("loops/for_continue.bas",
                (result) -> Assert.assertEquals("3" + System.lineSeparator() +
                                "4" + System.lineSeparator() +
                                "5" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testForExit() {
        this.test("loops/for_exit.bas",
                (result) -> Assert.assertEquals("1" + System.lineSeparator() +
                                "2" + System.lineSeparator() +
                                "3" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testDoUntil() {
        this.test("loops/do_until.bas",
                (result) -> Assert.assertEquals("1" + System.lineSeparator() +
                                "2" + System.lineSeparator() +
                                "3" + System.lineSeparator() +
                                "4" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testDoWhile() {
        this.test("loops/do_while.bas",
                (result) -> Assert.assertEquals("1" + System.lineSeparator() +
                                "2" + System.lineSeparator() +
                                "3" + System.lineSeparator() +
                                "4" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testRepeat() {
        this.test("loops/repeat.bas",
                (result) -> Assert.assertEquals("1" + System.lineSeparator() +
                                "2" + System.lineSeparator() +
                                "3" + System.lineSeparator() +
                                "4" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testSimpleFor() {
        this.test("loops/simple_for.bas",
                (result) -> Assert.assertEquals("1" + System.lineSeparator() + "2" + System.lineSeparator() + "3"
                        + System.lineSeparator() + "4" + System.lineSeparator() + "5" + System.lineSeparator(), result.output));
    }

    @Test
    public void testWhile() {
        this.test("loops/while.bas",
                (result) -> Assert.assertEquals("1" + System.lineSeparator() +
                                "2" + System.lineSeparator() +
                                "3" + System.lineSeparator() +
                                "4" + System.lineSeparator(),
                        result.output));
    }
}
