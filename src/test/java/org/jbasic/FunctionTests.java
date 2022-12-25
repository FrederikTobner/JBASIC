package org.jbasic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionTests extends JBasicTest{

    @Test
    public void testLenFunction() {
        test("function/len_function.bas",
                (result) -> assertEquals("6" + System.lineSeparator(), result.output));
    }

    @Test
    public void testValFunction() {
        test("function/val_function.bas",
                (result) -> assertEquals("1" + System.lineSeparator() +
                                "3" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testIsNotANumberFunction() {
        test("function/is_not_a_number_function.bas",
                (result) -> assertEquals("1" + System.lineSeparator(), result.output));
    }

    @Test
    public void testAbsFunction() {
        test("function/abs_function.bas",
                (result) -> assertEquals("2" + System.lineSeparator(), result.output));
    }
}
