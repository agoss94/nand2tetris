package nand2tetris.agoss94.com.github;

public class Code {

    private Code() {
        //Util class
    }

    static public String dest(String dest) {
        int destM = dest.contains("M") ? 1 : 0;
        int destD = dest.contains("D") ? 1 : 0;
        int destA = dest.contains("A") ? 1 : 0;
        return String.format("%d%d%d", destA, destD, destM);
    }

    static public String comp(String comp) {
        return switch (comp) {
            case "0"  -> "0101010";
            case "1"  -> "0111111";
            case "-1" -> "0111010";
            case "D"  -> "0001100";
            case "A"  -> "0110000";
            case "M"  -> "1110000";
            case "!D" -> "0001101";
            case "!A" -> "0110001";
            case "!M" -> "1110001";
            case "-D" -> "0001111";
            case "-A" -> "0110011";
            case "-M" -> "1110011";
            case "D+1" -> "0011111";
            case "A+1" -> "0110111";
            case "M+1" -> "1110111";
            case "D-1" -> "0001110";
            case "A-1" -> "0110010";
            case "M-1" -> "1110010";
            case "D+A" -> "0000010";
            case "D+M" -> "1000010";
            case "D-A" -> "0010011";
            case "D-M" -> "1010011";
            case "A-D" -> "0000111";
            case "M-D" -> "1000111";
            case "D&A" -> "0000000";
            case "D&M" -> "1000000";
            case "D|A" -> "0010101";
            case "D|M" -> "1010101";
            default -> throw new RuntimeException(String.format(
                    "The " + "computation code %s is not recognized", comp));
        };
    }

    static public String jump(String jump) {
        return switch (jump) {
            case "" -> "000";
            case "JGT" -> "001";
            case "JEQ" -> "010";
            case "JGE" -> "011";
            case "JLT" -> "100";
            case "JNE" -> "101";
            case "JLE" -> "110";
            case "JMP" -> "111";
            default -> throw new RuntimeException(String.format(
                    "The jump code %s is not recognized", jump));
        };
    }

}
