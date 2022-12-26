/**
 * @file JBasicVisitor.java
 * @brief The ANTLR visitor.
 * @details The visitor visits all the nodes in our abstract syntax tree in the order they are executed.
 */

package org.jbasic;

import basic.JBasicBaseVisitor;
import basic.JBasicParser;
import basic.LBExpressionParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * @brief The ANTLR visitor.
 * @details The visitor visits all the nodes in our abstract syntax tree in the order they are executed.
 */
public class JBasicVisitor extends JBasicBaseVisitor<JBasicValue> {

    /// standard input stream
    private final InputStream stdin;
    /// standard output stream
    private final PrintStream stdout;
    /// Memory object instance that is used when the program is executed to store the variables declared in the program
    private final JBasicMemory memory;

    /// standard output stream that is used when the program is executed
    private PrintStream printStream;
    /// standard input stream that is used when the program is executed
    private BufferedReader inputStream;

    /**
     * Constructor of the JBasicVisitor
     *
     * @param memory The memory object the visitor uses
     * @param stdin  The standard input stream used by the visitor
     * @param stdout The standard output stream used by the visitor
     */
    public JBasicVisitor(JBasicMemory memory, InputStream stdin, PrintStream stdout) {
        this.stdin = stdin;
        this.stdout = stdout;
        this.memory = memory;
    }

    /**
     * Visits a JBASIC program
     *
     * @param context The parsing context of the program that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitProgram(JBasicParser.ProgramContext context) {
        init();
        try {
            return super.visitProgram(context);
        } finally {
            cleanup();
        }
    }

    /**
     * @brief Initializes the Visitor
     * @details The output stream that is used for print statements
     * and the input stream used by input statements is therefor initialized
     */
    private void init() {
        printStream = new PrintStream(stdout, true);
        inputStream = new BufferedReader(new InputStreamReader(stdin));
    }

    /**
     * @brief Cleans up after program execution
     * @details The printStream is closed
     */
    private void cleanup() {
        printStream.close();
    }

    /**
     * Visits an ID Expression '$VAR' in the abstract syntax tree
     *
     * @param context The parsing context of the id that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitId(JBasicParser.IdContext context) {
        String id = context.getText();
        return memory.get(id);
    }

    /**
     * Visits a number literal in the abstract syntax tree
     *
     * @param context The parsing context of the number that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitNumber(JBasicParser.NumberContext context) {
        return new JBasicValue(Double.parseDouble(context.getText()));
    }

    /**
     * Visits a number literal in the abstract syntax tree
     *
     * @param context The parsing context of the string that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitString(JBasicParser.StringContext context) {
        String value = context.getText();
        return new JBasicValue(value.substring(1, value.length() - 1));
    }

    //region Statements

    /**
     * Visits a statement in the abstract syntax tree
     *
     * @param context The parsing context of the statement that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitStatement(JBasicParser.StatementContext context) {
        return super.visitStatement(context);
    }


    /**
     * Visits a 'continue statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'continue statement' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitContinueStatement(JBasicParser.ContinueStatementContext context) {
        throw new ContinueException();
    }

    /**
     * Visits a 'exit statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'exit statement' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitExitStatement(JBasicParser.ExitStatementContext context) {
        throw new ExitException();
    }

    /**
     * Visits a 'for statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'for statement' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitForStatement(JBasicParser.ForStatementContext context) {
        String variableName = context.variableDeclaration().variableName().ID().getText();
        JBasicValue start = visit(context.expression(0));
        JBasicValue end = visit(context.expression(1));
        JBasicValue step = context.expression(2) != null ? visit(context.expression(2)) : new JBasicValue(1);
        for (double i = start.underlyingNumber(); i <= end.underlyingNumber(); i = i + step.underlyingNumber()) {
            memory.assign(variableName, new JBasicValue(i));
            try {
                visit(context.block());
            } catch (ContinueException ignored) {
            } catch (ExitException e) {
                break;
            }
        }
        return new JBasicValue(0);
    }

    /**
     * Visits a 'if statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'if statement' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitIfStatement(JBasicParser.IfStatementContext context) {
        JBasicValue condition = visit(context.expression());
        if (condition.isTruthy(context)) {
            return visit(context.block());
        } else {
            for (JBasicParser.ElifStatementContext elifContext : context.elifStatement()) {
                condition = visit(elifContext.expression());
                if (condition.isTruthy(context)) {
                    return visit(elifContext.block());
                }
            }
            if (context.elseStatement() != null) {
                return visit(context.elseStatement().block());
            }
        }
        return condition;
    }

    /**
     * Visits a 'input statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'input statement' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitInputStatement(JBasicParser.InputStatementContext context) {
        printStream.print(visit(context.string()).underlyingString() + " ");
        String variableName = context.variableDeclaration().getText();
        try {
            String line = inputStream.readLine();
            JBasicValue val = new JBasicValue(line);
            memory.assign(variableName, val);
            return val;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Visits a 'let statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'let statement' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitLetStatement(JBasicParser.LetStatementContext context) {
        String variableName = context.variableDeclaration().variableName().ID().getText();
        JBasicValue value = visit(context.expression());
        memory.assign(variableName, value);
        return value;
    }

    /**
     * Visits a 'print statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'print statement' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitPrintStatement(JBasicParser.PrintStatementContext context) {
        JBasicValue value = visit(context.expression());
        if (value.isANumericalValue()) {
            printStream.println(CoreUtils.numericalOutputFormat.format(value.underlyingNumber()));
        } else {
            printStream.println(value.underlyingString());
        }
        return value;
    }

    /**
     * Visits a 'repeat statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'repeat statement' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitRepeatStatement(JBasicParser.RepeatStatementContext context) {
        JBasicValue condition;
        do {
            try {
                visit(context.block());
            } catch (ContinueException ignored) {
            } catch (ExitException e) {
                break;
            } finally {
                condition = visit(context.expression());
            }
        } while (condition.isFalsy(context));
        return new JBasicValue(0);
    }

    /**
     * Visits a 'while statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'while statement' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitWhileStatement(JBasicParser.WhileStatementContext context) {
        JBasicValue condition = visit(context.expression());
        while (condition.isTruthy(context)) {
            try {
                visit(context.block());
            } catch (ContinueException ignored) {
            } catch (ExitException e) {
                break;
            } finally {
                condition = visit(context.expression());
            }
        }
        return new JBasicValue(0);
    }
    //endregion

    //region Functions

    /**
     * Visits a 'abs' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'abs function' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitAbsFunction(JBasicParser.AbsFunctionContext context) {
        JBasicValue argument = visit(context.expression());
        if (argument.isANumericalValue()) {
            return new JBasicValue(Math.abs(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ABS(). Argument is not a number");
        }
    }

    /**
     * Visits a 'acs' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'acs function' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitAcsFunction(JBasicParser.AcsFunctionContext context) {
        JBasicValue argument = visit(context.expression());
        if (argument.isANumericalValue()) {
            return new JBasicValue(Math.acos(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ACS(). Argument is not a number");
        }
    }

    /**
     * Visits a 'arc sinus' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'asn function' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitAsnFunction(JBasicParser.AsnFunctionContext context) {
        JBasicValue argument = visit(context.expression());
        if (argument.isANumericalValue()) {
            return new JBasicValue(Math.asin(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ASN(). Argument is not a number");
        }
    }

    /**
     * Visits a 'arc tangent hyperbolicus' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'ath function' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitAthFunction(JBasicParser.AthFunctionContext context) {
        JBasicValue argument = visit(context.expression());
        if (argument.isANumericalValue()) {
            return new JBasicValue(CoreUtils.arcTangentHyperbolic(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ATH(). Argument is not a number");
        }
    }

    /**
     * Visits a 'arc tangent' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'atn function' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitAtnFunction(JBasicParser.AtnFunctionContext context) {
        JBasicValue argument = visit(context.expression());
        if (argument.isANumericalValue()) {
            return new JBasicValue(Math.atan(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ATN(). Argument is not a number");
        }
    }

    /**
     * Visits a 'cosine' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'cos function' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitCosFunction(JBasicParser.CosFunctionContext context) {
        JBasicValue argument = visit(context.expression());
        if (argument.isANumericalValue()) {
            return new JBasicValue(Math.cos(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate COS(). Argument is not a number");
        }
    }

    /**
     * Visits a 'exponential' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'abs function' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitExpFunction(JBasicParser.ExpFunctionContext context) {
        JBasicValue argument = visit(context.expression());
        if (argument.isANumericalValue()) {
            return new JBasicValue(Math.exp(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate EXP(). Argument is not a number");
        }
    }

    /**
     * Visits a 'is not a number' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'isnan function' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitIsnanFunction(JBasicParser.IsnanFunctionContext context) {
        JBasicValue argument = visit(context.expression());
        return argument.isNotANumericalValue() ? JBasicValue.CreateTrueValue : JBasicValue.CreateFalseValue;
    }

    /**
     * Visits a 'length' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'len function' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitLenFunction(JBasicParser.LenFunctionContext context) {
        JBasicValue argument = visit(context.expression());
        if (argument.isAStringValue()) {
            return new JBasicValue(argument.underlyingString().length());
        } else {
            throw new TypeException("Couldn't evaluate LEN(). Argument is not a string");
        }
    }

    /**
     * Visits a 'logarithm' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'log function' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitLogFunction(JBasicParser.LogFunctionContext context) {
        JBasicValue argument = visit(context.expression());
        if (argument.isANumericalValue()) {
            return new JBasicValue(Math.log(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ABS(). Argument is not a number");
        }
    }

    /**
     * Visits a 'sine' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'sin function' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitSinFunction(JBasicParser.SinFunctionContext context) {
        JBasicValue argument = visit(context.expression());
        if (argument.isANumericalValue()) {
            return new JBasicValue(Math.sin(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ABS(). Argument is not a number");
        }
    }

    /**
     * Visits a 'square root' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'sqr function' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitSqrFunction(JBasicParser.SqrFunctionContext context) {
        JBasicValue argument = visit(context.expression());
        if (argument.isANumericalValue()) {
            return new JBasicValue(Math.sqrt(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ABS(). Argument is not a number");
        }
    }

    /**
     * Visits a 'tangent' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'tan function' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitTanFunction(JBasicParser.TanFunctionContext context) {
        JBasicValue argument = visit(context.expression());
        if (argument.isANumericalValue()) {
            return new JBasicValue(Math.tan(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ABS(). Argument is not a number");
        }
    }

    /**
     * Visits a 'val' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'val function' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitValFunction(JBasicParser.ValFunctionContext context) {
        JBasicValue argument = visit(context.expression());
        if (argument.isAStringValue()) {
            String str = argument.underlyingString();
            try {
                return new JBasicValue(Long.parseLong(str));
            } catch (NumberFormatException e) {
                return JBasicValue.CreateNotANumberValue;
            }
        }
        return argument;
    }
    //endregion

    //region Expressions

    /**
     * Visits a 'and expression' in the abstract syntax tree
     *
     * @param context The parsing context of the 'and expression' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitAndExpression(JBasicParser.AndExpressionContext context) {
        JBasicValue left = visit(context.expression(0));
        JBasicValue right = visit(context.expression(1));
        return left.and(right, context);
    }

    /**
     * Visits an 'add expression' or a 'subtract expression'  in the abstract syntax tree
     *
     * @param context The parsing context of the 'add expression' or 'subtract expression' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitAddSubExpression(JBasicParser.AddSubExpressionContext context) {
        JBasicValue left = visit(context.expression(0));
        JBasicValue right = visit(context.expression(1));
        if (context.op.getType() == LBExpressionParser.ADD) {
            return left.add(right, context);
        } else {
            return left.subtract(right, context);
        }
    }

    /**
     * Visits an 'multiply expression' or a 'divide expression'  in the abstract syntax tree
     *
     * @param context The parsing context of the 'multiply expression' or 'divide expression' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitMulDivExpression(JBasicParser.MulDivExpressionContext context) {
        JBasicValue left = visit(context.expression(0));
        JBasicValue right = visit(context.expression(1));
        if (context.op.getType() == LBExpressionParser.MULTIPLY) {
            return left.multiply(right, context);
        } else if (context.op.getType() == LBExpressionParser.DIVIDE) {
            return left.divide(right, context);
        } else {
            return left.modulo(right, context);
        }
    }

    /**
     * Visits a 'not expression' in the abstract syntax tree
     *
     * @param context The parsing context of the 'not expression' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitNotExpression(JBasicParser.NotExpressionContext context) {
        JBasicValue value = visit(context.expression());
        return value.not(context);
    }

    /**
     * Visits a 'or expression' in the abstract syntax tree
     *
     * @param context The parsing context of the 'or expression' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitOrExpression(JBasicParser.OrExpressionContext context) {
        JBasicValue left = visit(context.expression(0));
        JBasicValue right = visit(context.expression(1));
        return left.or(right, context);
    }

    /**
     * Visits a 'rel expression' in the abstract syntax tree
     *
     * @param context The parsing context of the 'rel expression' that is visited
     * @return The Value that is omitted by executing code
     */
    @Override
    public JBasicValue visitRelExpression(JBasicParser.RelExpressionContext context) {
        JBasicValue left = visit(context.expression(0));
        JBasicValue right = visit(context.expression(1));
        switch (context.op.getType()) {
            case LBExpressionParser.GREATER_THEN:
                return left.greaterThen(right, context);
            case LBExpressionParser.GREATER_THEN_EQUAL:
                return left.greaterThenEqual(right, context);
            case LBExpressionParser.LESS_THEN:
                return left.lessThen(right, context);
            case LBExpressionParser.LESS_THEN_EQUAL:
                return left.lessThenEqual(right, context);
            case LBExpressionParser.EQUALS:
                return left.equal(right, context);
            default:
                return left.notEqual(right, context);
        }
    }
    //endregion
}
