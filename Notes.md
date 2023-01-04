# Notes

## Table of Contents

* [Overview](#setup)
* [Todo](#todo)

## Setup

### bash.rc
Edit the '.bashrc' file located in your home directory
```
export CLASSPATH=$CLASSPATH:<fullJarPath>
alias JBASIC="java -jar <fullJarPath>
```
### powershell profile
Run the following command to open the powershell profile file using notepad
```
notepad $PROFILE
```
Add alias and function to profile
```
function JBASIC {
    param (
        [Parameter(Mandatory=$true,Position=0)] [String[]]$parameter
    )
    java -jar D:\Projects\Java\JBASIC\target\JBASIC.jar $parameter
}
```

## Todo

General

* Data Keyword
* Ranges expressions
* Set console fore- and background SCF, SCB

Graphics

* GIW(x, y) - graphics initialize window,
* GOC(subroutine) - subroutine that is called when the window is closed,
* GRW(x, y) - graphics resize window,
* GDR(x1, y1, x2, y2) - graphics draw rectangle
* GDT(x1, y1, x2, y2, x3, y3) - graphics draw triangle
* GDC(x, y, diameter) - graphics draw circle