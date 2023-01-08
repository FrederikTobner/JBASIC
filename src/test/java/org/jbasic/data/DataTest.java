package org.jbasic.data;

import org.jbasic.JBasicBaseTest;
import org.junit.Assert;
import org.junit.Test;

public class DataTest extends JBasicBaseTest {

    @Test
    public void testRestore() {
        this.test("data/restore.bas",
                (result) -> Assert.assertEquals("5" + System.lineSeparator() + "4" + System.lineSeparator() + "3"
                        + System.lineSeparator() + "5" + System.lineSeparator() + "4" + System.lineSeparator(), result.output));
    }

    @Test
    public void testSimpleData() {
        this.test("data/simple.bas",
                (result) -> Assert.assertEquals("5" + System.lineSeparator() + "4" + System.lineSeparator() + "3"
                        + System.lineSeparator() + "2" + System.lineSeparator() + "1" + System.lineSeparator(), result.output));
    }
}
