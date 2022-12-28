package org.jbasic;

import org.antlr.v4.runtime.ParserRuleContext;

@SuppressWarnings("serial")
public class UndefinedVariableException extends InterpreterBaseException{
    public UndefinedVariableException(String message, ParserRuleContext context) {
        super(message, context);
    }
}
