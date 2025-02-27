package com.github.agoss94.nand2tetris;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private final Iterator<String> iterator;

    private String currentLine;

    private Matcher matcher;

    private static final Pattern A_INSTRUCTION = Pattern.compile("@(.*)");

    private static final Pattern L_INSTRUCTION = Pattern.compile("\\((.*)\\)");

    public Parser(Path filePath) {
        try {
            iterator = Files.readAllLines(filePath).iterator();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasMoreLines() {
        return iterator.hasNext();
    }

    public void advance() {
        String nextLine = iterator.next();
        //Strip out comment
        nextLine = nextLine.contains("//") ? nextLine.substring(0,
                nextLine.indexOf("//")) : nextLine;
        nextLine = nextLine.replaceAll("\\s", "");
        if (nextLine.isBlank() && hasMoreLines()) {
            advance();
            return;
        }
        currentLine = nextLine;
    }

    public InstructionType instructionType() {
       matcher = A_INSTRUCTION.matcher(currentLine);
       if (matcher.matches()) {
            return InstructionType.A_INSTRUCTION;
       }
       matcher = L_INSTRUCTION.matcher(currentLine);
       if (matcher.matches()) {
            return InstructionType.L_INSTRUCTION;
       }
       // Last possibility is a C instruction.
       matcher = null;
       return InstructionType.C_INSTRUCTION;
    }

    public String symbol() {
        return Optional.ofNullable(matcher).orElseThrow(IllegalStateException::new).group(1);
    }

    public String dest() {
        int indexOf = currentLine.indexOf("=");
        return indexOf == -1 ? "" : currentLine.substring(0, indexOf);
    }

    public String comp() {
        int left = Math.max(0, currentLine.indexOf("=") + 1);
        int right = currentLine.indexOf(";") > 0 ? currentLine.indexOf(";") :
                currentLine.length();
        return currentLine.substring(left, right);
    }

    public String jump() {
        int indexOf = currentLine.indexOf(";");
        return indexOf == -1 ? "" : currentLine.substring(indexOf + 1);
    }

}
