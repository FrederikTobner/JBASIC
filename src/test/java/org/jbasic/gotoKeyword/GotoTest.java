package org.jbasic.gotoKeyword;

import org.jbasic.JBasicEndToEndTest;
import org.junit.Assert;
import org.junit.Test;

public class GotoTest extends JBasicEndToEndTest {

    @Test
    public void testSimple() {
        this.test("goto/simple.bas",
                (result) -> Assert.assertEquals("1" + System.lineSeparator() +
                        "2" + System.lineSeparator() +
                        "3" + System.lineSeparator(), result.output));
    }
}
