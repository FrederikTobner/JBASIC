package org.jbasic.functions;

import org.jbasic.JBasicEndToEndTest;
import org.junit.Assert;
import org.junit.Test;

public class GeneralFunctionTest extends JBasicEndToEndTest {

    @Test
    public void testFunctionArityError() {
        this.test("functions/general/function_arity.bas",
                (result) -> Assert.assertEquals("Error at [1, 10]: ABS can not be called with 2 arguments",
                        result.error.trim()));
    }
}
