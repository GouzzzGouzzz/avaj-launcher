#!/bin/bash

find . -name "*.java" > sources.txt
javac @sources.txt

cd ./src && java fr.ft.avajlauncher.Main scenario.txt

if [ $? -eq 0 ]; then
    echo "Compilation successful."
else
    echo "Compilation failed."
fi
