grammar LBExpression;
import LBTokens;

program: statement+;

statement
    : expression NEWLINE
    | NEWLINE
    ;

expression
    : expression op=(ADD|SUBTRACT) expression                                                                   # AddSubExpression
    | expression AND expression                                                                                 # AndExpression
    | function                                                                                                  # FuncExpression
    | id                                                                                                        # IdExpression
    | expression op=(MULTIPLY|DIVIDE|MODULO) expression                                                         # MulDivExpression
    | NOT expression                                                                                            # NotExpression
    | number                                                                                                    # NumberExpression
    | expression OR expression                                                                                  # OrExpression
    | (LEFT_PARENTHESIS expression RIGHT_PARENTHESIS)                                                           # ParenExpression
    | expression op=(GREATER_THEN_EQUAL|GREATER_THEN|LESS_THEN_EQUAL|LESS_THEN|EQUALS|NOT_EQUAL) expression     # RelExpression
    | string                                                                                                    # StringExpression
    ;

function
    : absFunction
    | acsFunction
    | asnFunction
    | athFunction
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
    : STRING_LITERAL ;

number
    : NUMBER ;

id
    : ID ;

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

isnanFunction
    : ISNAN_FUNCTION LEFT_PARENTHESIS expression RIGHT_PARENTHESIS ;

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
