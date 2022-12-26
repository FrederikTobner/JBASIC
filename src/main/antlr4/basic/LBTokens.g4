lexer grammar LBTokens; // note "lexer grammar"

// operators
ADD : '+' ;
DIVIDE : '/' ;
MODULO : 'MOD' ;
MULTIPLY : '*' ;
SUBTRACT : '-' ;

// logical
EQUALS  : '=' ;
GREATER_THEN  : '>' ;
GREATER_THEN_EQUAL : '>=' ;
LESS_THEN  : '<' ;
LESS_THEN_EQUAL : '<=' ;
NOT_EQUAL : '!=' ;

// relational
AND : 'AND' | 'and' ;
NOT : '!' ;
OR  : 'OR' | 'or' ;

// other
COMMA  : ',' ;
LEFT_PARENTHESIS : '(' ;
RIGHT_PARENTHESIS : ')' ;

// functions
ABS_FUNCTION             : 'ABS' | 'abs' ;
ACS_FUNCTION             : 'ACS' | 'acs' ;
ASN_FUNCTION             : 'ASN' | 'asn' ;
ATH_FUNCTION             : 'ATH' | 'ath' ;
ATN_FUNCTION             : 'ATN' | 'atn' ;
COS_FUNCTION             : 'COS' | 'cos' ;
EXP_FUNCTION             : 'EXP' | 'exp' ;
ISNAN_FUNCTION           : 'ISNAN' | 'isnan' ;
LEN_FUNCTION             : 'LEN' | 'len' ;
LOG_FUNCTION             : 'LOG' | 'log' ;
SIN_FUNCTION             : 'SIN' | 'sin' ;
SQR_FUNCTION             : 'SQR' | 'sqr' ;
TAN_FUNCTION             : 'TAN' | 'tan' ;
VAL_FUNCTION             : 'VAL' | 'val' ;

// keywords
CONTINUE    : 'CONTINUE' | 'continue' ;
ELSE        : 'ELSE' | 'else' ;
END         : 'END' | 'end';
EXIT        : 'EXIT' | 'exit' ;
FOR         : 'FOR' | 'for' ;
IF          : 'IF' | 'if' ;
INPUT       : 'INPUT' | 'input' ;
LET         : 'LET' | 'let' ;
NEXT        : 'NEXT' | 'next' ;
PRINT       : 'PRINT' | 'print' ;
REM         : 'REM' | 'rem' ;
REPEAT      : 'REPEAT' | 'repeat' ;
STEP        : 'STEP' | 'step' ;
THEN        : 'THEN' | 'then' ;
TO          : 'TO' | 'to' ;
UNTIL       : 'UNTIL' | 'until' ;
WHILE       : 'WHILE' | 'while' ;

// comments
COMMENT : REM ~[\r\n]* ;

// literals
DOLLAR_SIGN     : '$' ;
ID              : [a-zA-Z]+ ;               // match identifiers
NEWLINE         :'\r'? '\n' ;               // return newlines to parser (end-statement signal)
NUMBER          : [0-9]+ ('.' [0-9]+)?;     // match integers
STRING_LITERAL  : '"' ~ ["\r\n]* '"' ;
WHITE_SPACE     : [ \t]+ -> skip ;          // toss out whitespace
