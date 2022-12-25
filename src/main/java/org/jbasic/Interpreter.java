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
        // Wrapping the sourcecode in a ANTLRInputStream
        ANTLRInputStream input = new ANTLRInputStream(programInput);
        // Initializing a newly created lexer object with the ANTLRInputStream
        JBasicLexer lexer = new JBasicLexer(input);
        // Creating a stream of tokens with the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // Creating a parser object with the stream of tokens
        JBasicParser parser = new JBasicParser(tokens);
        // Default bailout error strategy
        parser.setErrorHandler(new BailErrorStrategy());
        // Removing default error listeners
        parser.removeErrorListeners();
        // Adding our own error listener
        parser.addErrorListener(new ErrorListener(stderrPrint));
        try {
            // We create an abstract syntax tree from the tokens
            ParseTree tree = parser.program();
            // Memory used by the program
            memory = new Memory();
            // Creating the visitor to visit the nodes in the abstract syntax tree
            JBasicVisitor visitor = new JBasicVisitor(memory, stdin, stdoutPrint);
            // Executing the program with the visitor
            visitor.visit(tree);
        }
        catch (InterpreterException e) {
            stderrPrint.println(e.getMessage());
        }
        catch (ParseCancellationException e) {
            if (e.getCause() instanceof InputMismatchException) {
                InputMismatchException inputEx = (InputMismatchException)e.getCause();
                String msg = CoreUtils.formatErrorMessage(
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
