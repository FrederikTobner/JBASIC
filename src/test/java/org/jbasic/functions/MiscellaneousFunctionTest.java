package org.jbasic.functions;

import org.jbasic.JBasicBaseTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MiscellaneousFunctionTest extends JBasicBaseTest {

    @Test
    public void testIsNotANumberFunction() {
        test("functions/miscellaneous/is_not_a_number.bas",
                (result) -> assertEquals("1" + System.lineSeparator(), result.output));
    }

    @Test
    public void testValFunction() {
        test("functions/miscellaneous/value.bas",
                (result) -> assertEquals("1" + System.lineSeparator() +
                                "3" + System.lineSeparator(),
                        result.output));
    }
}
