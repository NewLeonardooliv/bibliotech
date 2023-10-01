#!/bin/bash

find src/ -name "*.java" > sources

javac -d bin -cp .:src/lib/mysql-connector-j-8.1.0.jar @sources
rm sources

if [ $? -eq 0 ]; then
    java -cp bin:src/lib/mysql-connector-j-8.1.0.jar Main
else
    echo "Erro na compilação."
fi
