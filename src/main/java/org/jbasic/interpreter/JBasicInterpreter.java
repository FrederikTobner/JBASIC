/****************************************************************************
 * Copyright (C) 2022 by Frederik Tobner                                    *
 *                                                                          *
 * This file is part of JBASIC.                                             *
 *                                                                          *
 * Permission to use, copy, modify, and distribute this software and its    *
 * documentation under the terms of the GNU General Public License is       *
 * hereby granted.                                                          *
 * No representations are made about the suitability of this software for   *
 * any purpose.                                                             *
 * It is provided "as is" without express or implied warranty.              *
 * See the <"https://www.gnu.org/licenses/gpl-3.0.html">GNU General Public  *
 * License for more details.                                                *
 ****************************************************************************/

/**
 * @file JBasicInterpreter.java
 * @brief The JBASIC interpreter
 */
package org.jbasic.interpreter;

import basic.JBasicLexer;
import basic.JBasicParser;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jbasic.core.IOFormatUtils;
import org.jbasic.visitor.JBasicVisitor;
import org.jbasic.error.ErrorListener;
import org.jbasic.error.InterpreterBaseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @brief The JBASIC interpreter
 */
public class JBasicInterpreter {

    /// The standard input stream used by the interpreter
    private final InputStream stdin;
    /// The standard output stream used by the interpreter
    private final PrintStream stdoutPrint;
    /// The standard error output stream used by the interpreter
    private final PrintStream stderrPrint;
    /// The Memory object instance used to store variables
    private JBasicInterpreterState state;

    /**
     * @brief Constructor a new Interpreter object instance
     * @param stdin The standard input stream used by the interpreter
     * @param stdout The standard output stream used by the interpreter
     * @param stderr The standard error stream used by the interpreter
     */
    public JBasicInterpreter(InputStream stdin, OutputStream stdout, OutputStream stderr) {
        this.stdin = stdin;
        this.stdoutPrint = new PrintStream(stdout, true);
        this.stderrPrint = new PrintStream(stderr, true);
    }

    /**
     * @brief Executes a JBASIC program
     * @param programInput The JBASIC program that is executed
     */
    public void run(InputStream programInput) throws IOException {
        // Wrapping the sourcecode in a ANTLRInputStream
        CharStream input = CharStreams.fromStream(programInput);
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
        parser.addErrorListener(new ErrorListener(this.stderrPrint));
        try {
            // We create an abstract syntax tree from the tokens
            ParseTree tree = parser.program();
            // Memory used by the program
            this.state = new JBasicInterpreterState();
            // Creating the visitor to visit the nodes in the abstract syntax tree
            JBasicVisitor visitor = new JBasicVisitor(this.state, this.stdin, this.stdoutPrint);
            // Executing the program with the visitor
            visitor.visit(tree);
        }
        catch (InterpreterBaseException exception) {
            this.stderrPrint.println(exception.getMessage());
        }
        catch (ParseCancellationException exception) {
            if (exception.getCause() instanceof InputMismatchException) {
                InputMismatchException exceptionCause = (InputMismatchException) exception.getCause();
                String syntaxErrorMessage = IOFormatUtils.formatErrorMessage(
                        exceptionCause.getOffendingToken().getLine(),
                        exceptionCause.getOffendingToken().getCharPositionInLine(),
                        "Syntax error");
                this.stderrPrint.println(syntaxErrorMessage);
            }
        }
    }

    /**
     * @return The memory of the interpreter
     * @brief Gets the memory associated with the interpreter
     * @details The memory associated with the interpreter is a simple hashtable that stores all the variables that are declared in the program
     */
    public JBasicInterpreterState getState() {
        return this.state;
    }

    /**
     * Deallocates the memory used by the interpreter and
     * erases all the label and function definitions
     */
    public void clear() {
        this.state.freeMemory();
        this.state.eraseLabels();
        this.state.eraseSubroutines();
    }
}
