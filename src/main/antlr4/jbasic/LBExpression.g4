grammar LBExpression;
import LBTokens;

program: statement+;

statement
    : expression NEWLINE
    | NEWLINE
    ;

expression
    : expression op=(ADD|MINUS) expression                                                                      # AddSubExpression
    | expression AND_KEYWORD expression                                                                         # AndExpression
    | variableIdentifier LEFT_PARENTHESIS (expression (COMMA expression)*) RIGHT_PARENTHESIS                    # ArrayGetAtIndexExpression
    | expression op=(GREATER_THEN_EQUAL|GREATER_THEN|LESS_THEN_EQUAL|LESS_THEN|EQUALS|NOT_EQUAL) expression     # ComparisonExpression
    | function                                                                                                  # FuncExpression
    | variableIdentifier                                                                                        # IdentifierExpression
    | expression op=(MULTIPLY|DIVIDE|MODULO_KEYWORD) expression                                                 # MulDivExpression
    | MINUS expression                                                                                          # NegateExpression
    | NOT_KEYWORD expression                                                                                    # NotExpression
    | numericLiteral                                                                                            # NumericLiteralExpression
    | expression OR_KEYWORD  expression                                                                         # OrExpression
    | (LEFT_PARENTHESIS expression RIGHT_PARENTHESIS)                                                           # ParenExpression
    | stringLiteral                                                                                             # StringLiteralExpression
    ;

function
    : absFunction
    | acsFunction
    | asnFunction
    | athFunction
    | atnFunction
    | avgFunction
    | cosFunction
    | expFunction
    | lenFunction
    | logFunction
    | maxFunction
    | minFunction
    | sinFunction
    | sqrFunction
    | sumFunction
    | tanFunction
    | valFunction
    ;

variableIdentifier
    : IDENTIFIER variableSuffix?
    ;

variableSuffix
    : (DOLLAR_SIGN|PERCENT_SIGN)
    ;

numericLiteral
    : NUMERIC_LITERAL ;

stringLiteral
    : STRING_LITERAL ;

functionCallArgs
    : (expression (COMMA expression)*)? ;

absFunction
    : ABS_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

acsFunction
    : ACS_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

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

logFunction
    : LOG_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

maxFunction
    : MAX_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

minFunction
    : MIN_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

sinFunction
    : SIN_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

sqrFunction
    : SQR_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

sumFunction
    : SUM_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

tanFunction
    : TAN_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;

valFunction
    : VAL_FUNCTION LEFT_PARENTHESIS functionCallArgs RIGHT_PARENTHESIS ;
