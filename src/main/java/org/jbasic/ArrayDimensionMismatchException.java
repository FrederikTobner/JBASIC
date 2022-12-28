package org.jbasic;

import org.antlr.v4.runtime.ParserRuleContext;

@SuppressWarnings("serial")
public class ArrayDimensionMismatchException extends InterpreterBaseException {
    public ArrayDimensionMismatchException(String message, ParserRuleContext context) {
        super(message, context);
    }
}
