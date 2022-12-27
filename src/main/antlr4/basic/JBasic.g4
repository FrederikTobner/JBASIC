grammar JBasic;
import LBExpression, LBTokens;

program: block EOF;

statement
    : letStatement
    | printStatement
    | inputStatement
    | ifStatement
    | forStatement
    | whileStatement
    | repeatStatement
    | continueStatement
    | exitStatement
    | COMMENT
    | subroutineDefinition
    | subroutineCallStatement;

block
    : (statement (NEWLINE+ | EOF))*
    ;

letStatement
    : LET_KEYWORD? variableDeclaration EQUALS expression
    ;

variableDeclaration
    : variableName variableSuffix?
    ;

variableName
    : ID
    ;

subroutineName
    : ID
    ;

variableSuffix
    : DOLLAR_SIGN
    ;

subroutineDefinition
    : subroutineSignature subroutineBody
    ;

subroutineSignature
    : SUB_KEYWORD subroutineName LEFT_PARENTHESIS (ID (COMMA ID)*)? RIGHT_PARENTHESIS NEWLINE
    ;

subroutineBody
    : block END_KEYWORD SUB_KEYWORD NEWLINE
    ;

subroutineCallStatement
    : CALL_KEYWORD subroutineName LEFT_PARENTHESIS (expression (COMMA expression)*)? RIGHT_PARENTHESIS
    ;

printStatement
    : PRINT_KEYWORD expression
    ;

inputStatement
    : INPUT_KEYWORD stringLiteral variableDeclaration
    ;

ifStatement
    : IF_KEYWORD expression NEWLINE* THEN_KEYWORD NEWLINE+ block elifStatement* elseStatement? END_KEYWORD
    ;

elifStatement
    : ELSE_KEYWORD IF_KEYWORD expression NEWLINE* THEN_KEYWORD NEWLINE+ block
    ;

elseStatement
    : ELSE_KEYWORD NEWLINE+ block
    ;

forStatement
    : FOR_KEYWORD variableDeclaration EQUALS expression TO_KEYWORD expression (STEP_KEYWORD expression)? NEWLINE+ block NEXT_KEYWORD
    ;

whileStatement
    : WHILE_KEYWORD expression NEWLINE+ block END_KEYWORD
    ;

repeatStatement
    : REPEAT_KEYWORD NEWLINE+ block NEWLINE* UNTIL_KEYWORD expression
    ;

continueStatement
    : CONTINUE_KEYWORD
    ;

exitStatement
    : EXIT_KEYWORD
    ;