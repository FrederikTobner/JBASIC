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
    : lenFunction
    | valFunction
    | isnanFunction
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

lenFunction
    : LEN LEFT_PARENTHESIS expression RIGHT_PARENTHESIS
    ;

valFunction
    : VAL LEFT_PARENTHESIS expression RIGHT_PARENTHESIS
    ;

isnanFunction
    : ISNAN LEFT_PARENTHESIS expression RIGHT_PARENTHESIS
    ;

//exprlist
//    : expression (COMMA expression)*
//    ;
