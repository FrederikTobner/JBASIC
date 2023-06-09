package org.jbasic.variable;

import org.jbasic.JBasicEndToEndTest;
import org.junit.Assert;
import org.junit.Test;

public class VariableTest extends JBasicEndToEndTest {

    @Test
    public void testUndefinedVariableError() {
        this.test("variable/undefined_variable.bas",
                (result) -> Assert.assertEquals("Error at [1, 6]: undefined is not defined",
                        result.error.trim()));
    }
}
