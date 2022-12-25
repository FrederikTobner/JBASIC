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
 * The ANTLR visitor. This does the actual job of executing the program.
 */
public class JBasicVisitor extends JBasicBaseVisitor<Value> {

    private final InputStream stdin;
    private final PrintStream stdout;
    private final Memory memory;

    private PrintStream printStream;
    private BufferedReader inputStream;

    public JBasicVisitor(Memory memory, InputStream stdin, PrintStream stdout) {
        this.stdin = stdin;
        this.stdout = stdout;
        this.memory = memory;
    }

    @Override
    public Value visitProgram(JBasicParser.ProgramContext context) {
        init();
        try {
            return super.visitProgram(context);
        } finally {
            cleanup();
        }
    }

    private void init() {
        printStream = new PrintStream(stdout, true);
        inputStream = new BufferedReader(new InputStreamReader(stdin));
    }

    private void cleanup() {
        printStream.close();
    }

    @Override
    public Value visitString(JBasicParser.StringContext context) {
        String value = context.getText();
        return new Value(value.substring(1, value.length() - 1));
    }

    @Override
    public Value visitNumber(JBasicParser.NumberContext context) {
        return new Value(Double.parseDouble(context.getText()));
    }

    @Override
    public Value visitId(JBasicParser.IdContext context) {
        String id = context.getText();
        return memory.get(id);
    }

    //region Statements

    @Override
    public Value visitStatement(JBasicParser.StatementContext context) {
        return super.visitStatement(context);
    }

    @Override
    public Value visitIfStatement(JBasicParser.IfStatementContext context) {
        Value condition = visit(context.expression());
        if (condition.isTrue(context)) {
            return visit(context.block());
        } else {
            for (JBasicParser.ElifStatementContext elifContext : context.elifStatement()) {
                condition = visit(elifContext.expression());
                if (condition.isTrue(context)) {
                    return visit(elifContext.block());
                }
            }
            if (context.elseStatement() != null) {
                return visit(context.elseStatement().block());
            }
        }
        return condition;
    }

    @Override
    public Value visitPrintStatement(JBasicParser.PrintStatementContext context) {
        Value value = visit(context.expression());
        if (value.isNumber()) {
            printStream.println(Utils.numericalOutputFormat.format(value.internalNumber()));
        } else {
            printStream.println(value.internalString());
        }
        return value;
    }

    @Override
    public Value visitInputStatement(JBasicParser.InputStatementContext context) {
        printStream.print(visit(context.string()).internalString() + " ");
        String variableName = context.variableDeclaration().getText();
        try {
            String line = inputStream.readLine();
            Value val = new Value(line);
            memory.assign(variableName, val);
            return val;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Value visitForStatement(JBasicParser.ForStatementContext context) {
        String variableName = context.variableDeclaration().variableName().ID().getText();
        Value start = visit(context.expression(0));
        Value end = visit(context.expression(1));
        Value step = context.expression(2) != null ? visit(context.expression(2)) : new Value(1);
        for (double i = start.internalNumber(); i <= end.internalNumber(); i = i + step.internalNumber()) {
            memory.assign(variableName, new Value(i));
            try {
                visit(context.block());
            } catch (ContinueLoopException ignored) {
            } catch (ExitLoopException e) {
                break;
            }
        }
        return new Value(0);
    }

    @Override
    public Value visitWhileStatement(JBasicParser.WhileStatementContext context) {
        Value condition = visit(context.expression());
        while (condition.isTrue(context)) {
            try {
                visit(context.block());
            } catch (ContinueLoopException ignored) {
            } catch (ExitLoopException e) {
                break;
            } finally {
                condition = visit(context.expression());
            }
        }
        return new Value(0);
    }

    @Override
    public Value visitRepeatStatement(JBasicParser.RepeatStatementContext context) {
        Value condition;
        do {
            try {
                visit(context.block());
            } catch (ContinueLoopException ignored) {
            } catch (ExitLoopException e) {
                break;
            } finally {
                condition = visit(context.expression());
            }
        } while (condition.isFalse(context));
        return new Value(0);
    }

    @Override
    public Value visitLetStatement(JBasicParser.LetStatementContext context) {
        String variableName = context.variableDeclaration().variableName().ID().getText();
        Value value = visit(context.expression());
        memory.assign(variableName, value);
        return value;
    }

    @Override
    public Value visitContinueStatement(JBasicParser.ContinueStatementContext context) {
        throw new ContinueLoopException();
    }

    @Override
    public Value visitExitStatement(JBasicParser.ExitStatementContext context) {
        throw new ExitLoopException();
    }
    //endregion

    //region Functions

    @Override
    public Value visitAbsFunction(JBasicParser.AbsFunctionContext context) {
        Value argument = visit(context.expression());
        if (argument.isNumber()) {
            return new Value(Math.abs(argument.internalNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ABS(). Argument is not a number");
        }
    }

    @Override
    public Value visitAcsFunction(JBasicParser.AcsFunctionContext context) {
        Value argument = visit(context.expression());
        if (argument.isNumber()) {
            return new Value(Math.acos(argument.internalNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ABS(). Argument is not a number");
        }
    }

    @Override
    public Value visitAsnFunction(JBasicParser.AsnFunctionContext context) {
        Value argument = visit(context.expression());
        if (argument.isNumber()) {
            return new Value(Math.asin(argument.internalNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ABS(). Argument is not a number");
        }
    }

    @Override
    public Value visitAtnFunction(JBasicParser.AtnFunctionContext context) {
        Value argument = visit(context.expression());
        if (argument.isNumber()) {
            return new Value(Math.atan(argument.internalNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ABS(). Argument is not a number");
        }
    }

    @Override
    public Value visitCosFunction(JBasicParser.CosFunctionContext context) {
        Value argument = visit(context.expression());
        if (argument.isNumber()) {
            return new Value(Math.cos(argument.internalNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ABS(). Argument is not a number");
        }
    }

    @Override
    public Value visitExpFunction(JBasicParser.ExpFunctionContext context) {
        Value argument = visit(context.expression());
        if (argument.isNumber()) {
            return new Value(Math.exp(argument.internalNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ABS(). Argument is not a number");
        }
    }

    @Override
    public Value visitLenFunction(JBasicParser.LenFunctionContext context) {
        Value argument = visit(context.expression());
        if (argument.isString()) {
            return new Value(argument.internalString().length());
        } else {
            throw new TypeException("Couldn't evaluate LEN(). Argument is not a string");
        }
    }

    @Override
    public Value visitLogFunction(JBasicParser.LogFunctionContext context) {
        Value argument = visit(context.expression());
        if (argument.isNumber()) {
            return new Value(Math.log(argument.internalNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ABS(). Argument is not a number");
        }
    }

    @Override
    public Value visitSinFunction(JBasicParser.SinFunctionContext context) {
        Value argument = visit(context.expression());
        if (argument.isNumber()) {
            return new Value(Math.sin(argument.internalNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ABS(). Argument is not a number");
        }
    }

    @Override
    public Value visitSqrFunction(JBasicParser.SqrFunctionContext context) {
        Value argument = visit(context.expression());
        if (argument.isNumber()) {
            return new Value(Math.sqrt(argument.internalNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ABS(). Argument is not a number");
        }
    }

    @Override
    public Value visitTanFunction(JBasicParser.TanFunctionContext context) {
        Value argument = visit(context.expression());
        if (argument.isNumber()) {
            return new Value(Math.tan(argument.internalNumber()));
        } else {
            throw new TypeException("Couldn't evaluate ABS(). Argument is not a number");
        }
    }

    @Override
    public Value visitValFunction(JBasicParser.ValFunctionContext context) {
        Value argument = visit(context.expression());
        if (argument.isString()) {
            String str = argument.internalString();
            try {
                return new Value(Long.parseLong(str));
            } catch (NumberFormatException e) {
                return Value.NaN;
            }
        }
        return argument;
    }

    @Override
    public Value visitIsnanFunction(JBasicParser.IsnanFunctionContext context) {
        Value argument = visit(context.expression());
        return argument.isNaN() ? Value.TRUE : Value.FALSE;
    }
    //endregion

    //region Expressions

    @Override
    public Value visitRelExpression(JBasicParser.RelExpressionContext context) {
        Value left = visit(context.expression(0));
        Value right = visit(context.expression(1));
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

    @Override
    public Value visitNotExpression(JBasicParser.NotExpressionContext context) {
        Value value = visit(context.expression());
        return value.not(context);
    }

    @Override
    public Value visitAndExpression(JBasicParser.AndExpressionContext context) {
        Value left = visit(context.expression(0));
        Value right = visit(context.expression(1));
        return left.and(right, context);
    }

    @Override
    public Value visitOrExpression(JBasicParser.OrExpressionContext context) {
        Value left = visit(context.expression(0));
        Value right = visit(context.expression(1));
        return left.or(right, context);
    }

    @Override
    public Value visitExpExpression(JBasicParser.ExpExpressionContext context) {
        Value left = visit(context.expression(0));
        Value right = visit(context.expression(1));
        // TODO which one is left and which is right ?
        return left.expression(right, context);
    }

    @Override
    public Value visitMulDivExpression(JBasicParser.MulDivExpressionContext context) {
        Value left = visit(context.expression(0));
        Value right = visit(context.expression(1));
        if (context.op.getType() == LBExpressionParser.MULTIPLY) {
            return left.multiply(right, context);
        } else if (context.op.getType() == LBExpressionParser.DIVIDE) {
            return left.divide(right, context);
        } else {
            return left.modulo(right, context);
        }
    }

    @Override
    public Value visitAddSubExpression(JBasicParser.AddSubExpressionContext context) {
        Value left = visit(context.expression(0));
        Value right = visit(context.expression(1));
        if (context.op.getType() == LBExpressionParser.ADD) {
            return left.add(right, context);
        } else {
            return left.subtract(right, context);
        }
    }

    //endregion

}
