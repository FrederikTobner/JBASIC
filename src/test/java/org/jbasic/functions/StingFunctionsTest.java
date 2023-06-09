package org.jbasic.functions;

import org.jbasic.JBasicEndToEndTest;
import org.junit.Assert;
import org.junit.Test;

public class StingFunctionsTest extends JBasicEndToEndTest {

    @Test
    public void testLenFunction() {
        this.test("functions/string/length.bas",
                (result) -> Assert.assertEquals("6" + System.lineSeparator(), result.output));
    }
}
