package org.jbasic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StatementTests extends JBasicTest {

    @Test
    public void testLet() {
        test("statement/let.bas", (result) -> {
            assertTrue(result.interpreter.getMemory().get("string").isString());
            assertEquals("foo", result.interpreter.getMemory().get("string").internalString());
            assertTrue(result.interpreter.getMemory().get("numeric").isNumber());
            assertEquals(123L, result.interpreter.getMemory().get("numeric").internalNumber());
            assertTrue(result.interpreter.getMemory().get("nan").isNaN());
        });
    }
}
