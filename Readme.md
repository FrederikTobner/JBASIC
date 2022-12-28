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
* [Arrays](#arrays)
* [Subroutines](#subroutines)
* [Building](#building)
* [How it works](#how-it-works)
* [License](#license)

## Overview

JBASIC is dialect of 'BASIC' (Beginners' All-purpose Symbolic Instruction Code) a family of general-purpose, high-level
programming languages, that focus on ease of use.

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

| Keyword(s)                     | Description                                        |
|--------------------------------|----------------------------------------------------|
| IF ... THEN ... {ELSE}         | Used to perform comparisons or make decisions.     |
| FOR ... TO ... {STEP} ... NEXT | Repeats a section of code a given number of times. |
| WHILE ... END                  | Repeats a section of code a given number of times. |
| REPEAT ... UNTIL               | Repeats a section of code a given number of times. |

### Subroutine specific
| Keyword(s)        | Description              |
|-------------------|--------------------------|
| SUB ... END SUB   | Defines a new subroutine |
| CALL              | Calls a subroutine       |

## Functions

JBASIC offers numerous functions to perform various tasks.

### Mathematical

| FunctionName | Description               |
|--------------|---------------------------|
| ABS          | Absolute value            |
| ACS          | Arc cosine                |
| ASN          | Arc sine                  |
| ATH          | Area tangent hyperbolicus |
| ATN          | Arc tangent               |
| AVG          | Average                   |
| COS          | Cosine                    |
| EXP          | Exponential function      |
| LOG          | Natural logarithm         |
| MAX          | Maximum value             |
| MIN          | Minimum value             |
| SIN          | Sine                      |
| SQR          | Square root               |
| SUM          | Summation                 |
| TAN          | Tangent                   |

### Strings

| FunctionName | Description        |
|--------------|--------------------|
| LEN          | Length of a string |

## Arrays

Arrays are declared using the dim keyword. An array in JBASIC can be either one-, two- or three-dimensional.
A specific value in the array can be accessed or altered by specifying the index. The first element in an array has the index '1' in JBASIC.

```
DIM array(3)
array(1) = "Hello"
array(2) = " World!"
PRINT array(1) + array(2)
```

If the array name in a DIM statement ends with '$' it can only store strings and if it ends with '%' it can only store numerical values.

## Subroutines

JBASIC allows the definition of subroutines to accomplish a particular task. An example for a simple subroutine would
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

## Building

The interpreter is built using maven, uses the JDK version 11 and can be built by running the following command

    mvn install

## How it works

The interpretation of a program is performed in the following stages

* Lexical analysis: We iterate over all the characters in the source code and group them together to tokens
* Parsing: Next we parse the linear sequence of tokens we have created in the previous step to create an abstract syntax
  tree
* Execution: To execute a JBASIC program we visit all the nodes in our abstract syntax tree and evaluate them

More information about the interpreter can be found at the [technical documentation](https://frederiktobner.github.io/JBASIC/).

## License

This project is licensed under the [GNU General Public License](LICENSE)