package org.jbasic.variable;

import org.jbasic.JBasicBaseTest;
import org.junit.Assert;
import org.junit.Test;

public class VariableTest extends JBasicBaseTest {

    @Test
    public void testUndefinedVariableError() {
        this.test("variable/undefined_variable.bas",
                (result) -> Assert.assertEquals("Error at [1, 6]: undefined is not defined",
                        result.error.trim()));
    }
}
