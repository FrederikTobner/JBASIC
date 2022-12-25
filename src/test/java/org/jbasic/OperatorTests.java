package org.jbasic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OperatorTests extends JBasicTest {

    @Test
    public void testAdd() {
        test("operator/add.bas",
                (result) -> assertEquals("3" + System.lineSeparator(), result.output));
    }

    @Test
    public void testSubtract() {
        test("operator/subtract.bas",
                (result) -> assertEquals("1" + System.lineSeparator(), result.output));
    }

    @Test
    public void testMultiply() {
        test("operator/multiply.bas",
                (result) -> assertEquals("6" + System.lineSeparator(), result.output));
    }

    @Test
    public void testDivide() {
        test("operator/divide.bas",
                (result) -> assertEquals("2" + System.lineSeparator() +
                                "3" + System.lineSeparator(),
                                result.output));
    }

    @Test
    public void testModulo() {
        test("operator/modulo.bas",
                (result) -> assertEquals("2" + System.lineSeparator(), result.output));
    }
}
