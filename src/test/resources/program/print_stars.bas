REM Prints the specified number of stars
INPUT "Amount=" amount_string
amount = VAL(amount_string)
LET S$ = ""
FOR I = 1 TO amount
    S$ = S$ + "*"
NEXT
PRINT S$