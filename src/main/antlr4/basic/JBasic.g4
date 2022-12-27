grammar JBasic;
import LBExpression, LBTokens;

program: block EOF;

statement
    : COMMENT
    | continueStatement
    | exitStatement
    | forStatement
    | ifStatement
    | inputStatement
    | letStatement
    | printStatement
    | repeatStatement
    | subroutineDefinitionStatement
    | subroutineInvocationStatement
    | whileStatement
    ;

block
    : (statement (NEWLINE+ | EOF))*
    ;

letStatement
    : LET_KEYWORD? variableDeclaration EQUALS expression
    ;

subroutineBody
    : (statement (NEWLINE+ | EOF))* END_KEYWORD SUB_KEYWORD
    ;

subroutineSignature
    : SUB_KEYWORD IDENTIFIER LEFT_PARENTHESIS (IDENTIFIER (COMMA IDENTIFIER)*)? RIGHT_PARENTHESIS NEWLINE
    ;

variableDeclaration
    : IDENTIFIER variableSuffix?
    ;


variableSuffix
    : DOLLAR_SIGN
    ;

// Statements

continueStatement
    : CONTINUE_KEYWORD
    ;

elifStatement
    : ELSE_KEYWORD IF_KEYWORD expression NEWLINE* THEN_KEYWORD NEWLINE+ block
    ;

elseStatement
    : ELSE_KEYWORD NEWLINE+ block
    ;

exitStatement
    : EXIT_KEYWORD
    ;

forStatement
    : FOR_KEYWORD variableDeclaration EQUALS expression TO_KEYWORD expression (STEP_KEYWORD expression)? NEWLINE+ block NEXT_KEYWORD
    ;

inputStatement
    : INPUT_KEYWORD stringLiteral variableDeclaration
    ;

ifStatement
    : IF_KEYWORD expression NEWLINE* THEN_KEYWORD NEWLINE+ block elifStatement* elseStatement? END_KEYWORD
    ;

printStatement
    : PRINT_KEYWORD expression
    ;

repeatStatement
    : REPEAT_KEYWORD NEWLINE+ block NEWLINE* UNTIL_KEYWORD expression
    ;

subroutineDefinitionStatement
    : subroutineSignature subroutineBody
    ;

subroutineInvocationStatement
    : CALL_KEYWORD IDENTIFIER LEFT_PARENTHESIS (expression (COMMA expression)*)? RIGHT_PARENTHESIS
    ;

whileStatement
    : WHILE_KEYWORD expression NEWLINE+ block END_KEYWORD
    ;
