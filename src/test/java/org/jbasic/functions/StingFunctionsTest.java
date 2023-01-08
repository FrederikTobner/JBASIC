package org.jbasic.functions;

import org.jbasic.JBasicBaseTest;
import org.junit.Assert;
import org.junit.Test;

public class StingFunctionsTest extends JBasicBaseTest {

    @Test
    public void testLenFunction() {
        this.test("functions/string/length.bas",
                (result) -> Assert.assertEquals("6" + System.lineSeparator(), result.output));
    }
}
