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
    | COMMENT;

block
    : (statement (NEWLINE+ | EOF))*
    ;

letStatement
    : LET? variableDeclaration EQUALS expression
    ;

variableDeclaration
    : variableName variableSuffix?
    ;

variableName
    : ID
    ;

variableSuffix
    : DOLLAR
    ;

printStatement
    : PRINT expression;

inputStatement
    : INPUT string variableDeclaration
    ;

ifStatement
    : IF expression NEWLINE* THEN NEWLINE+ block elifStatement* elseStatement? END
    ;

elifStatement
    : ELSE IF expression NEWLINE* THEN NEWLINE+ block
    ;

elseStatement
    : ELSE NEWLINE+ block
    ;

forStatement
    : FOR variableDeclaration EQUALS expression TO expression (STEP expression)? NEWLINE+ block NEXT
    ;

whileStatement
    : WHILE expression NEWLINE+ block END
    ;

repeatStatement
    : REPEAT NEWLINE+ block NEWLINE* UNTIL expression
    ;

continueStatement
    : CONTINUE
    ;

exitStatement
    : EXIT
    ;
