# JBASIC

[![Maven CI](https://github.com/FrederikTobner/JBASIC/actions/workflows/maven.yml/badge.svg)](https://github.com/FrederikTobner/JBASIC/actions/workflows/maven.yml)
[![Code QL](https://github.com/FrederikTobner/JBASIC/actions/workflows/codeql.yml/badge.svg)](https://github.com/FrederikTobner/JBASIC/actions/workflows/codeql.yml)

Simple Interpreter for the JBASIC programming language written in Java using [ANTLR4](https://www.antlr.org/).

JBASIC is a simple general purpose, high-level scripting language heavily inspired
by the earliest dialects of [BASIC](https://en.wikipedia.org/wiki/BASIC).

## Table of Contents

* [Overview](#overview)
* [Operators](#operators)
* [Keywords](#keywords)
* [Functions](#functions)
* [Building](#building)
* [How it works](#how-it-works)
* [License](#license)

## Overview

JBASIC is dialect of 'BASIC' (Beginners' All-purpose Symbolic Instruction Code) a family of general-purpose, high-level
programming languages, that focus on ease of use. 
The original version of BASIC was created in 1963 at the Dartmouth College, by  John G. Kemeny and Thomas E. Kurtz.

JBASIC is a second generation basic language with structured conditionals, loops and subroutines.

Usage:

    java -jar JBASIC.jar <filePath>

## Operators

### Binary operators

| Operator | Description                                                           | Example      |
|----------|-----------------------------------------------------------------------|--------------|
| +        | Computes the sum of two numerical values or concatenates two strings. | 1 + 2   // 3 |
| -        | Subtracts the second value from the first value.                      | 2 - 1   // 1 |
| *        | Multiplies the two values.                                            | 3 * 3   // 9 |
| /        | Divides the first value with the second value.                        | 9 / 3   // 3 |
| <        | Less than                                                             | 1 < 2   // 1 |
| <=       | Less than equal.                                                      | 1 <= 2  // 1 |
| >        | Greater than                                                          | 1 > 2   // 0 |
| >=       | Greater than equal.                                                   | 1 >= 2  // 0 |
| =        | Comparison for equality of two values. 1 if they are equal 0 if not   | 9 = 3   // 0 |
| <>       | Comparison for equality of two values. 0 if they are equal 1 if not   | 9 <> 3  // 1 |

### Unary operators

| Operator | Description                | Example |
|----------|----------------------------|---------|
| -        | Negates a numerical value. | -5      |

## Keywords

The language features the following keywords

### Data manipulation

| Keyword | Description                                                               | Example      |
|---------|---------------------------------------------------------------------------|--------------|
| LET     | Assigns a value (which may be the result of an expression) to a variable. | LET I = 1    |  
| DIM     | Creates a new array with the specified dimensions                         | DIM array[3] |

If the variable name in a Let statement ends with '$' the assigned value needs to be a string and if it ends with '%' a numerical value.

```
LET ASTRING$ = "Hello World"
LET ANUMBER% = 10
```

An array in JBASIC can be either one-, two- or three-dimensional.
A specific value in the array can be accessed or altered by specifying the index. The first element in an array has the index '1' in JBASIC.

```
DIM array[3]
array[1] = "Hello"
array[2] = " World!"
PRINT array[1] + array[2]
```

If the array name in a DIM statement ends with '$' it can only store strings and if it ends with '%' it can only store numerical values.

### Input and output

| Keyword | Description                                                                                   | Example              |
|---------|-----------------------------------------------------------------------------------------------|----------------------|
| CLS     | Clears the screen                                                                             | CLS                  |
| INPUT   | Asks the user to enter the value of a variable.<br>The statement may include a prompt message | INPUT "NAME= ", name |
| PRINT   | Displays a message on the screen or other output device                                       | PRINT "Hello World!" |

### Logical

| Keyword | Description                                                                   | Example              |
|---------|-------------------------------------------------------------------------------|----------------------|
| NOT     | Inverts a logical value.                                                      | NOT 1 // 0           |

### Mathematical

| Keyword | Description                                                                    | Example      |
|---------|--------------------------------------------------------------------------------|--------------|
| MOD     | Returns the remainder of the division of the first value with the second value | 5 MOD 3 // 2 |

### Miscellaneous

| Keyword | Description                                                                                             | Example                        |
|---------|---------------------------------------------------------------------------------------------------------|--------------------------------|
| REM     | holds a programmer's comment or remark<br> used to help identify the purpose of a given section of code | REM This is a remark / comment |

### Program flow control

| Keyword(s)                                                           | Description                                                                                                    | Example                                                                                     |
|----------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------|
| IF ... THEN <br>...<br> {ELSE IF} <br>...<br> {ELSE} <br>...<br> END | Used to perform comparisons or make decisions.                                                                 | IF 1 < 2 THEN<br>&emsp;print "true"<br>ELSE<br>&emsp;print "false"<br>END                   |
| FOR ... TO ... {STEP} <br>...<br> NEXT                               | Repeats a section of code a given number of times.                                                             | FOR I = 0 TO 5<br>&emsp;print I<br>NEXT                                                     |
| WHILE ... <br> ...<br> END                                           | Repeats a section of code a given number of times.                                                             | WHILE I < 2 <br>&emsp;print I<br>&emsp;I = I + 1<br>END                                     |
| REPEAT <br> ... <br> UNTIL ...                                       | Repeats a section of code a given number of times.                                                             | REPEAT <br>&emsp;print I<br>&emsp;I = I + 1 <br>UNTIL I > 4                                 |
| DO <br> ... <br> WHILE ...                                           | Repeats a section of code a given number of times,<br> but at least once.                                      | DO <br>&emsp;print I<br>&emsp;I = I + 1<br>WHILE I < 2                                      |
| DO <br> ... <br> UNTIL ...                                           | Repeats a section of code a given number of times,<br> but at least once.                                      | DO 1 < 2 <br>&emsp;print I<br>&emsp;I = I + 1 <br>UNTIL I > 4                               |
| SWITCH ...<br> (CASE ... : ...)+<br> END                             | Allows the use of the value of a variable or<br>expression to change the program execution via search and map. | SWITCH 1 + 2 <br>&emsp;CASE 1: "one"<br>&emsp;CASE 2: "two"<br>&emsp;CASE 3: "three"<br>end |
| GOTO                                                                 | Jumps to a numbered or labelled line in the program.                                                           | 10: PRINT "Hi" <br>20: GOTO 10                                                              |

### Subroutine specific

JBASIC allows the definition of subroutines to accomplish a particular task.

| Keyword(s)           | Description              | Example                                         |
|----------------------|--------------------------|-------------------------------------------------|
| SUB(...) ... END SUB | Defines a new subroutine | SUB printSum(a, b) <br>&emsp;PRINT a + b<br>END |
| CALL(...)            | Calls a subroutine       | CALL printSum(1, 2)                             |

An example for a simple subroutine would
be:

```
SUB Greet()
   INPUT "Name=" name
   PRINT "Hi my name is " + name
END SUB

CALL Greet()
```

The SUB keyword marks the definition of a subroutine. After that the name of the subroutine and the arguments of the
subroutine are specified. Then the body of the subroutine follows. The end of the subroutine is marked with the END and
the SUB keyword. Subroutines are then invoked using the call keyword.

## Functions

JBASIC offers numerous built-in functions to perform various tasks.

### Mathematical

| FunctionName | Description                | Example Usage                                                                |
|--------------|----------------------------|------------------------------------------------------------------------------|
| ABS          | Absolute value             | ABS(-2)                                 // 2                                 |
| ACS          | Arc cosine                 | ACS(0)                                  // 1.5707963267949                   |
| ASH          | Arc sine hyperbolicus      | ASH(1.1752011936438)                    // 1                                 |
| ASN          | Arc sine                   | ASN(1)                                  // 1.5707963267949                   |
| ATH          | Area tangent hyperbolicus  | ATH(1)                                  // ∞                                 |
| ATN          | Arc tangent                | ATN(1)                                  // 0.78539816339745                  |
| AVG          | Average                    | AVG(1, 2, 3)                            // 2                                 |
| COS          | Cosine                     | COS(1.5707963267949)                    // 0                                 |
| EXP          | Exponential function       | EXP(1)                                  // 2,7182818284590452353602874713527 |
| LOG          | Natural logarithm          | LOG(2,7182818284590452353602874713527)  // 1                                 |
| MAX          | Maximum value              | MAX(1, 2, 3)                            // 3                                 |
| MIN          | Minimum value              | MIN(1, 2, 3)                            // 1                                 |
| SIN          | Sine (argument in radians) | SIN(1.5707963267949)                    // 1                                 |
| SQR          | Square root                | SQR(9)                                  // 3                                 |
| SUM          | Summation                  | SUM(1, 2, 3)                            // 6                                 |
| TAN          | Tangent                    | TAN(0.78539816339745)                   // 1                                 |

### Miscellaneous

| Function | Description                                                                | Example Usage           |
|----------|----------------------------------------------------------------------------|-------------------------|
| LIST     | Creates a string that contains the full source code of the current program | LIST()      // LIST()   |
| RND      | Creates a random number in the specified range                             | VAL(1, 3)   // [1 .. 3] |
| VAL      | Converts a string to a numerical value                                     | VAL("3")    // 3        |

### Strings

| FunctionName | Description        | Example Usage   |
|--------------|--------------------|-----------------|
| LEN          | Length of a string | LEN("123") // 3 |

## Building

The interpreter is built using maven and uses the JDK version 11.

## How it works

The interpretation of a program is performed in the following stages:

* Lexical analysis: We iterate over all the characters in the source code and group them together to tokens
* Parsing: Next we parse the linear sequence of tokens we have created in the previous step to create an abstract syntax
  tree
* Execution: To execute a JBASIC program we visit all the nodes in our abstract syntax tree and evaluate them

More information about the interpreter can be found at the [technical documentation](https://frederiktobner.github.io/JBASIC/).

## License

This project is licensed under the [GNU General Public License](LICENSE)