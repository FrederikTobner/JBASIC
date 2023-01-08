package org.jbasic;

import org.jbasic.interpreter.JBasicInterpreter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

public class JBasicBaseTest {

    protected static class TestResult {

        public JBasicInterpreter interpreter;
        public String output;
        public String error;

        public TestResult(JBasicInterpreter interpreter, String output, String error) {
            this.interpreter = interpreter;
            this.output = output;
            this.error = error;
        }
    }

    protected void test(String resource, Consumer<TestResult> assertion) {
        this.test(resource, "", assertion);
    }

    protected void test(String resource, String input, Consumer<TestResult> assertion) {
        try {
            ByteArrayOutputStream stdout = new ByteArrayOutputStream();
            ByteArrayOutputStream stderr = new ByteArrayOutputStream();
            ByteArrayInputStream stdin = new ByteArrayInputStream(input.getBytes());
            JBasicInterpreter interpreter = new JBasicInterpreter(stdin, stdout, stderr);
            interpreter.run(this.resource(resource));
            assertion.accept(new TestResult(interpreter, stdout.toString(), stderr.toString()));
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private InputStream resource(String filename) {
        return this.getClass().getResourceAsStream("/" + filename);
    }

}