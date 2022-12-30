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

lexer grammar LBTokens; // note "lexer grammar"

// operators
ADD                     : '+' ;
DIVIDE                  : '/' ;
MINUS                   : '-' ;
MULTIPLY                : '*' ;

// logical
EQUALS                  : '=' ;
GREATER_THEN            : '>' ;
GREATER_THEN_EQUAL      : '>=' ;
LESS_THEN               : '<' ;
LESS_THEN_EQUAL         : '<=' ;
NOT_EQUAL               : '!=' ;

// other
COMMA                   : ',' ;
LEFT_PARENTHESIS        : '(' ;
RIGHT_PARENTHESIS       : ')' ;
LEFT_BRACKET            : '[' ;
RIGHT_BRACKET           : ']' ;

// functions
ABS_FUNCTION            : 'ABS' | 'abs' ;
ACS_FUNCTION            : 'ACS' | 'acs' ;
ASN_FUNCTION            : 'ASN' | 'asn' ;
ATH_FUNCTION            : 'ATH' | 'ath' ;
ATN_FUNCTION            : 'ATN' | 'atn' ;
AVG_FUNCTION            : 'AVG' | 'avg' ;
COS_FUNCTION            : 'COS' | 'cos' ;
EXP_FUNCTION            : 'EXP' | 'exp' ;
LEN_FUNCTION            : 'LEN' | 'len' ;
LOG_FUNCTION            : 'LOG' | 'log' ;
MAX_FUNCTION            : 'MAX' | 'max' ;
MIN_FUNCTION            : 'MIN' | 'min' ;
SIN_FUNCTION            : 'SIN' | 'sin' ;
SQR_FUNCTION            : 'SQR' | 'sqr' ;
SUM_FUNCTION            : 'SUM' | 'sum' ;
TAN_FUNCTION            : 'TAN' | 'tan' ;
VAL_FUNCTION            : 'VAL' | 'val' ;

// base keywords
CALL_KEYWORD            : 'CALL' | 'call' ;
CONTINUE_KEYWORD        : 'CONTINUE' | 'continue' ;
DIM_KEYWORD             : 'DIM' | 'dim' ;
ELSE_KEYWORD            : 'ELSE' | 'else' ;
END_KEYWORD             : 'END' | 'end';
EXIT_KEYWORD            : 'EXIT' | 'exit' ;
FOR_KEYWORD             : 'FOR' | 'for' ;
IF_KEYWORD              : 'IF' | 'if' ;
INPUT_KEYWORD           : 'INPUT' | 'input' ;
LET_KEYWORD             : 'LET' | 'let' ;
NEXT_KEYWORD            : 'NEXT' | 'next' ;
PRINT_KEYWORD           : 'PRINT' | 'print' ;
REM_KEYWORD             : 'REM' | 'rem' ;
REPEAT_KEYWORD          : 'REPEAT' | 'repeat' ;
SUB_KEYWORD             : 'SUB' | 'sub' ;
STEP_KEYWORD            : 'STEP' | 'step' ;
THEN_KEYWORD            : 'THEN' | 'then' ;
TO_KEYWORD              : 'TO' | 'to' ;
UNTIL_KEYWORD           : 'UNTIL' | 'until' ;
WHILE_KEYWORD           : 'WHILE' | 'while' ;

// mathematical keywords
MODULO_KEYWORD          : 'MOD' ;

// relational keywords
AND_KEYWORD             : 'AND' | 'and' ;
NOT_KEYWORD             : 'NOT' | 'not' ;
OR_KEYWORD              : 'OR'  | 'or' ;

GOTO_KEYWORD            : 'GOTO' | 'goto' ;

// comments
COMMENT                 : REM_KEYWORD ~[\r\n]* ;

// Label
DOUBLE_DOT              : ':' ;

// literals
PERCENT_SIGN            : '%' ;
DOLLAR_SIGN             : '$' ;
IDENTIFIER              : [a-zA-Z][a-zA-Z0-9_]* ;       // match identifiers
NEWLINE                 :'\r'? '\n' ;                   // return newlines to parser (end-statement signal)
NUMERIC_LITERAL         : [0-9]+ ('.' [0-9]+)? ;        // match numerical literals
STRING_LITERAL          : '"' ~ ["\r\n]* '"' ;          // match string literals
WHITE_SPACE             : [ \t]+ -> skip ;              // toss out whitespace
