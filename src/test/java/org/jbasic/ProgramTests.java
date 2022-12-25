package org.jbasic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProgramTests extends JBasicTest{

    @Test
    public void testGcdEuclid() {
        test("program/gcd_euclid.bas", "9" + System.lineSeparator() + "12" + System.lineSeparator(),
                (result) -> assertEquals("A= B= GCD=3" + System.lineSeparator(), result.output));
    }
}
