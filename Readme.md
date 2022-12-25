# JBASIC

[![Maven CI](https://github.com/FrederikTobner/JBASIC/actions/workflows/maven.yml/badge.svg)](https://github.com/FrederikTobner/JBASIC/actions/workflows/maven.yml)

Simple Interpreter for the JBASIC programming language written in Java using [ANTLR4](https://www.antlr.org/)
and [hamcrest](http://hamcrest.org/JavaHamcrest/).

JBASIC is heavily inspired by [BASIC](https://en.wikipedia.org/wiki/BASIC).

## Table of Contents

* [Overview](#overview)
* [Keywords](#keywords)
* [Functions](#functions)
* [Building](#building)
* [How it works](#how-it-works)
* [License](#license)

## Overview

JBASIC is dialect of 'BASIC' (Beginners' All-purpose Symbolic Instruction Code) a family of general-purpose, high-level programming languages.
The main focus of the 'BASIC' languages is ease of use.

## Keywords

### Data manipulation

| Keyword | Description                                                                                                                                                                                           |
|---------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| LET     | assigns a value (which may be the result of an expression) to a variable. In most dialects of BASIC, LET is optional, and a line with no other identifiable keyword will assume the keyword to be LET |

### Input and output

| Keyword | Description                                                                                |
|---------|--------------------------------------------------------------------------------------------|
| INPUT   | asks the user to enter the value of a variable. The statement may include a prompt message |
| PRINT   | displays a message on the screen or other output device                                    |

### Program flow control

| Keyword(s)                          | Description                                                                                                                                                                                                                      |
|-------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| IF ... THEN ... {ELSE}              | used to perform comparisons or make decisions. Early dialects only allowed a line number after the THEN, but later versions allowed any valid statement to follow. ELSE was not widely supported, especially in earlier versions |
| FOR ... TO ... {STEP} ... NEXT      | repeat a section of code a given number of times. A variable that acts as a counter, the "index", is available within the loop                                                                                                   |
| WHILE ... WEND and REPEAT ... UNTIL | repeat a section of code a given number of times. A variable that acts as a counter, the "index", is available within the loop                                                                                                   |


## Functions

### Mathematical

| FunctionName | Description               |
|--------------|---------------------------|
| ABS          | Absolute value            |
| ACS          | Arc cosine                |
| ASN          | Arc sine                  |
| ATH          | Area tangent hyperbolicus |
| ATN          | Arc tangent               |
| COS          | Cosine                    |
| EXP          | Exponential function      |
| LOG          | Natural logarithm         |
| SIN          | Absolute value            |
| SQR          | Sine                      |
| SQR          | Square root               |
| TAN          | Tangent                   |

### Strings

| FunctionName | Description        |
|--------------|--------------------|
| LEN          | Length of a string |

## Building

The interpreter is built using maven

    mvn install

The JAR will be located in the /target directory

## How it works

The interpretation of a program is performed in the following stages

## License

This project is licensed under the [GNU General Public License](LICENSE)
