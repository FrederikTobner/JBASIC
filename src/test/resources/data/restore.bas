DATA 5, 4, 3, 2, 1
FOR i = 1 TO 5
    IF i = 4 then
        RESTORE 0
    END
    READ Value
    PRINT Value
NEXT