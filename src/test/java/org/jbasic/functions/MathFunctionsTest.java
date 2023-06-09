package org.jbasic.functions;

import org.jbasic.JBasicEndToEndTest;
import org.junit.Assert;
import org.junit.Test;

import java.time.format.DecimalStyle;

public class MathFunctionsTest extends JBasicEndToEndTest {

    @Test
    public void testAbsFunction() {
        this.test("functions/math/absolute.bas",
                (result) -> Assert.assertEquals("2" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "5" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testAcsFunction() {
        this.test("functions/math/arc_cosine.bas",
                (result) -> Assert.assertEquals("1" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "5707963267949" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testAshFunction() {
        this.test("functions/math/arc_sine_hyperbolicus.bas",
                (result) -> Assert.assertEquals("1" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testAsnFunction() {
        this.test("functions/math/arc_sine.bas",
                (result) -> Assert.assertEquals("1" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "5707963267949" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testAthFunction() {
        this.test("functions/math/area_tangent_hyperbolicus.bas",
                (result) -> Assert.assertEquals("0" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "54930614433405" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testAtnFunction() {
        this.test("functions/math/arc_tangent.bas",
                (result) -> Assert.assertEquals("0" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "78539816339745" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testCosFunction() {
        this.test("functions/math/cosine.bas",
                (result) -> Assert.assertEquals("0" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testExpFunction() {
        this.test("functions/math/exponential.bas",
                (result) -> Assert.assertEquals("22026" + DecimalStyle.ofDefaultLocale().getDecimalSeparator() +
                                "465794806718" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testMaxFunction() {
        this.test("functions/math/max.bas",
                (result) -> Assert.assertEquals("15" + System.lineSeparator(), result.output));
    }

    @Test
    public void testMinFunction() {
        this.test("functions/math/min.bas",
                (result) -> Assert.assertEquals("7" + System.lineSeparator(), result.output));
    }

    @Test
    public void testNaturalLogFunction() {
        this.test("functions/math/natural_logarithm.bas",
                (result) -> Assert.assertEquals("1" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testSinFunction() {
        this.test("functions/math/sine.bas",
                (result) -> Assert.assertEquals("1" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testSqrFunction() {
        this.test("functions/math/square_root.bas",
                (result) -> Assert.assertEquals("2" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testSumFunction() {
        this.test("functions/math/sum.bas",
                (result) -> Assert.assertEquals("10" + System.lineSeparator(),
                        result.output));
    }

    @Test
    public void testTanFunction() {
        this.test("functions/math/tangent.bas",
                (result) -> Assert.assertEquals("1" + System.lineSeparator(),
                        result.output));
    }

}
