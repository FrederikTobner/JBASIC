package org.jbasic;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

/**
 * Created by matei on 2/23/17.
 */
public class JBasicTest {

    private static class Result {

        public Interpreter interpreter;
        public String output;
        public String error;

        public Result(Interpreter interpreter, String output, String error) {
            this.interpreter = interpreter;
            this.output = output;
            this.error = error;
        }
    }

    private void test(String resource, Consumer<Result> assertions) {
        test(resource, "", assertions);
    }

    private void test(String resource, String input, Consumer<Result> assertions) {
        try {
            ByteArrayOutputStream stdout = new ByteArrayOutputStream();
            ByteArrayOutputStream stderr = new ByteArrayOutputStream();
            ByteArrayInputStream stdin = new ByteArrayInputStream(input.getBytes());
            Interpreter interpreter = new Interpreter(stdin, stdout, stderr);
            interpreter.run(resource(resource));
            String output = stdout.toString();
            String error = stderr.toString();
            assertions.accept(new Result(interpreter, output, error));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream resource(String filename) {
        return getClass().getResourceAsStream("/" + filename);
    }

    @Test
    public void testPrint() {
        test("print.bas", (result) -> {
            assertEquals("Hello world!" + System.lineSeparator(), result.output);
        });
    }

    @Test
    public void testLet() {
        test("let.bas", (result) -> {
            assertTrue(result.interpreter.getMemory().get("string").isString());
            assertEquals("foo", result.interpreter.getMemory().get("string").internalString());
            assertTrue(result.interpreter.getMemory().get("numeric").isNumber());
            assertEquals(123L, result.interpreter.getMemory().get("numeric").internalNumber());
            assertTrue(result.interpreter.getMemory().get("nan").isNaN());
        });
    }

    @Test
    public void testForSimple() {
        test("for_simple.bas", (result) -> {
            assertEquals("1" + System.lineSeparator() + "2" + System.lineSeparator() + "3"
                    + System.lineSeparator() +"4" + System.lineSeparator() +"5" + System.lineSeparator(), result.output);
        });
    }

    @Test
    public void testForContinue() {
        test("for_continue.bas",
                (result) -> assertEquals("3" + System.lineSeparator() +
                "4" + System.lineSeparator() +
                "5" + System.lineSeparator(),
                result.output));
    }

    @Test
    public void testForExit() {
        test("for_exit.bas",
                (result) -> assertEquals("1" + System.lineSeparator() +
                "2" + System.lineSeparator() +
                "3" + System.lineSeparator(),
                result.output));
    }

    @Test
    public void testIfSimpleTrue() {
        test("if_simple_true.bas",
                (result) -> assertEquals("one" + System.lineSeparator() +
                "two" + System.lineSeparator(),
                result.output));
    }

    @Test
    public void testIfSimpleFalse() {
        test("if_simple_false.bas",
                (result) -> assertEquals("three" + System.lineSeparator(), result.output));
    }

    @Test
    public void testIfElse() {
        test("if_else.bas", (result) -> {
            assertEquals("true" + System.lineSeparator() +
                    "false" + System.lineSeparator(),
                    result.output);
        });
    }

    @Test
    public void testWhile() {
        test("while.bas", (result) -> {
            assertEquals("1" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "3" + System.lineSeparator() +
                    "4"+ System.lineSeparator(),
                    result.output);
        });
    }

    @Test
    public void testRepeat() {
        test("repeat.bas", (result) -> {
            assertEquals("1" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "3" + System.lineSeparator() +
                    "4" + System.lineSeparator(),
                    result.output);
        });
    }

    @Test
    public void testSyntaxErr() {
        test("syntax_err.bas", (result) -> {
            assertEquals("Error at [1, 4]: Syntax error",
                    result.error.trim());
        });
    }

    @Test
    public void testTypeErr() {
        test("type_err.bas", (result) -> {
            assertEquals("Error at [0, 0]: Couldn't evaluate numeric expression. Value \"1\" is not a number",
                    result.error.trim());
        });
    }

    @Test
    public void testFunctions() {
        test("functions.bas", (result) -> {
            assertEquals("123" + System.lineSeparator() +
                    "5" + System.lineSeparator() +
                    "1" + System.lineSeparator(), result.output);
        });
    }

    @Test
    public void testOperations() {
        test("operations.bas", (result) -> {
            assertEquals("3" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "6" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "3" + System.lineSeparator() +
                    "2" + System.lineSeparator(), result.output);
        });
    }

    @Test
    public void testInput() {
        test("input.bas", "Little Basic\n", (result) -> {
            assertEquals("Name:  Hello Little Basic" + System.lineSeparator(), result.output);
        });
    }

    @Test
    public void testGcdEuclid() {
        test("gcd_euclid.bas", "9" + System.lineSeparator() + "12" + System.lineSeparator(),
                (result) -> assertEquals("A= B= GCD=3" + System.lineSeparator(), result.output));
    }

}