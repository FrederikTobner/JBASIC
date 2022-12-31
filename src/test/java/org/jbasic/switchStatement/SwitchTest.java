package org.jbasic.switchStatement;

import org.jbasic.JBasicBaseTest;
import org.junit.Assert;
import org.junit.Test;

public class SwitchTest extends JBasicBaseTest {

    @Test
    public void testSimpleNumerical() {
        this.test("switch/simple_numerical.bas",
                (result) -> Assert.assertEquals("tres" + System.lineSeparator(),
                        result.output));
    }
}
