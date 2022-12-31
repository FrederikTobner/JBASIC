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

JBASIC is a second generation basic language with structured conditionals and loops, and subroutines.

Usage:

    java -jar JBASIC.jar <filePath>

## Operators

### Binary operators

| Operator | Description                                                           |
|----------|-----------------------------------------------------------------------|
| +        | Computes the sum of two numerical values or concatenates two strings. |
| -        | Subtracts the second value from the first value.                      |
| *        | Multiplies the two values.                                            |
| /        | Divides the first value with the second value.                        |
| < and <= | Less than and less than equal.                                        |
| > and >= | Greater than and greater than equal.                                  |
| =        | Comparison for equality of two values. 1 if they are equal 0 if not   |
| <>       | Comparison for equality of two values. 0 if they are equal 1 if not   |

### Unary operators

| Operator | Description                |
|----------|----------------------------|
| -        | Negates a numerical value. |

## Keywords

The language features the following keywords

### Data manipulation

| Keyword | Description                                                               |
|---------|---------------------------------------------------------------------------|
| LET     | Assigns a value (which may be the result of an expression) to a variable. |
| DIM     | Creates a new array with the specified dimensions                         |

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

| Keyword | Description                                                                                |
|---------|--------------------------------------------------------------------------------------------|
| INPUT   | Asks the user to enter the value of a variable. The statement may include a prompt message |
| PRINT   | Displays a message on the screen or other output device                                    |

### Logical

| Keyword | Description                                                                   |
|---------|-------------------------------------------------------------------------------|
| NOT     | Inverts a logical value.                                                      |

### Mathematical

| Keyword | Description                                                                   |
|---------|-------------------------------------------------------------------------------|
| MOD     | Returns the remainder of the division of he first value with the second value |

### Miscellaneous

| Keyword | Description                                                                                         |
|---------|-----------------------------------------------------------------------------------------------------|
| REM     | holds a programmer's comment or remark used to help identify the purpose of a given section of code |

### Program flow control

| Keyword(s)                     | Description                                          |
|--------------------------------|------------------------------------------------------|
| IF ... THEN ... {ELSE}         | Used to perform comparisons or make decisions.       |
| FOR ... TO ... {STEP} ... NEXT | Repeats a section of code a given number of times.   |
| WHILE ... END                  | Repeats a section of code a given number of times.   |
| REPEAT ... UNTIL               | Repeats a section of code a given number of times.   |
| GOTO                           | Jumps to a numbered or labelled line in the program. |

### Subroutine specific

JBASIC allows the definition of subroutines to accomplish a particular task.

| Keyword(s)        | Description              |
|-------------------|--------------------------|
| SUB ... END SUB   | Defines a new subroutine |
| CALL              | Calls a subroutine       |

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

| Function | Description                                                                | Example Usage   |
|----------|----------------------------------------------------------------------------|-----------------|
| LIST     | Creates a string that contains the full source code of the current program | LIST()          |
| VAL      | Converts a string to a numerical value                                     | VAL("3")   // 3 |

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

## To be implemented

General
* Random numbers
* More mathematical functions
* SELECT expression (CASE: expression statement+)+ END
* CLS
* Data Keyword
* Ranges expressions

Graphics
* GIW(x, y) - graphics initialize window, 
* GOC(subroutine) - subroutine that is called when the window is closed, 
* GRW(x, y) - graphics resize window, 
* GDR(window, x, y, z) - graphics draw rectangle
* GDT(x1, y1, x2, y2) - graphics draw triangle
* GDC(x, y, diameter) - graphics draw circle

## License

This project is licensed under the [GNU General Public License](LICENSE)