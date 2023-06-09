:: Generates documentation using doxygen (https://www.doxygen.nl/)
@ECHO OFF
IF NOT EXIST ../src/main/java (
    ECHO Can not find source directory
    EXIT
)
cd ../src/main/java
ECHO Generating Documentation ...
doxygen
ECHO Moving generated content out of the html folder ...
IF NOT EXIST ..\..\..\docs (
    ECHO Can not find docs directory
    EXIT
)
IF NOT EXIST ..\..\..\docs\html (
    ECHO Can not find html directory
    EXIT
)
copy ..\..\..\docs\html ..\..\..\docs
ECHO Removing html folder ...
rmdir /s ..\..\..\docs\html\
ECHO Successfully generated documentation!