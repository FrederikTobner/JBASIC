REM Greatest common divider
INPUT "First=", first_input
INPUT "Second=", second_input
first_val% = NUM(first_input)
second_val% = NUM(second_input)

WHILE second_val% > 0
    t% = first_val% MOD second_val%
    first_val% = second_val%
    second_val% = t%
END

PRINT first_val%