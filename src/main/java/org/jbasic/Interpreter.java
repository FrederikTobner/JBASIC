package org.jbasic;

import basic.JBasicLexer;
import basic.JBasicParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * The entry point of the interpreter.
 */
public class Interpreter {

    private final InputStream stdin;
    private final PrintStream stdoutPrint;
    private final PrintStream stderrPrint;
    private Memory memory;

    public Interpreter(InputStream stdin, OutputStream stdout, OutputStream stderr) {
        this.stdin = stdin;
        this.stdoutPrint = new PrintStream(stdout, true);
        this.stderrPrint = new PrintStream(stderr, true);
    }

    public void run(InputStream programInput) throws IOException {
        ANTLRInputStream input = new ANTLRInputStream(programInput);
        JBasicLexer lexer = new JBasicLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JBasicParser parser = new JBasicParser(tokens);
        parser.setErrorHandler(new BailErrorStrategy());
        parser.removeErrorListeners();
        parser.addErrorListener(new ErrorListener(stderrPrint));
        try {
            ParseTree tree = parser.program();
            memory = new Memory();
            JBasicVisitor eval = new JBasicVisitor(memory, stdin, stdoutPrint);
            eval.visit(tree);
        }
        catch (InterpreterException e) {
            stderrPrint.println(e.getMessage());
        }
        catch (ParseCancellationException e) {
            if (e.getCause() instanceof InputMismatchException) {
                InputMismatchException inputEx = (InputMismatchException)e.getCause();
                String msg = Utils.formatErrorMessage(
                        inputEx.getOffendingToken().getLine(),
                        inputEx.getOffendingToken().getCharPositionInLine(),
                        "Syntax error");
                stderrPrint.println(msg);
            }
        }
    }

    public Memory getMemory() {
        return memory;
    }

    public void clear() {
        memory.free();
    }
}
