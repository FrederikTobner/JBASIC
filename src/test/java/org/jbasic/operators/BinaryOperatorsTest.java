package org.jbasic.operators;

import org.jbasic.JBasicEndToEndTest;
import org.junit.Assert;
import org.junit.Test;

import java.time.format.DecimalStyle;

public class BinaryOperatorsTest extends JBasicEndToEndTest {

    @Test
    public void testAdd() {
        this.test("operators/binary/add.bas",
                (result) -> Assert.assertEquals("3" + System.lineSeparator() +
                                "foobar" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testDivide() {
        this.test("operators/binary/divide.bas",
                (result) -> Assert.assertEquals("2" + System.lineSeparator() +
                                "3" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() + "5" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testModulo() {
        this.test("operators/binary/modulo.bas",
                (result) -> Assert.assertEquals("2" + System.lineSeparator(), result.output));
    }

    @Test
    public void testMultiply() {
        this.test("operators/binary/multiply.bas",
                (result) -> Assert.assertEquals("6" + System.lineSeparator(), result.output));
    }

    @Test
    public void testSubtract() {
        this.test("operators/binary/subtract.bas",
                (result) -> Assert.assertEquals("1" + System.lineSeparator(), result.output));
    }
}
