package org.jbasic.functions;

import org.jbasic.JBasicBaseTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StingFunctionTest extends JBasicBaseTest {

    @Test
    public void testLenFunction() {
        this.test("functions/string/length.bas",
                (result) -> assertEquals("6" + System.lineSeparator(), result.output));
    }
}
