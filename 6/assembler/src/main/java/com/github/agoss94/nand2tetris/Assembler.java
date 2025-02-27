package com.github.agoss94.nand2tetris;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Assembler {

    private static SymbolTable table;

    private static Parser parser;

    private static StringBuilder binaryBuilder;

    private static int nextAddress = 15;

    public static void main(String[] args) {
        System.out.printf("Read assembly file %s%n", args[0]);
        if (args[0].isBlank() || !args[0].contains("asm")) {
            throw new IllegalArgumentException("No asm file given.");
        }

        //First pass of parser
        Path filePath = Path.of(args[0]);
        parser = new Parser(filePath);
        table = new SymbolTable();
        int counter = 0;
        while (parser.hasMoreLines()) {
            parser.advance();
            switch (parser.instructionType()) {
                case C_INSTRUCTION, A_INSTRUCTION -> counter++;
                case L_INSTRUCTION ->
                        table.addEntry(parser.symbol(), counter + 1);
                default -> throw new RuntimeException(
                        "Unknown instruction " + "type");
            }
        }

        //Second pass of parser
        parser = new Parser(filePath);
        binaryBuilder = new StringBuilder();
        while (parser.hasMoreLines()) {
            parser.advance();
            switch (parser.instructionType()) {
                case A_INSTRUCTION -> {
                    handleAInstruction();
                }
                case C_INSTRUCTION -> {
                    handleCInstruction();
                }
                case L_INSTRUCTION -> {
                    // Do nothing
                }
                default -> throw new IllegalStateException(
                        "Unexpected value: " + parser.instructionType());
            }
        }

        String fileName = filePath.getFileName().toString().replace("asm",
                "hack");
        Path hackFilePath = filePath.getParent().resolve(Path.of(fileName));
        try {
            System.out.println(String.format("Write to %s", hackFilePath));
            Files.writeString(hackFilePath, binaryBuilder.toString(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleCInstruction() {
        binaryBuilder.append("111").append(Code.dest(parser.dest()))
                .append(Code.comp(parser.comp()))
                .append(Code.jump(parser.jump())).append(String.format("%n"));
    }

    private static void handleAInstruction() {
        String symbol = parser.symbol();
        // If the first char is not digit it must be a label or
        // variable
        int address;
        if (!Character.isDigit(symbol.toCharArray()[0])) {
            if (!table.contains(symbol)) {
                table.addEntry(symbol, nextAddress++);
            }
            address = table.getAddress(symbol);
        } else {
            address = Integer.parseInt(symbol);
        }
        String binaryAddress = Integer.toBinaryString(address);
        int numberOfZeros = 15 - binaryAddress.length();
        binaryAddress = binaryAddress.concat("0".repeat(numberOfZeros + 1));
        binaryBuilder.append(binaryAddress).append(String.format("%n"));
    }
}
