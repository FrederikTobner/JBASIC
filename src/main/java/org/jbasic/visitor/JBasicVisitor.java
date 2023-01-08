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

package org.jbasic.visitor;

import jbasic.JBasicBaseVisitor;
import jbasic.JBasicParser;
import jbasic.LBExpressionParser;
import org.antlr.v4.runtime.RuleContext;
import org.jbasic.core.IOFormatter;
import org.jbasic.core.RandomNumbersGenerator;
import org.jbasic.core.Trigonometry;
import org.jbasic.core.guard.ArraySafeguard;
import org.jbasic.core.guard.FunctionSafeguard;
import org.jbasic.core.guard.NumericalValueSafeguard;
import org.jbasic.core.guard.ValueTypeSafeguard;
import org.jbasic.core.guard.VariableSafeguard;
import org.jbasic.interpreter.JBasicInterpreterState;
import org.jbasic.languageModels.JBasicSubroutine;
import org.jbasic.languageModels.JBasicValue;
import org.jbasic.programFlow.ContinueException;
import org.jbasic.programFlow.ExitException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @brief The ANTLR visitor.
 * @details The visitor visits all the nodes in our abstract syntax tree in the order they are executed.
 */
public class JBasicVisitor extends JBasicBaseVisitor<JBasicValue> {

    /// standard input stream used by the visitor
    private final InputStream stdin;
    /// standard output stream used by the visitor
    private final PrintStream stdout;
    /// State of the interpreter
    private final JBasicInterpreterState state;

    /// standard output stream that is used when the program is executed
    private PrintStream printStream;
    /// standard input stream that is used when the program is executed
    private BufferedReader inputStream;

    /**
     * Constructor of the JBasicVisitor
     *
     * @param state The interpreter state object used by the visitor
     * @param stdin  The standard input stream used by the visitor
     * @param stdout The standard output stream used by the visitor
     */
    public JBasicVisitor(JBasicInterpreterState state, InputStream stdin, PrintStream stdout) {
        this.stdin = stdin;
        this.stdout = stdout;
        this.state = state;
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
        }
        finally {
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
    public JBasicValue visitVariableIdentifier(JBasicParser.VariableIdentifierContext context) {
        return this.state.getVariableValue(context.getText(), context);
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

    /**
     * Visits a labeled block in the syntax tree
     *
     * @param context The parsing context of the labeled block that is visited
     * @return The Value that is omitted by visiting the labeled block in the abstract syntax tree
     */
    @Override
    public JBasicValue visitLabeledBlock(JBasicParser.LabeledBlockContext context) {
        if (context.lab.getType() == LBExpressionParser.NUMERIC_LITERAL) {
            NumericalValueSafeguard.guaranteeIsWhole("Digits are not allowed in a label",
                    Double.parseDouble(context.NUMERIC_LITERAL().getText()),
                    context);

            this.state.addLabel(context.NUMERIC_LITERAL().getText(), context.block());
        }
        else {
            this.state.addLabel(context.IDENTIFIER().getText(), context.block());
        }

        return this.visit(context.block());
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
     * Visits a 'array declaration statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'array declaration statement' that is visited
     * @return The Value that is omitted by visiting the 'array declaration statement'
     */
    @Override
    public JBasicValue visitArrayDeclarationStatement(JBasicParser.ArrayDeclarationStatementContext context) {
        // Determine name of the array
        String arrayName = context.variableIdentifier().getText();
        ArraySafeguard.guaranteeArrayDimensionCountIsValid(context.expression());
        List<Integer> dimensions = new ArrayList<>();
        context.expression().forEach(expressionContext -> {
            JBasicValue dimension = this.visit(expressionContext);
            ArraySafeguard.guaranteeArrayDimensionIsValid(dimension, expressionContext);
            dimensions.add((int) dimension.underlyingNumber());
        });
        JBasicValue array;
            switch (dimensions.size()) {
                case 1:
                    array = new JBasicValue(new JBasicValue[dimensions.get(0)]);
                    this.state.assignToVariable(arrayName, array);
                    return array;
                case 2:
                    array = new JBasicValue(new JBasicValue[dimensions.get(0)][dimensions.get(1)]);
                    this.state.assignToVariable(arrayName, array);
                    return array;
                case 3:
                    array = new JBasicValue(new JBasicValue[dimensions.get(0)][dimensions.get(1)][dimensions.get(2)]);
                    this.state.assignToVariable(arrayName, array);
                    return array;
                default:
                    // Unreachable
                    throw new IllegalStateException("Unexpected value: " + dimensions.size());
            }
    }

    /**
     * Visits a 'array set at index statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'array set at index statement' that is visited
     * @return The Value that is omitted by visiting the 'array set at index statement'
     */
    @Override
    public JBasicValue visitArraySetAtIndexStatement(JBasicParser.ArraySetAtIndexStatementContext context) {
        String arrayName = context.variableIdentifier().getText();
        JBasicValue array = this.state.getVariableValue(arrayName, context);
        VariableSafeguard.guaranteeVariableIsDefined(array, arrayName, context.variableIdentifier());
        ValueTypeSafeguard.guaranteeValueIsArray("Could not execute set expression", array, context.variableIdentifier());
        ArraySafeguard.guaranteeArrayDimensionCountIsValid(context.expression());
        ArraySafeguard.guaranteeArrayDimensionsMatch(array, context.expression());
        List<Integer> dimensions = new ArrayList<>();
        context.expression().forEach(expressionContext -> {
            JBasicValue dimension = this.visit(expressionContext);
            ArraySafeguard.guaranteeArrayDimensionIsValid(dimension, expressionContext);
            dimensions.add((int) dimension.underlyingNumber() - 1);
        });
        JBasicValue value = this.visit(context.arraySetAtIndexAssignment().expression());
        if(context.variableIdentifier().variableSuffix() != null) {
            VariableSafeguard.guaranteeVariableSuffixIsNotViolated(value, context.variableIdentifier().variableSuffix());
        }
        switch (dimensions.size()) {
            case 1:
                return array.underlyingOneDimensionalArray()[dimensions.get(0)] = value;
            case 2:
                return array.underlyingTwoDimensionalArray()[dimensions.get(0)][dimensions.get(1)] = value;
            case 3:
                return array.underlyingThreeDimensionalArray()[dimensions.get(0)][dimensions.get(1)][dimensions.get(2)] = value;
            default:
                // Unreachable
                throw new IllegalStateException("Unexpected value: " + context.expression().size());
        }
    }

    /**
     * Visits a 'continue statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'continue statement' that is visited
     * @return The Value that is omitted by visiting the continue statement
     */
    @Override
    public JBasicValue visitContinueStatement(JBasicParser.ContinueStatementContext context) throws ContinueException {
        throw new ContinueException();
    }

    /**
     * Visits a 'continue statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'continue statement' that is visited
     * @return The Value that is omitted by visiting the continue statement
     */
    @Override
    public JBasicValue visitClsStatement(JBasicParser.ClsStatementContext context) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        return new JBasicValue(0);
    }

    /**
     * Visits a 'do while statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'while statement' that is visited
     * @return The Value that is omitted by visiting the while statement
     */
    @Override
    public JBasicValue visitDoUntilStatement(JBasicParser.DoUntilStatementContext context) {
        JBasicValue condition;
        do {
            try {
                this.visit(context.block());
            }
            catch (ContinueException ignored) {
            }
            catch (ExitException e) {
                break;
            }
            finally {
                condition = this.visit(context.expression());
            }
        } while (condition.isFalsy(context));
        return new JBasicValue(0);
    }

    /**
     * Visits a 'do while statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'while statement' that is visited
     * @return The Value that is omitted by visiting the while statement
     */
    @Override
    public JBasicValue visitDoWhileStatement(JBasicParser.DoWhileStatementContext context) {
        JBasicValue condition;
        do {
            try {
                this.visit(context.block());
            }
            catch (ContinueException ignored) {
            }
            catch (ExitException e) {
                break;
            }
            finally {
                condition = this.visit(context.expression());
            }
        } while (condition.isTruthy(context));
        return new JBasicValue(0);
    }

    /**
     * Visits a 'exit statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'exit statement' that is visited
     * @return The Value that is omitted by visiting the exit statement
     */
    @Override
    public JBasicValue visitExitStatement(JBasicParser.ExitStatementContext context) throws ExitException {
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
        String variableName = context.variableIdentifier().IDENTIFIER().getText();
        JBasicValue start = this.visit(context.expression(0));
        JBasicValue end = this.visit(context.expression(1));
        JBasicValue step = context.expression(2) != null ? this.visit(context.expression(2)) : new JBasicValue(1);
        for (double counter = start.underlyingNumber();
             counter <= end.underlyingNumber();
             counter = counter + step.underlyingNumber()) {
            this.state.assignToVariable(variableName, new JBasicValue(counter));
            try {
                this.visit(context.block());
            }
            catch (ContinueException ignored) {
            }
            catch (ExitException e) {
                break;
            }
        }
        return new JBasicValue(0);
    }

    /**
     * Visits a 'goto statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'goto statement' that is visited
     * @return The Value that is omitted by visiting the 'goto statement'
     */
    @Override
    public JBasicValue visitGotoStatement(JBasicParser.GotoStatementContext context) {
        return this.state.gotoLabel(context.lab.getType() == LBExpressionParser.IDENTIFIER  ?
                context.IDENTIFIER().getText() :
                context.NUMERIC_LITERAL().getText(), this, context);
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
        }
        else {
            for (JBasicParser.ElifStatementContext elifContext : context.elifStatement()) {
                condition = this.visit(elifContext.statement());
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
    public JBasicValue visitInputStatement(JBasicParser.InputStatementContext context) throws RuntimeException {
        this.printStream.print(this.visit(context.stringLiteral()).underlyingString() + " ");
        String variableName = context.variableIdentifier().getText();
        try {
            String line = this.inputStream.readLine();
            JBasicValue val = new JBasicValue(line);
            this.state.assignToVariable(variableName, val);
            return val;
        }
        catch (IOException e) {
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
        String variableName = context.variableIdentifier().getText();
        JBasicValue value = this.visit(context.expression());
        if(context.variableIdentifier().variableSuffix() != null) {
            VariableSafeguard.guaranteeVariableSuffixIsNotViolated(value, context.variableIdentifier().variableSuffix());
        }
        this.state.assignToVariable(variableName, value);
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

        for (JBasicParser.ExpressionContext expressionContext :context.expression()) {
            this.visit(expressionContext).printValue(this.printStream, context.expression().size() != 1);
            this.printStream.println();
        }
        return new JBasicValue(0);
    }

    /**
     * Visits a 'repeat statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'repeat statement' that is visited
     * @return The Value that is omitted by visiting the repeat statement
     */
    @Override
    public JBasicValue visitRepeatStatement(JBasicParser.RepeatStatementContext context) {
        JBasicValue condition = this.visit(context.expression());
        while (condition.isFalsy(context)) {
            try {
                this.visit(context.block());
            }
            catch (ContinueException ignored) {
            }
            catch (ExitException e) {
                break;
            }
            finally {
                condition = this.visit(context.expression());
            }
        }
        return new JBasicValue(0);
    }

    /**
     * Visits a 'subroutine definition statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'subroutine definition statement' that is visited
     * @return 0
     */
    @Override
    public JBasicValue visitSubroutineDefinitionStatement(JBasicParser.SubroutineDefinitionStatementContext context) {
        // Adds all the argument from the subroutine signature to the List
        this.state.defineSubroutine(context.subroutineSignature().IDENTIFIER().getText(),
                new JBasicSubroutine(context.subroutineSignature().variableIdentifier().stream()
                        .map(RuleContext::getText).toArray(String[]::new),
                        context.subroutineBody().statement().toArray(JBasicParser.StatementContext[]::new)),
                context);
        return new JBasicValue(0);
    }

    /**
     * Visits a 'subroutine invocation statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'subroutine invocation statement' that is visited
     * @return 0
     */
    @Override
    public JBasicValue visitSubroutineInvocationStatement(JBasicParser.SubroutineInvocationStatementContext context) {

        this.state.invokeSubroutine(context.IDENTIFIER().getText(),
                context.expression().stream().map(this::visit).collect(Collectors.toList()),
                this, context);
        return new JBasicValue(0);
    }

    /**
     * Visits a 'switch statement' in the abstract syntax tree
     *
     * @param context The parsing context of the 'switch statement' that is visited
     * @return The Value that is omitted by visiting the switch statement
     */
    @Override
    public JBasicValue visitSwitchStatement(JBasicParser.SwitchStatementContext context) {
        JBasicValue value = this.visit(context.expression());
        for (JBasicParser.SwitchCaseContext caseContext : context.switchCase()) {
            if (caseContext.numericLiteral() != null) {
                JBasicValue o = this.visitNumericLiteral(caseContext.numericLiteral());
                if (o.equals(value)) {
                    try {
                        this.visit(caseContext.block());
                    }
                    catch (ExitException e) {
                        break;
                    }
                }
            }
            else {
                if (this.visit(caseContext.stringLiteral()).equals(value)) {
                    try {
                        this.visit(caseContext.block());
                    }
                    catch (ExitException e) {
                        break;
                    }
                }
            }
        }
        return new JBasicValue(0);
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
            }
            catch (ContinueException ignored) {
            }
            catch (ExitException e) {
                break;
            }
            finally {
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
        FunctionSafeguard.guaranteeArityIsNotViolated("ABS", context.functionCallArgs(),
                (parameterCount -> parameterCount == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression(0));
        ValueTypeSafeguard.guaranteeValueIsNumerical("Could not call ABS", argument, context);
        return new JBasicValue(Math.abs(argument.underlyingNumber()));
    }

    /**
     * Visits a 'acs' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'acs function' that is visited
     * @return The Value that is omitted by visiting the acs function
     */
    @Override
    public JBasicValue visitAcsFunction(JBasicParser.AcsFunctionContext context) {
        FunctionSafeguard.guaranteeArityIsNotViolated("ACS", context.functionCallArgs(),
                (parameterCount -> parameterCount == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        ValueTypeSafeguard.guaranteeValueIsNumerical("Could not call ACS", argument, context);
        return new JBasicValue(Math.acos(argument.underlyingNumber()));
    }

    /**
     * Visits a 'arc sine hyperbolicus' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'ath function' that is visited
     * @return The Value that is omitted by visiting the 'ath function'
     */
    @Override
    public JBasicValue visitAshFunction(JBasicParser.AshFunctionContext context) {
        FunctionSafeguard.guaranteeArityIsNotViolated("ATH", context.functionCallArgs(),
                (parameterCount -> parameterCount == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        ValueTypeSafeguard.guaranteeValueIsNumerical("Could not call ATH", argument, context);
        return new JBasicValue(Trigonometry.inverseSineHyperbolicus(argument.underlyingNumber()));
    }

    /**
     * Visits a 'arc sinus' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'asn function' that is visited
     * @return The Value that is omitted by visiting the 'asn function'
     */
    @Override
    public JBasicValue visitAsnFunction(JBasicParser.AsnFunctionContext context) {
        FunctionSafeguard.guaranteeArityIsNotViolated("ASN", context.functionCallArgs(),
                (parameterCount -> parameterCount == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        ValueTypeSafeguard.guaranteeValueIsNumerical("Could not call ASN", argument, context);
        return new JBasicValue(Math.asin(argument.underlyingNumber()));
    }

    /**
     * Visits a 'arc tangent hyperbolicus' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'ath function' that is visited
     * @return The Value that is omitted by visiting the 'ath function'
     */
    @Override
    public JBasicValue visitAthFunction(JBasicParser.AthFunctionContext context) {
        FunctionSafeguard.guaranteeArityIsNotViolated("ATH", context.functionCallArgs(),
                (parameterCount -> parameterCount == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        ValueTypeSafeguard.guaranteeValueIsNumerical("Could not call ATH", argument, context);
        return new JBasicValue(Trigonometry.areaTangentHyperbolicus(argument.underlyingNumber()));
    }

    /**
     * Visits a 'arc tangent' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'atn function' that is visited
     * @return The Value that is omitted by visiting the 'atn function'
     */
    @Override
    public JBasicValue visitAtnFunction(JBasicParser.AtnFunctionContext context) {
        FunctionSafeguard.guaranteeArityIsNotViolated("ATN", context.functionCallArgs(),
                (parameterCount -> parameterCount == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        ValueTypeSafeguard.guaranteeValueIsNumerical("Could not call ATN", argument, context);
        return new JBasicValue(Math.atan(argument.underlyingNumber()));
    }

    /**
     * Visits a 'avg' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'avg function' that is visited
     * @return The Value that is omitted by visiting the 'avg function'
     */
    @Override
    public JBasicValue visitAvgFunction(JBasicParser.AvgFunctionContext context) {
        FunctionSafeguard.guaranteeArityIsNotViolated("AVG", context.functionCallArgs(),
                (parameterCount -> parameterCount != 0));
        double sum = 0;
        for (JBasicParser.ExpressionContext expression : context.functionCallArgs().expression()) {
            JBasicValue value = this.visit(expression);
            ValueTypeSafeguard.guaranteeValueIsNumerical("Could not call AVG", value, context);
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
        FunctionSafeguard.guaranteeArityIsNotViolated("COS", context.functionCallArgs(),
                (parameterCount -> parameterCount == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        ValueTypeSafeguard.guaranteeValueIsNumerical("Could not call COS", argument, context);
        return new JBasicValue(Math.cos(argument.underlyingNumber()));
    }

    /**
     * Visits a 'exponential' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'abs function' that is visited
     * @return The Value that is omitted by visiting the 'exp function'
     */
    @Override
    public JBasicValue visitExpFunction(JBasicParser.ExpFunctionContext context) {
        FunctionSafeguard.guaranteeArityIsNotViolated("EXP", context.functionCallArgs(),
                (parameterCount -> parameterCount == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        ValueTypeSafeguard.guaranteeValueIsNumerical("Could not call EXP", argument, context);
        return new JBasicValue(Math.exp(argument.underlyingNumber()));
    }

    /**
     * Visits a 'length' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'len function' that is visited
     * @return The Value that is omitted by visiting the 'len function'
     */
    @Override
    public JBasicValue visitLenFunction(JBasicParser.LenFunctionContext context) {
        FunctionSafeguard.guaranteeArityIsNotViolated("LEN", context.functionCallArgs(),
                (parameterCount -> parameterCount == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        ValueTypeSafeguard.guaranteeValueIsString("Could not call LEN", argument, context);
        return new JBasicValue(argument.underlyingString().length());
    }

    /**
     * Visits a 'list expression'  in the abstract syntax tree
     *
     * @param context The parsing context of the 'list expression' that is visited
     * @return The Value that is omitted by visiting the list expression
     */
    @Override
    public JBasicValue visitListFunction(JBasicParser.ListFunctionContext context) {
        return new JBasicValue(this.state.getCurrentScript());
    }

    /**
     * Visits a 'logarithm' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'log function' that is visited
     * @return The Value that is omitted by visiting the 'log function'
     */
    @Override
    public JBasicValue visitLogFunction(JBasicParser.LogFunctionContext context) {
        FunctionSafeguard.guaranteeArityIsNotViolated("LOG", context.functionCallArgs(),
                (parameterCount -> parameterCount == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        ValueTypeSafeguard.guaranteeValueIsNumerical("Could not call LOG", argument, context);
        return new JBasicValue(Math.log(argument.underlyingNumber()));
    }

    /**
     * Visits a 'max' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'max function' that is visited
     * @return The Value that is omitted by visiting the 'max function'
     */
    @Override
    public JBasicValue visitMaxFunction(JBasicParser.MaxFunctionContext context) {
        FunctionSafeguard.guaranteeArityIsNotViolated("MAX", context.functionCallArgs(),
                (parameterCount -> parameterCount != 0));
        double maxValue = Double.MIN_VALUE;
        for (JBasicParser.ExpressionContext expression : context.functionCallArgs().expression()) {
            JBasicValue value = this.visit(expression);
            ValueTypeSafeguard.guaranteeValueIsNumerical("Could not call MAX", value, context);
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
        FunctionSafeguard.guaranteeArityIsNotViolated("MIN", context.functionCallArgs(),
                (parameterCount -> parameterCount != 0));
        double minValue = Double.MAX_VALUE;
        List<JBasicParser.ExpressionContext> expressionContexts = context.functionCallArgs().expression();
        for (JBasicParser.ExpressionContext expression : expressionContexts) {
            JBasicValue value = this.visit(expression);
            ValueTypeSafeguard.guaranteeValueIsNumerical("Could not call MIN", value, context);
            minValue = Math.min(minValue, value.underlyingNumber());
        }
        return new JBasicValue(minValue);
    }

    /**
     * Visits a 'num' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'num function' that is visited
     * @return The Value that is omitted by visiting the 'num function'
     */
    @Override
    public JBasicValue visitNumFunction(JBasicParser.NumFunctionContext context) {
        FunctionSafeguard.guaranteeArityIsNotViolated("NUM", context.functionCallArgs(),
                (parameterCount -> parameterCount == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        if (argument.isAStringValue()) {
            String str = argument.underlyingString();
            try {
                return new JBasicValue(Long.parseLong(str));
            }
            catch (NumberFormatException e) {
                return JBasicValue.NullValue;
            }
        }
        return argument;
    }

    /**
     * Visits a 'random' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'random function' that is visited
     * @return The Value that is omitted by visiting the 'random function'
     */
    @Override
    public JBasicValue visitRndFunction(JBasicParser.RndFunctionContext context) {
        FunctionSafeguard.guaranteeArityIsNotViolated("RND", context.functionCallArgs(),
                (parameterCount -> parameterCount == 2));
        JBasicValue minValue = this.visit(context.functionCallArgs().expression().get(0));
        ValueTypeSafeguard.guaranteeValueIsNumerical("Could not call RND", minValue, context.functionCallArgs().expression().get(0));
        JBasicValue maxValue = this.visit(context.functionCallArgs().expression().get(1));
        ValueTypeSafeguard.guaranteeValueIsNumerical("Could not call RND", maxValue, context.functionCallArgs().expression().get(1));
        return new JBasicValue(RandomNumbersGenerator.doubleRandomWithinRange(minValue.underlyingNumber(), maxValue.underlyingNumber()));
    }

    /**
     * Visits a 'sine' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'sin function' that is visited
     * @return The Value that is omitted by visiting the 'sin function'
     */
    @Override
    public JBasicValue visitSinFunction(JBasicParser.SinFunctionContext context) {
        FunctionSafeguard.guaranteeArityIsNotViolated("SIN", context.functionCallArgs(),
                (parameterCount -> parameterCount == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        ValueTypeSafeguard.guaranteeValueIsNumerical("Could not call SIN", argument, context);
        return new JBasicValue(Math.sin(argument.underlyingNumber()));
    }

    /**
     * Visits a 'square root' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'sqr function' that is visited
     * @return The Value that is omitted by visiting the 'sqr function'
     */
    @Override
    public JBasicValue visitSqrFunction(JBasicParser.SqrFunctionContext context) {
        FunctionSafeguard.guaranteeArityIsNotViolated("SQR", context.functionCallArgs(),
                (parameterCount -> parameterCount == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        ValueTypeSafeguard.guaranteeValueIsNumerical("Could not call SQR", argument, context);
        return new JBasicValue(Math.sqrt(argument.underlyingNumber()));
    }

    /**
     * Visits a 'str' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'str function' that is visited
     * @return The Value that is omitted by visiting the 'str function'
     */
    @Override
    public JBasicValue visitStrFunction(JBasicParser.StrFunctionContext context) {
        FunctionSafeguard.guaranteeArityIsNotViolated("STR", context.functionCallArgs(),
                (parameterCount -> parameterCount == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        if (argument.isANumericalValue()) {
            Double number = argument.underlyingNumber();
            return new JBasicValue(IOFormatter.numericalOutputFormat.format(number));
        }
        return argument;
    }

    /**
     * Visits a 'sum' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'sum function' that is visited
     * @return The Value that is omitted by visiting the 'sum function'
     */
    @Override
    public JBasicValue visitSumFunction(JBasicParser.SumFunctionContext context) {
        FunctionSafeguard.guaranteeArityIsNotViolated("SUM", context.functionCallArgs(),
                (parameterCount -> parameterCount != 0));
        BigDecimal sum = BigDecimal.valueOf(0);
        for (JBasicParser.ExpressionContext expression : context.functionCallArgs().expression()) {
            JBasicValue value = this.visit(expression);
            ValueTypeSafeguard.guaranteeValueIsNumerical("Could not call SUM", value, context);
            sum = sum.add(BigDecimal.valueOf(value.underlyingNumber()));
        }
        return new JBasicValue(Double.parseDouble(String.valueOf(sum)));
    }

    /**
     * Visits a 'tangent' function call in the abstract syntax tree
     *
     * @param context The parsing context of the 'tan function' that is visited
     * @return The Value that is omitted by visiting the 'tan function'
     */
    @Override
    public JBasicValue visitTanFunction(JBasicParser.TanFunctionContext context) {
        FunctionSafeguard.guaranteeArityIsNotViolated("TAN", context.functionCallArgs(),
                (parameterCount -> parameterCount == 1));
        JBasicValue argument = this.visit(context.functionCallArgs().expression().get(0));
        ValueTypeSafeguard.guaranteeValueIsNumerical("Could not call TAN",argument, context);
        return new JBasicValue(Math.tan(argument.underlyingNumber()));
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
     * Visits an 'array get at index expression' in the abstract syntax tree
     *
     * @param context The parsing context of the 'array get at index expression' that is visited
     * @return The Value that is omitted by visiting the 'array get at index expression'
     */
    @Override
    public JBasicValue visitArrayGetAtIndexExpression(JBasicParser.ArrayGetAtIndexExpressionContext context) {
        String arrayName = context.variableIdentifier().getText();
        JBasicValue array = this.state.getVariableValue(arrayName, context);
        VariableSafeguard.guaranteeVariableIsDefined(array, arrayName, context.variableIdentifier());
        ArraySafeguard.guaranteeArrayDimensionCountIsValid(context.expression());
        ArraySafeguard.guaranteeArrayDimensionsMatch(array, context.expression());
        List<Integer> dimensions = new ArrayList<>();
        context.expression().forEach(expressionContext -> {
            JBasicValue dimension = this.visit(expressionContext);
            ArraySafeguard.guaranteeArrayDimensionIsValid(dimension, expressionContext);
            dimensions.add((int)dimension.underlyingNumber() - 1);
        });
        switch (dimensions.size()) {
            case 1:
                return array.underlyingOneDimensionalArray()[dimensions.get(0)];
            case 2:
                return array.underlyingTwoDimensionalArray()[dimensions.get(0)][dimensions.get(1)];
            case 3:
                return array.underlyingThreeDimensionalArray()[dimensions.get(0)][dimensions.get(1)][dimensions.get(2)];
            default:
                // Unreachable
                throw new IllegalStateException("Unexpected value: " + context.expression().size());
        }
    }

    /**
     * Visits a 'comparison expression' in the abstract syntax tree
     *
     * @param context The parsing context of the 'comparison expression' that is visited
     * @return The Value that is omitted by visiting the 'comparison expression'
     */
    @Override
    public JBasicValue visitComparisonExpression(JBasicParser.ComparisonExpressionContext context) {
        JBasicValue left = this.visit(context.expression(0));
        JBasicValue right = this.visit(context.expression(1));
        switch (context.op.getType()) {
            case LBExpressionParser.GREATER_THEN:
                return left.greaterThen(right, context);
            case LBExpressionParser.GREATER_THEN_EQUAL:
                return left.greaterThenEqual(right, context);
            case LBExpressionParser.LESS_THEN:
                return left.lessThen(right, context);
            default:
                return left.lessThenEqual(right, context);
        }
    }

    /**
     * Visits a 'equality expression' in the abstract syntax tree
     *
     * @param context The parsing context of the 'equality expression' that is visited
     * @return The Value that is omitted by visiting the 'equality expression'
     */
    @Override
    public JBasicValue visitEqualityExpression(JBasicParser.EqualityExpressionContext context) {
        JBasicValue left = this.visit(context.expression(0));
        JBasicValue right = this.visit(context.expression(1));
        if (context.op.getType() == LBExpressionParser.EQUALS) {
            return left.equal(right, context);
        }
        return left.notEqual(right, context);
    }

    /**
     * Visits a 'factor expression' in the abstract syntax tree
     *
     * @param context The parsing context of the 'factor expression' that is visited
     * @return The Value that is omitted by visiting the 'factor expression'
     */
    @Override
    public JBasicValue visitFactorExpression(JBasicParser.FactorExpressionContext context) {
        JBasicValue left = this.visit(context.expression(0));
        JBasicValue right = this.visit(context.expression(1));
        if (context.op.getType() == LBExpressionParser.MULTIPLY) {
            return left.multiply(right, context);
        }
        else if (context.op.getType() == LBExpressionParser.DIVIDE) {
            return left.divide(right, context);
        }
        else {
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
        return this.visit(context.expression()).negate(context);
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
     * Visits an 'term expression' in the abstract syntax tree
     *
     * @param context The parsing context of the 'term expression' that is visited
     * @return The Value that is omitted by visiting the 'term expression'
     */
    @Override
    public JBasicValue visitTermExpression(JBasicParser.TermExpressionContext context) {
        JBasicValue left = this.visit(context.expression(0));
        JBasicValue right = this.visit(context.expression(1));
        if (context.op.getType() == LBExpressionParser.ADD) {
            return left.add(right, context);
        }
        else {
            return left.subtract(right, context);
        }
    }

    //endregion
}
