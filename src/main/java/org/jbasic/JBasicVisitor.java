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
import java.util.ArrayList;
import java.util.List;

/**
 * @brief The ANTLR visitor.
 * @details The visitor visits all the nodes in our abstract syntax tree in the order they are executed.
 */
public class JBasicVisitor extends JBasicBaseVisitor<JBasicValue> {

    /// standard input stream used by the visitor
    private final InputStream stdin;
    /// standard output stream used by the visitor
    private final PrintStream stdout;
    /// Memory object instance that is used when the program is executed to store the variables declared in the program
    private final JBasicInterpreterState memory;

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
    public JBasicVisitor(JBasicInterpreterState memory, InputStream stdin, PrintStream stdout) {
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
        this.initialize();
        try {
            return super.visitProgram(context);
        } finally {
            this.cleanup();
        }
    }

    /**
     * @brief Initializes the Visitor
     * @details The output stream that is used for print statements
     * and the input stream used by input statements is therefor initialized
     */
    private void initialize() {
        this.printStream = new PrintStream(this.stdout, true);
        this.inputStream = new BufferedReader(new InputStreamReader(this.stdin));
    }

    /**
     * @brief Cleans up after program execution
     * @details The printStream is closed
     */
    private void cleanup() {
        this.printStream.close();
    }

    /**
     * Visits an ID Expression '$VAR' in the abstract syntax tree
     *
     * @param context The parsing context of the id that is visited
     * @return The Value that is omitted by visiting an id in the abstract syntax tree
     */
    @Override
    public JBasicValue visitIdentifier(JBasicParser.IdentifierContext context) {
        String id = context.getText();
        return this.memory.getVariable(id);
    }

    /**
     * Visits a number literal in the abstract syntax tree
     *
     * @param context The parsing context of the number that is visited
     * @return The Value that is omitted by visiting the numerical literal in the abstract syntax tree
     */
    @Override
    public JBasicValue visitNumericLiteral(JBasicParser.NumericLiteralContext context) {
        return new JBasicValue(Double.parseDouble(context.getText()));
    }

    /**
     * Visits a number literal in the abstract syntax tree
     *
     * @param context The parsing context of the string that is visited
     * @return The Value that is omitted by visiting the string literal in the abstract syntax tree
     */
    @Override
    public JBasicValue visitStringLiteral(JBasicParser.StringLiteralContext context) {
        String value = context.getText();
        return new JBasicValue(value.substring(1, value.length() - 1));
    }

    //region Statements

    /**
     * Visits a statement in the abstract syntax tree
     *
     * @param context The parsing context of the statement that is visited
     * @return The Value that is omitted by visiting the statement
     */
    @Override
    public JBasicValue visitStatement(JBasicParser.StatementContext context) {
        return super.visitStatement(context);
    }


    /**
     * Visits a 'continue statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'continue statement' that is visited
     * @return The Value that is omitted by visiting the continue statement
     */
    @Override
    public JBasicValue visitContinueStatement(JBasicParser.ContinueStatementContext context) {
        throw new ContinueException();
    }

    /**
     * Visits a 'exit statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'exit statement' that is visited
     * @return The Value that is omitted by visiting the exit statement
     */
    @Override
    public JBasicValue visitExitStatement(JBasicParser.ExitStatementContext context) {
        throw new ExitException();
    }

    /**
     * Visits a 'for statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'for statement' that is visited
     * @return The Value that is omitted by visiting the for statement
     */
    @Override
    public JBasicValue visitForStatement(JBasicParser.ForStatementContext context) {
        String variableName = context.variableDeclaration().IDENTIFIER().getText();
        JBasicValue start = this.visit(context.expression(0));
        JBasicValue end = this.visit(context.expression(1));
        JBasicValue step = context.expression(2) != null ? this.visit(context.expression(2)) : new JBasicValue(1);
        for (double i = start.underlyingNumber(); i <= end.underlyingNumber(); i = i + step.underlyingNumber()) {
            this.memory.assignToVariable(variableName, new JBasicValue(i));
            try {
                this.visit(context.block());
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
     * @return The Value that is omitted by  visiting the if statement
     */
    @Override
    public JBasicValue visitIfStatement(JBasicParser.IfStatementContext context) {
        JBasicValue condition = this.visit(context.expression());
        if (condition.isTruthy(context)) {
            return this.visit(context.block());
        } else {
            for (JBasicParser.ElifStatementContext elifContext : context.elifStatement()) {
                condition = this.visit(elifContext.expression());
                if (condition.isTruthy(context)) {
                    return this.visit(elifContext.block());
                }
            }
            if (context.elseStatement() != null) {
                return this.visit(context.elseStatement().block());
            }
        }
        return condition;
    }

    /**
     * Visits a 'input statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'input statement' that is visited
     * @return The Value that is omitted by visiting the input statement
     */
    @Override
    public JBasicValue visitInputStatement(JBasicParser.InputStatementContext context) {
        this.printStream.print(this.visit(context.stringLiteral()).underlyingString() + " ");
        String variableName = context.variableDeclaration().getText();
        try {
            String line = this.inputStream.readLine();
            JBasicValue val = new JBasicValue(line);
            this.memory.assignToVariable(variableName, val);
            return val;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Visits a 'let statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'let statement' that is visited
     * @return The Value that is omitted by visiting the let statement
     */
    @Override
    public JBasicValue visitLetStatement(JBasicParser.LetStatementContext context) {
        String variableName = context.variableDeclaration().IDENTIFIER().getText();
        JBasicValue value = this.visit(context.expression());
        this.memory.assignToVariable(variableName, value);
        return value;
    }

    /**
     * Visits a 'print statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'print statement' that is visited
     * @return The Value that is omitted by visiting the print statement
     */
    @Override
    public JBasicValue visitPrintStatement(JBasicParser.PrintStatementContext context) {
        JBasicValue value = this.visit(context.expression());
        if (value.isANumericalValue()) {
            this.printStream.println(CoreUtils.numericalOutputFormat.format(value.underlyingNumber()));
        } else {
            this.printStream.println(value.underlyingString());
        }
        return value;
    }

    /**
     * Visits a 'repeat statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'repeat statement' that is visited
     * @return The Value that is omitted by visiting the repeat statement
     */
    @Override
    public JBasicValue visitRepeatStatement(JBasicParser.RepeatStatementContext context) {
        JBasicValue condition;
        do {
            try {
                this.visit(context.block());
            } catch (ContinueException ignored) {
            } catch (ExitException e) {
                break;
            } finally {
                condition = this.visit(context.expression());
            }
        } while (condition.isFalsy(context));
        return new JBasicValue(0);
    }

    @Override
    public JBasicValue visitSubroutineDefinitionStatement(JBasicParser.SubroutineDefinitionStatementContext context) {

        List<String> arguments = new ArrayList<>();
        // Adds all the argument from the subroutine signature to the List
        context.subroutineSignature().IDENTIFIER().subList(1, context.subroutineSignature().IDENTIFIER().size())
                .forEach(argument -> arguments.add(argument.getText()));
        this.memory.defineSubroutine(context.subroutineSignature().IDENTIFIER(0).getText(),
                new JBasicSubroutine(arguments.toArray(new String[0]), context.subroutineBody().statement()),
                context);
        return new JBasicValue(1);
    }

    @Override
    public JBasicValue visitSubroutineInvocationStatement(JBasicParser.SubroutineInvocationStatementContext context) {

        List<JBasicValue> parameters = new ArrayList<>();
        // Parses parameters
        context.expression().forEach(expression -> parameters.add(this.visit(expression)));
        this.memory.invokeSubroutine(context.IDENTIFIER().getText(), parameters, this, context);
        return new JBasicValue(1);
    }

    /**
     * Visits a 'while statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'while statement' that is visited
     * @return The Value that is omitted by visiting the while statement
     */
    @Override
    public JBasicValue visitWhileStatement(JBasicParser.WhileStatementContext context) {
        JBasicValue condition = this.visit(context.expression());
        while (condition.isTruthy(context)) {
            try {
                this.visit(context.block());
            } catch (ContinueException ignored) {
            } catch (ExitException e) {
                break;
            } finally {
                condition = this.visit(context.expression());
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
     * @return The Value that is omitted by visiting the abs function
     */
    @Override
    public JBasicValue visitAbsFunction(JBasicParser.AbsFunctionContext context) {
        CoreUtils.assertArity("ABS", context.functionCallArgs(), (i -> i == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression(0));
        if (argument.isANumericalValue()) {
            return new JBasicValue(Math.abs(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ABS(). Argument is not a number", context);
        }
    }

    /**
     * Visits a 'acs' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'acs function' that is visited
     * @return The Value that is omitted by visiting the acs function
     */
    @Override
    public JBasicValue visitAcsFunction(JBasicParser.AcsFunctionContext context) {
        CoreUtils.assertArity("ACS", context.functionCallArgs(), (i -> i == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        if (argument.isANumericalValue()) {
            return new JBasicValue(Math.acos(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ACS(). Argument is not a number", context);
        }
    }

    /**
     * Visits a 'arc sinus' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'asn function' that is visited
     * @return The Value that is omitted by visiting the 'asn function'
     */
    @Override
    public JBasicValue visitAsnFunction(JBasicParser.AsnFunctionContext context) {
        CoreUtils.assertArity("ASN", context.functionCallArgs(), (i -> i == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        if (argument.isANumericalValue()) {
            return new JBasicValue(Math.asin(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ASN(). Argument is not a number", context);
        }
    }

    /**
     * Visits a 'arc tangent hyperbolicus' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'ath function' that is visited
     * @return The Value that is omitted by visiting the 'ath function'
     */
    @Override
    public JBasicValue visitAthFunction(JBasicParser.AthFunctionContext context) {
        CoreUtils.assertArity("ATH", context.functionCallArgs(), (i -> i == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        if (argument.isANumericalValue()) {
            return new JBasicValue(CoreUtils.areaTangentHyperbolicus(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ATH(). Argument is not a number", context);
        }
    }

    /**
     * Visits a 'arc tangent' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'atn function' that is visited
     * @return The Value that is omitted by visiting the 'atn function'
     */
    @Override
    public JBasicValue visitAtnFunction(JBasicParser.AtnFunctionContext context) {
        CoreUtils.assertArity("ATN", context.functionCallArgs(), (i -> i == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        if (argument.isANumericalValue()) {
            return new JBasicValue(Math.atan(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ATN(). Argument is not a number", context);
        }
    }

    /**
     * Visits a 'avg' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'avg function' that is visited
     * @return The Value that is omitted by visiting the 'avg function'
     */
    @Override
    public JBasicValue visitAvgFunction(JBasicParser.AvgFunctionContext context) {
        CoreUtils.assertArity("AVG", context.functionCallArgs(), (i -> i != 0));
        double sum = 0;
        for (JBasicParser.ExpressionContext expression : context.functionCallArgs().expression()) {
            JBasicValue value = this.visit(expression);
            if (!value.isANumericalValue()) {
                throw new TypeException("AVG can only be called with numerical values", context);
            }
            sum += value.underlyingNumber();
        }
        return new JBasicValue(sum / context.functionCallArgs().expression().size());
    }

    /**
     * Visits a 'cosine' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'cos function' that is visited
     * @return The Value that is omitted by visiting the 'cos function'
     */
    @Override
    public JBasicValue visitCosFunction(JBasicParser.CosFunctionContext context) {
        CoreUtils.assertArity("COS", context.functionCallArgs(), (i -> i == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        if (argument.isANumericalValue()) {
            return new JBasicValue(Math.cos(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate COS(). Argument is not a number", context);
        }
    }

    /**
     * Visits a 'exponential' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'abs function' that is visited
     * @return The Value that is omitted by visiting the 'exp function'
     */
    @Override
    public JBasicValue visitExpFunction(JBasicParser.ExpFunctionContext context) {
        CoreUtils.assertArity("EXP", context.functionCallArgs(), (i -> i == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        if (argument.isANumericalValue()) {
            return new JBasicValue(Math.exp(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate EXP(). Argument is not a number", context);
        }
    }

    /**
     * Visits a 'length' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'len function' that is visited
     * @return The Value that is omitted by visiting the 'len function'
     */
    @Override
    public JBasicValue visitLenFunction(JBasicParser.LenFunctionContext context) {
        CoreUtils.assertArity("LEN", context.functionCallArgs(), (i -> i == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        if (argument.isAStringValue()) {
            return new JBasicValue(argument.underlyingString().length());
        } else {
            throw new TypeException("Couldn't evaluate LEN(). Argument is not a string", context);
        }
    }

    /**
     * Visits a 'logarithm' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'log function' that is visited
     * @return The Value that is omitted by visiting the 'log function'
     */
    @Override
    public JBasicValue visitLogFunction(JBasicParser.LogFunctionContext context) {
        CoreUtils.assertArity("LOG", context.functionCallArgs(), (i -> i == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        if (argument.isANumericalValue()) {
            return new JBasicValue(Math.log(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate LOG(). Argument is not a number", context);
        }
    }

    /**
     * Visits a 'max' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'max function' that is visited
     * @return The Value that is omitted by visiting the 'max function'
     */
    @Override
    public JBasicValue visitMaxFunction(JBasicParser.MaxFunctionContext context) {
        CoreUtils.assertArity("MAX", context.functionCallArgs(), (i -> i != 0));
        double maxValue = Double.MIN_VALUE;
        for (JBasicParser.ExpressionContext expression : context.functionCallArgs().expression()) {
            JBasicValue value = this.visit(expression);
            if (!value.isANumericalValue()) {
                throw new TypeException("MAX can only be called with numerical values", context);
            }
            maxValue = Math.max(maxValue, value.underlyingNumber());
        }
        return new JBasicValue(maxValue);
    }

    /**
     * Visits a 'min' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'min function' that is visited
     * @return The Value that is omitted by visiting the 'min function'
     */
    @Override
    public JBasicValue visitMinFunction(JBasicParser.MinFunctionContext context) {
        CoreUtils.assertArity("MIN", context.functionCallArgs(), (i -> i != 0));
        double minValue = Double.MAX_VALUE;
        for (JBasicParser.ExpressionContext expression : context.functionCallArgs().expression()) {
            JBasicValue value = this.visit(expression);
            if (!value.isANumericalValue()) {
                throw new TypeException("MIN can only be called with numerical values", context);
            }
            minValue = Math.min(minValue, value.underlyingNumber());
        }
        return new JBasicValue(minValue);
    }

    /**
     * Visits a 'sine' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'sin function' that is visited
     * @return The Value that is omitted by visiting the 'sin function'
     */
    @Override
    public JBasicValue visitSinFunction(JBasicParser.SinFunctionContext context) {
        CoreUtils.assertArity("SIN", context.functionCallArgs(), (i -> i == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        if (argument.isANumericalValue()) {
            return new JBasicValue(Math.sin(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate SIN(). Argument is not a number", context);
        }
    }

    /**
     * Visits a 'square root' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'sqr function' that is visited
     * @return The Value that is omitted by visiting the 'sqr function'
     */
    @Override
    public JBasicValue visitSqrFunction(JBasicParser.SqrFunctionContext context) {
        CoreUtils.assertArity("SQR", context.functionCallArgs(), (i -> i == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        if (argument.isANumericalValue()) {
            return new JBasicValue(Math.sqrt(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate SQR(). Argument is not a number", context);
        }
    }

    /**
     * Visits a 'sum' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'sum function' that is visited
     * @return The Value that is omitted by visiting the 'sum function'
     */
    @Override
    public JBasicValue visitSumFunction(JBasicParser.SumFunctionContext context) {
        CoreUtils.assertArity("SUM", context.functionCallArgs(), (i -> i != 0));
        double sum = 0;
        for (JBasicParser.ExpressionContext expression : context.functionCallArgs().expression()) {
            JBasicValue value = this.visit(expression);
            if (!value.isANumericalValue()) {
                throw new TypeException("SUM can only be called with numerical values", context);
            }
            sum += value.underlyingNumber();
        }
        return new JBasicValue(sum);
    }

    /**
     * Visits a 'tangent' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'tan function' that is visited
     * @return The Value that is omitted by visiting the 'tan function'
     */
    @Override
    public JBasicValue visitTanFunction(JBasicParser.TanFunctionContext context) {
        CoreUtils.assertArity("TAN", context.functionCallArgs(), (i -> i == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        if (argument.isANumericalValue()) {
            return new JBasicValue(Math.tan(argument.underlyingNumber()));
        } else {
            throw new TypeException("Couldn't evaluate TAN(). Argument is not a number", context);
        }
    }

    /**
     * Visits a 'val' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'val function' that is visited
     * @return The Value that is omitted by visiting the 'val function'
     */
    @Override
    public JBasicValue visitValFunction(JBasicParser.ValFunctionContext context) {
        CoreUtils.assertArity("VAL", context.functionCallArgs(), (i -> i == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        if (argument.isAStringValue()) {
            String str = argument.underlyingString();
            try {
                return new JBasicValue(Long.parseLong(str));
            } catch (NumberFormatException e) {
                return JBasicValue.NullValue;
            }
        }
        return argument;
    }
    //endregion

    //region Expressions

    /**
     * Visits an 'and expression' in the abstract syntax tree
     *
     * @param context The parsing context of the 'and expression' that is visited
     * @return The Value that is omitted by visiting the and expression
     */
    @Override
    public JBasicValue visitAndExpression(JBasicParser.AndExpressionContext context) {
        JBasicValue left = this.visit(context.expression(0));
        JBasicValue right = this.visit(context.expression(1));
        return left.and(right, context);
    }

    /**
     * Visits an 'add expression' or a 'subtract expression'  in the abstract syntax tree
     *
     * @param context The parsing context of the 'add expression' or 'subtract expression' that is visited
     * @return The Value that is omitted by visiting the addition or subtraction expression
     */
    @Override
    public JBasicValue visitAddSubExpression(JBasicParser.AddSubExpressionContext context) {
        JBasicValue left = this.visit(context.expression(0));
        JBasicValue right = this.visit(context.expression(1));
        if (context.op.getType() == LBExpressionParser.ADD) {
            return left.add(right, context);
        } else {
            return left.subtract(right, context);
        }
    }

    /**
     * Visits a 'multiply expression' or a 'divide expression'  in the abstract syntax tree
     *
     * @param context The parsing context of the 'multiply expression' or 'divide expression' that is visited
     * @return The Value that is omitted by visiting the multiply or div expression
     */
    @Override
    public JBasicValue visitMulDivExpression(JBasicParser.MulDivExpressionContext context) {
        JBasicValue left = this.visit(context.expression(0));
        JBasicValue right = this.visit(context.expression(1));
        if (context.op.getType() == LBExpressionParser.MULTIPLY) {
            return left.multiply(right, context);
        } else if (context.op.getType() == LBExpressionParser.DIVIDE) {
            return left.divide(right, context);
        } else {
            return left.modulo(right, context);
        }
    }

    /**
     * Visits a 'negate expression' in the abstract syntax tree
     *
     * @param context The parsing context of the 'negate expression' that is visited
     * @return The Value that is omitted by visiting the 'negate expression'
     */
    @Override
    public JBasicValue visitNegateExpression(JBasicParser.NegateExpressionContext context) {
        JBasicValue value = this.visit(context.expression());
        return value.negate(context);
    }

    /**
     * Visits a 'not expression' in the abstract syntax tree
     *
     * @param context The parsing context of the 'not expression' that is visited
     * @return The Value that is omitted by visiting the 'not expression'
     */
    @Override
    public JBasicValue visitNotExpression(JBasicParser.NotExpressionContext context) {
        JBasicValue value = this.visit(context.expression());
        return value.not(context);
    }

    /**
     * Visits an 'or expression' in the abstract syntax tree
     *
     * @param context The parsing context of the 'or expression' that is visited
     * @return The Value that is omitted by visiting the 'or expression'
     */
    @Override
    public JBasicValue visitOrExpression(JBasicParser.OrExpressionContext context) {
        JBasicValue left = this.visit(context.expression(0));
        JBasicValue right = this.visit(context.expression(1));
        return left.or(right, context);
    }

    /**
     * Visits a 'relative comparison expression' in the abstract syntax tree
     *
     * @param context The parsing context of the 'rel expression' that is visited
     * @return The Value that is omitted by visiting the 'rel expression'
     */
    @Override
    public JBasicValue visitRelExpression(JBasicParser.RelExpressionContext context) {
        JBasicValue left = this.visit(context.expression(0));
        JBasicValue right = this.visit(context.expression(1));
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
