grammar LBExpression;
import LBTokens;

program: statement+;

statement
    : expression NEWLINE
    | NEWLINE
    ;

expression
    : string                                                                                                    # StringExpression
    | number                                                                                                    # NumberExpression
    | function                                                                                                  # FuncExpression
    | id                                                                                                        # IdExpression
    | (LEFT_PARENTHESIS expression RIGHT_PARENTHESIS)                                                           # ParenExpression
    | expression op=(MULTIPLY|DIVIDE|MODULO) expression                                                         # MulDivExpression
    | expression op=(ADD|SUBTRACT) expression                                                                   # AddSubExpression
    | expression op=(GREATER_THEN_EQUAL|GREATER_THEN|LESS_THEN_EQUAL|LESS_THEN|EQUALS|NOT_EQUAL) expression     # RelExpression
    | NOT expression                                                                                            # NotExpression
    | expression AND expression                                                                                 # AndExpression
    | expression OR expression                                                                                  # OrExpression
    | <assoc=right> expression EXP expression                                                                   # ExpExpression
    ;

function
    : absFunction
    | acsFunction
    | asnFunction
    | atnFunction
    | cosFunction
    | expFunction
    | isnanFunction
    | lenFunction
    | logFunction
    | sinFunction
    | sqrFunction
    | tanFunction
    | valFunction
    ;

string
    : STRING_LITERAL
    ;

number
    : NUMBER
    ;

id
    : ID
    ;

absFunction
    : ABS_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS
    ;

acsFunction
    : ACS_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS
    ;

asnFunction
    : ASN_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS
    ;

atnFunction
    : ATN_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS
    ;

cosFunction
    : COS_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS
    ;

expFunction
    : EXP_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS
    ;

isnanFunction
    : ISNAN_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS
    ;

lenFunction
    : LEN_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS
    ;

logFunction
    : LOG_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS
    ;

sinFunction
    : SIN_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS
    ;

sqrFunction
    : SQR_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS
    ;

tanFunction
    : TAN_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS
    ;

valFunction
    : VAL_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS
    ;
