REM PRINTS the given amount of fibonacci number
LET X = 0
LET Y = 1
INPUT "Amount=" amount_string
amount = VAL(amount_string)
FOR I = 1 TO amount
    if I MOD 2 then
        PRINT X
        X = X + Y
    else
        PRINT Y
        Y = X + Y
    end
NEXT