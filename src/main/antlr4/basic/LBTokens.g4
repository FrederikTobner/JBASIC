lexer grammar LBTokens; // note "lexer grammar"

// operators
MULTIPLY : '*' ;
DIVIDE : '/' ;
ADD : '+' ;
SUBTRACT : '-' ;
EXP : '^' ;
MODULO : 'MOD' ;

// logical
NOT_EQUAL : '<>' ;
GREATER_THEN_EQUAL : '>=' ;
LESS_THEN_EQUAL : '<=' ;
GREATER_THEN  : '>' ;
LESS_THEN  : '<' ;
EQUALS  : '=' ;

// relational
AND : 'AND' | 'and' ;
OR  : 'OR' | 'or' ;
NOT : 'NOT' | 'not' ;

// other
COMMA  : ',' ;
LEFT_PARENTHESIS : '(' ;
RIGHT_PARENTHESIS : ')' ;

// functions
LEN : 'LEN' | 'len' ;
VAL : 'VAL' | 'val' ;
ISNAN   : 'ISNAN' | 'isnan' ;

// keywords
PRINT       : 'PRINT' | 'print' ;
INPUT       : 'INPUT' | 'input' ;
LET         : 'LET' | 'let' ;
REM         : 'REM' | 'rem' ;
IF          : 'IF' | 'if' ;
THEN        : 'THEN' | 'then' ;
ELSE        : 'ELSE' | 'else' ;
END         : 'END' | 'end';
FOR         : 'FOR' | 'for' ;
WHILE       : 'WHILE' | 'while' ;
REPEAT      : 'REPEAT' | 'repeat' ;
UNTIL       : 'UNTIL' | 'until' ;
STEP        : 'STEP' | 'step' ;
NEXT        : 'NEXT' | 'next' ;
TO          : 'TO' | 'to' ;
CONTINUE    : 'CONTINUE' | 'continue' ;
EXIT        : 'EXIT' | 'EXIT' ;

// comments
COMMENT : REM ~[\r\n]* ;

// literals
ID              : [a-zA-Z]+ ;               // match identifiers
NUMBER          : [0-9]+ ('.' [0-9]+)?;     // match integers
STRING_LITERAL   : '"' ~ ["\r\n]* '"' ;
DOLLAR          : '$' ;
NEWLINE         :'\r'? '\n' ;               // return newlines to parser (end-statement signal)
WHITE_SPACE     : [ \t]+ -> skip ;          // toss out whitespace
//NUMBER
//    : ('0' .. '9') + (('e' | 'E') NUMBER)*
//    ;