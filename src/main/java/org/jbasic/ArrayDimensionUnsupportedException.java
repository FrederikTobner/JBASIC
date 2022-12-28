package org.jbasic;

import org.antlr.v4.runtime.ParserRuleContext;

@SuppressWarnings("serial")
public class ArrayDimensionUnsupportedException extends InterpreterBaseException {
    public ArrayDimensionUnsupportedException(String message, ParserRuleContext context) {
        super(message, context);
    }
}
