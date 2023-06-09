package org.jbasic.IO;

import org.jbasic.JBasicEndToEndTest;
import org.junit.Assert;
import org.junit.Test;

public class IOTest extends JBasicEndToEndTest {

    @Test
    public void testInput() {
        this.test("IO/input.bas", "JBASIC\n",
                (result) -> Assert.assertEquals("Name:  Hello JBASIC" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testPrint() {
        this.test("IO/output.bas",
                (result) -> Assert.assertEquals("Hello world!" + System.lineSeparator(),
                        result.output));
    }
}
