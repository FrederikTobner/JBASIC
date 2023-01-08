package org.jbasic;

import org.junit.Assert;
import org.junit.Test;

public class ErrorTest extends JBasicBaseTest {

    @Test
    public void testSyntaxError() {
        this.test("error/syntax_error.bas",
                (result) -> Assert.assertEquals("Error at [2, 4]: Syntax error",
                        result.error.trim()));
    }

    @Test
    public void testTypeError() {
        this.test("error/type_error.bas",
                (result) -> Assert.assertEquals("Error at [2, 6]: Could not evaluate arithmetic expression. Value is not a number",
                        result.error.trim()));
    }
}
