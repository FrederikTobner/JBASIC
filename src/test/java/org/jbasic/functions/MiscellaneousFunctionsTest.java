package org.jbasic.functions;

import org.jbasic.JBasicBaseTest;
import org.junit.Assert;
import org.junit.Test;

public class MiscellaneousFunctionsTest extends JBasicBaseTest {

    @Test
    public void testListFunction() {
        this.test("functions/miscellaneous/list.bas",
                (result) -> Assert.assertEquals("PRINT LIST()" + System.lineSeparator() +
                                System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testNumFunction() {
        this.test("functions/miscellaneous/num.bas",
                (result) -> Assert.assertEquals("123" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testStrFunction() {
        this.test("functions/miscellaneous/str.bas",
                (result) -> Assert.assertEquals("123" + System.lineSeparator(),
                        result.output));
    }
}
