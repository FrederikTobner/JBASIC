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

grammar JBasic;
import LBExpression, LBTokens;

program: block EOF;

statement
    : arrayDeclarationStatement
    | arraySetAtIndexStatement
    | COMMENT
    | clsStatement
    | continueStatement
    | doUntilStatement
    | doWhileStatement
    | exitStatement
    | forStatement
    | gotoStatement
    | ifStatement
    | inputStatement
    | letStatement
    | printStatement
    | repeatStatement
    | subroutineDefinitionStatement
    | subroutineInvocationStatement
    | switchStatement
    | whileStatement
    ;

/****************************************************************************
 * Code Blocks                                                              *
 ****************************************************************************/

block
    : (labeledBlock|statement (NEWLINE+ | EOF))* ;

labeledBlock
    : lab=(NUMERIC_LITERAL|IDENTIFIER) DOUBLE_DOT NEWLINE? block ;

/****************************************************************************
 * Subroutines                                                              *
 ****************************************************************************/

subroutineBody
    : (statement (NEWLINE+ | EOF))* END_KEYWORD SUB_KEYWORD ;

subroutineSignature
    : SUB_KEYWORD IDENTIFIER LEFT_PARENTHESIS (variableIdentifier (COMMA variableIdentifier)*)? RIGHT_PARENTHESIS NEWLINE ;

/****************************************************************************
 * Variables                                                                *
 ****************************************************************************/

variableIdentifier
    : IDENTIFIER variableSuffix? ;

variableSuffix
    : (DOLLAR_SIGN|PERCENT_SIGN) ;

/****************************************************************************
 * Switch statements                                                        *
 ****************************************************************************/

switchCase
    : CASE_KEYWORD (numericLiteral|stringLiteral) DOUBLE_DOT block ;

/****************************************************************************
 * Statements                                                               *
 ****************************************************************************/

arrayDeclarationStatement
    : DIM_KEYWORD variableIdentifier LEFT_BRACKET (expression (COMMA expression)*)? RIGHT_BRACKET ;

arraySetAtIndexStatement
    : variableIdentifier LEFT_BRACKET (expression (COMMA expression)*) RIGHT_BRACKET arraySetAtIndexAssignment ;

arraySetAtIndexAssignment
    : EQUALS expression ;

clsStatement
    : CLS_KEYWORD ;

continueStatement
    : CONTINUE_KEYWORD ;

doUntilStatement
    : DO_KEYWORD NEWLINE+ block UNTIL_KEYWORD expression ;

doWhileStatement
    : DO_KEYWORD NEWLINE+ block WHILE_KEYWORD expression ;

elifStatement
    : ELSE_KEYWORD IF_KEYWORD statement NEWLINE* THEN_KEYWORD NEWLINE+ block ;

elseStatement
    : ELSE_KEYWORD NEWLINE+ block ;

exitStatement
    : EXIT_KEYWORD ;

forStatement
    : FOR_KEYWORD variableIdentifier EQUALS expression TO_KEYWORD expression (STEP_KEYWORD expression)? NEWLINE+ block NEXT_KEYWORD ;

gotoStatement
    : GOTO_KEYWORD lab=(NUMERIC_LITERAL|IDENTIFIER) ;

inputStatement
    : INPUT_KEYWORD stringLiteral COMMA (variableIdentifier|expression) ;

ifStatement
    : IF_KEYWORD expression NEWLINE* THEN_KEYWORD NEWLINE+ block elifStatement* elseStatement? END_KEYWORD ;

letStatement
    : LET_KEYWORD? variableIdentifier EQUALS expression ;

printStatement
    : PRINT_KEYWORD expression (COMMA expression)* ;

repeatStatement
    : REPEAT_KEYWORD NEWLINE+ block NEWLINE* UNTIL_KEYWORD expression ;

subroutineDefinitionStatement
    : subroutineSignature subroutineBody ;

subroutineInvocationStatement
    : CALL_KEYWORD IDENTIFIER LEFT_PARENTHESIS (expression (COMMA expression)*)? RIGHT_PARENTHESIS ;

switchStatement
    : SWITCH_KEYWORD expression NEWLINE+ (switchCase)+ END_KEYWORD ;

whileStatement
    : WHILE_KEYWORD expression NEWLINE+ block END_KEYWORD ;
