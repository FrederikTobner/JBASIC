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

/****************************************************************************
 * Operators                                                                *
 ****************************************************************************/
ADD                     : '+' ;
DIVIDE                  : '/' ;
EQUALS                  : '=' ;
GREATER_THEN            : '>' ;
GREATER_THEN_EQUAL      : '>=' ;
LESS_THEN               : '<' ;
LESS_THEN_EQUAL         : '<=' ;
MINUS                   : '-' ;
MULTIPLY                : '*' ;
NOT_EQUAL               : '<>' ;

/****************************************************************************
 * Other single character symbols                                           *
 ****************************************************************************/

COMMA                   : ',' ;
DOLLAR_SIGN             : '$' ;
DOUBLE_DOT              : ':' ;
LEFT_BRACKET            : '[' ;
LEFT_PARENTHESIS        : '(' ;
PERCENT_SIGN            : '%' ;
RIGHT_BRACKET           : ']' ;
RIGHT_PARENTHESIS       : ')' ;

/****************************************************************************
 * Native Functions                                                         *
 ****************************************************************************/

ABS_FUNCTION            : 'ABS' | 'abs' ;
ACS_FUNCTION            : 'ACS' | 'acs' ;
ASH_FUNCTION            : 'ASH' | 'ash' ;
ASN_FUNCTION            : 'ASN' | 'asn' ;
ATH_FUNCTION            : 'ATH' | 'ath' ;
ATN_FUNCTION            : 'ATN' | 'atn' ;
AVG_FUNCTION            : 'AVG' | 'avg' ;
COS_FUNCTION            : 'COS' | 'cos' ;
EXP_FUNCTION            : 'EXP' | 'exp' ;
LEN_FUNCTION            : 'LEN' | 'len' ;
LIST_FUNCTION           : 'LIST' | 'list' ;
LOG_FUNCTION            : 'LOG' | 'log' ;
MAX_FUNCTION            : 'MAX' | 'max' ;
MIN_FUNCTION            : 'MIN' | 'min' ;
NUM_FUNCTION            : 'NUM' | 'num' ;
RND_FUNCTION            : 'RND' | 'rnd' ;
SIN_FUNCTION            : 'SIN' | 'sin' ;
SQR_FUNCTION            : 'SQR' | 'sqr' ;
STR_FUNCTION            : 'STR' | 'str' ;
SUM_FUNCTION            : 'SUM' | 'sum' ;
TAN_FUNCTION            : 'TAN' | 'tan' ;

/****************************************************************************
 * Keywords                                                                 *
 ****************************************************************************/

AND_KEYWORD             : 'AND' | 'and' ;
CALL_KEYWORD            : 'CALL' | 'call' ;
CASE_KEYWORD            : 'CASE'| 'case' ;
CLS_KEYWORD             : 'CLS'  | 'cls' ;
CONTINUE_KEYWORD        : 'CONTINUE' | 'continue' ;
DIM_KEYWORD             : 'DIM' | 'dim' ;
DO_KEYWORD              : 'DO' | 'do' ;
ELSE_KEYWORD            : 'ELSE' | 'else' ;
END_KEYWORD             : 'END' | 'end';
EXIT_KEYWORD            : 'EXIT' | 'exit' ;
FOR_KEYWORD             : 'FOR' | 'for' ;
GOTO_KEYWORD            : 'GOTO' | 'goto' ;
IF_KEYWORD              : 'IF' | 'if' ;
INPUT_KEYWORD           : 'INPUT' | 'input' ;
LET_KEYWORD             : 'LET' | 'let' ;
MODULO_KEYWORD          : 'MOD'| 'mod'  ;
NEXT_KEYWORD            : 'NEXT' | 'next' ;
NOT_KEYWORD             : 'NOT' | 'not' ;
OR_KEYWORD              : 'OR'  | 'or' ;
PRINT_KEYWORD           : 'PRINT' | 'print' ;
REM_KEYWORD             : 'REM' | 'rem' ;
REPEAT_KEYWORD          : 'REPEAT' | 'repeat' ;
SUB_KEYWORD             : 'SUB' | 'sub' ;
STEP_KEYWORD            : 'STEP' | 'step' ;
SWITCH_KEYWORD          : 'SWITCH'| 'switch' ;
THEN_KEYWORD            : 'THEN' | 'then' ;
TO_KEYWORD              : 'TO' | 'to' ;
UNTIL_KEYWORD           : 'UNTIL' | 'until' ;
WHILE_KEYWORD           : 'WHILE' | 'while' ;

/****************************************************************************
 * Literals, Comments, identifiers newlines and whitespace characters       *
 ****************************************************************************/

COMMENT                 : REM_KEYWORD ~[\r\n]* ;
IDENTIFIER              : [a-zA-Z][a-zA-Z0-9_]* ;                   // match identifiers
NEWLINE                 :'\r'? '\n' ;                               // return newlines to parser (marks the end of a statement)
NUMERIC_LITERAL         : ('0' .. '9')+ ('.' ('0' .. '9')+)? ;      // match numerical literals
STRING_LITERAL          : '"' ~ ["\r\n]* '"' ;                      // match string literals
WHITE_SPACE             : [ \t]+ -> skip ;                          // ignore whitespace characters
