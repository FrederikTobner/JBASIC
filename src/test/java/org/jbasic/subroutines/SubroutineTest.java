package org.jbasic.subroutines;

import org.jbasic.JBasicEndToEndTest;
import org.junit.Assert;
import org.junit.Test;

public class SubroutineTest extends JBasicEndToEndTest {

    @Test
    public void testSimple() {
        this.test("subroutine/simple.bas",
                "Jeff" + System.lineSeparator(),
                (result) -> Assert.assertEquals("Name= Hi my name is Jeff" + System.lineSeparator(), result.output));
    }

    @Test
    public void testSubroutineArityError() {
        this.test("subroutine/subroutine_arity.bas",
                (result) -> Assert.assertEquals("Error at [6, 0]: Subroutine expects 0 arguments but was called with 1",
                        result.error.trim()));
    }

    @Test
    public void testSubroutineRedefinitionError() {
        this.test("subroutine/subroutine_redefinition.bas",
                (result) -> Assert.assertEquals("Error at [6, 0]: A subroutine with the name Greet is already defined in the script",
                        result.error.trim()));
    }

    @Test
    public void testUndefinedSubroutineError() {
        this.test("subroutine/undefined_subroutine.bas",
                (result) -> Assert.assertEquals("Error at [1, 0]: A subroutine with the nameUNDEFINED is not defined in the script",
                        result.error.trim()));
    }
}
