grammar LBExpression;
import LBTokens;

program: statement+;

statement
    : expression NEWLINE
    | NEWLINE
    ;

expression
    : expression op=(ADD|SUBTRACT) expression                                                                   # AddSubExpression
    | expression AND_KEYWORD expression                                                                         # AndExpression
    | function                                                                                                  # FuncExpression
    | identifier                                                                                                # IdentifierExpression
    | expression op=(MULTIPLY|DIVIDE|MODULO_KEYWORD) expression                                                 # MulDivExpression
    | SUBTRACT expression                                                                                       # NegateExpression
    | NOT_KEYWORD expression                                                                                    # NotExpression
    | numericLiteral                                                                                            # NumericLiteralExpression
    | expression OR_KEYWORD  expression                                                                         # OrExpression
    | (LEFT_PARENTHESIS expression RIGHT_PARENTHESIS)                                                           # ParenExpression
    | expression op=(GREATER_THEN_EQUAL|GREATER_THEN|LESS_THEN_EQUAL|LESS_THEN|EQUALS|NOT_EQUAL) expression     # RelExpression
    | stringLiteral                                                                                             # StringLiteralExpression
    ;

function
    : absFunction
    | acsFunction
    | asnFunction
    | athFunction
    | atnFunction
    | cosFunction
    | expFunction
    | lenFunction
    | logFunction
    | sinFunction
    | sqrFunction
    | tanFunction
    | valFunction
    ;

identifier
    : ID ;

numericLiteral
    : NUMERIC_LITERAL ;

stringLiteral
    : STRING_LITERAL ;

absFunction
    : ABS_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS ;

acsFunction
    : ACS_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS ;

asnFunction
    : ASN_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS ;

athFunction
    : ATH_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS ;

atnFunction
    : ATN_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS ;

cosFunction
    : COS_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS ;

expFunction
    : EXP_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS ;

lenFunction
    : LEN_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS ;

logFunction
    : LOG_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS ;

sinFunction
    : SIN_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS ;

sqrFunction
    : SQR_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS ;

tanFunction
    : TAN_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS ;

valFunction
    : VAL_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS ;
