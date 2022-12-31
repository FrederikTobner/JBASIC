# Generates documentation using doxyxgen (https://www.doxygen.nl/)
if [ -d "../src/main/java" ]
then
    echo "Generating Documentation ..."
    cd ../src/main/java
    doxygen
    echo "Moving generated content out of the html folder ..."
    if [ -d "../../../docs" ]
    then
        if [ -d "../../../docs/html/" ]
        then
            cp -avr ../../../docs/html/* ../../../docs
            echo "Removing html folder ..."
            rm -rf ../../../docs/html
            echo "Successfully generated documentation!"
        else
            echo "No html folder inside the docs folder found"
            exit 70
        fi
    else
        echo "No docs folder generated"
        exit 70
    fi
else
    echo "Could not find source folder"
    exit 70
fi