package org.jbasic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SubroutineTest extends JBasicBaseTest {

    @Test
    public void testSimple() {
        this.test("subroutine/simple.bas",
                "Jeff" + System.lineSeparator(),
                (result) -> assertEquals("Name= Hi my name is Jeff" + System.lineSeparator(), result.output));
    }
}
