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

grammar LBExpression;
import LBTokens;

program: statement+;

statement
    : expression NEWLINE
    | NEWLINE
    ;

/****************************************************************************
 * Expressions                                                              *
 ****************************************************************************/

expression
    : expression op=(ADD|MINUS) expression                                                                      # AddSubExpression
    | expression AND_KEYWORD expression                                                                         # AndExpression
    | variableIdentifier LEFT_BRACKET (expression (COMMA expression)*) RIGHT_BRACKET                            # ArrayGetAtIndexExpression
    | expression op=(GREATER_THEN_EQUAL|GREATER_THEN|LESS_THEN_EQUAL|LESS_THEN|EQUALS|NOT_EQUAL) expression     # ComparisonExpression
    | function                                                                                                  # FunctionExpression
    | variableIdentifier                                                                                        # IdentifierExpression
    | expression op=(MULTIPLY|DIVIDE|MODULO_KEYWORD) expression                                                 # MulDivExpression
    | MINUS expression                                                                                          # NegateExpression
    | NOT_KEYWORD expression                                                                                    # NotExpression
    | numericLiteral                                                                                            # NumericLiteralExpression
    | expression OR_KEYWORD  expression                                                                         # OrExpression
    | (LEFT_PARENTHESIS expression RIGHT_PARENTHESIS)                                                           # ParenExpression
    | stringLiteral                                                                                             # StringLiteralExpression
    ;

/****************************************************************************
 * Native Functions                                                         *
 ****************************************************************************/

function
    : absFunction
    | acsFunction
    | ashFunction
    | asnFunction
    | athFunction
    | atnFunction
    | avgFunction
    | cosFunction
    | expFunction
    | lenFunction
    | listFunction
    | logFunction
    | maxFunction
    | minFunction
    | numFunction
    | rndFunction
    | sinFunction
    | sqrFunction
    | strFunction
    | sumFunction
    | tanFunction
    ;

functionCallArgs
    : (expression (COMMA expression)*)? ;

absFunction
    : ABS_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

acsFunction
    : ACS_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

ashFunction
    : ASH_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

asnFunction
    : ASN_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

athFunction
    : ATH_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

atnFunction
    : ATN_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

avgFunction
    : AVG_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

cosFunction
    : COS_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

expFunction
    : EXP_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

lenFunction
    : LEN_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

listFunction
    : LIST_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

logFunction
    : LOG_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

maxFunction
    : MAX_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

minFunction
    : MIN_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

numFunction
    : NUM_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

rndFunction
    : RND_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

sinFunction
    : SIN_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

sqrFunction
    : SQR_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

strFunction
    : STR_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

sumFunction
    : SUM_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

tanFunction
    : TAN_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

/****************************************************************************
 * Variables                                                                *
 ****************************************************************************/

variableIdentifier
    : IDENTIFIER variableSuffix?
    ;

variableSuffix
    : (DOLLAR_SIGN|PERCENT_SIGN)
    ;

/****************************************************************************
 * Literals                                                                 *
 ****************************************************************************/

numericLiteral
    : NUMERIC_LITERAL ;

stringLiteral
    : STRING_LITERAL ;