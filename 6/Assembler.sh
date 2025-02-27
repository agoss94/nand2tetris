#!/bin/bash

# Path to the JAR file
JAR_FILE="assembler-1.0.jar"

# Main class to run
MAIN_CLASS="nand2tetris.agoss94.github.com.Assembler"

# Check if the JAR file exists
if [[ ! -f "$JAR_FILE" ]]; then
    echo "Error: $JAR_FILE not found!"
    exit 1
fi

# Run the JAR file with the specified main class
java -cp "$JAR_FILE" "$MAIN_CLASS"

