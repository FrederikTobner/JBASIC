package org.jbasic.switchStatement;

import org.jbasic.JBasicEndToEndTest;
import org.junit.Assert;
import org.junit.Test;

public class SwitchTest extends JBasicEndToEndTest {

    @Test
    public void testSimpleNumerical() {
        this.test("switch/simple_numerical.bas",
                (result) -> Assert.assertEquals("tres" + System.lineSeparator(),
                        result.output));
    }
}
