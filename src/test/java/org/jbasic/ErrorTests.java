package org.jbasic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ErrorTests extends JBasicTest{

    @Test
    public void testSyntaxError() {
        test("error/syntax_error.bas",
                (result) -> assertEquals("Error at [2, 4]: Syntax error",
                        result.error.trim()));
    }

    @Test
    public void testTypeError() {
        test("error/type_error.bas",
                (result) -> assertEquals("Error at [0, 0]: Couldn't evaluate numeric expression. Value \"1\" is not a number",
                        result.error.trim()));
    }
}
