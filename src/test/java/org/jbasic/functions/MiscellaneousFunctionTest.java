package org.jbasic.functions;

import org.jbasic.JBasicBaseTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MiscellaneousFunctionTest extends JBasicBaseTest {

    @Test
    public void testValFunction() {
        this.test("functions/miscellaneous/value.bas",
                (result) -> assertEquals("1" + System.lineSeparator() +
                                "3" + System.lineSeparator(),
                        result.output));
    }
}
