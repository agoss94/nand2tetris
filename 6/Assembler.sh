#!/bin/bash

# Path to the JAR file
JAR_FILE="./assembler/target/assembler-1.0.jar"

# Main class to run
MAIN_CLASS="com.github.agoss94.nand2tetris.Assembler"

# Check if the JAR file exists
if [[ ! -f "$JAR_FILE" ]]; then
    echo "Error: $JAR_FILE not found!"
    exit 1
fi

# If an argument is passed, process that file
if [[ -n "$1" ]]; then
    if [[ -f "$1" && "$1" == *.asm ]]; then
        # Run the JAR with the provided file
        echo "Processing $1..."
        java -cp "$JAR_FILE" "$MAIN_CLASS" "$1"
    else
        echo "Error: $1 is not a valid .asm file!"
        exit 1
    fi
else
    # No argument, loop over all .asm files in the current directory
    for asm_file in *.asm; do
        if [[ -f "$asm_file" ]]; then
            echo "Processing $asm_file..."
            java -cp "$JAR_FILE" "$MAIN_CLASS" "$asm_file"
        fi
    done
fi

