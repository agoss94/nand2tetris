package nand2tetris.agoss94.com.github;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    private static final URL TEST1 =
            ParserTest.class.getResource("/Test1.asm");

    @Test
    void test() throws URISyntaxException {
        assertNotNull(TEST1);
        Parser parser = new Parser(Path.of(TEST1.getPath()));
        assertTrue(parser.hasMoreLines());
        parser.advance();
        assertEquals(InstructionType.A_INSTRUCTION, parser.instructionType());
        assertEquals("counter", parser.symbol());
        assertTrue(parser.hasMoreLines());
        parser.advance();
        assertEquals(InstructionType.A_INSTRUCTION, parser.instructionType());
        assertEquals("12345", parser.symbol());
        assertTrue(parser.hasMoreLines());
        parser.advance();
        assertEquals(InstructionType.L_INSTRUCTION, parser.instructionType());
        assertEquals("LOOP", parser.symbol());
        parser.advance();
        assertEquals(InstructionType.C_INSTRUCTION, parser.instructionType());
        assertEquals("M", parser.dest());
        assertEquals("D+1", parser.comp());
        assertEquals("", parser.jump());
        parser.advance();
        assertEquals(InstructionType.C_INSTRUCTION, parser.instructionType());
        assertEquals("", parser.dest());
        assertEquals("D", parser.comp());
        assertEquals("JMP", parser.jump());
        parser.advance();
        assertEquals(InstructionType.C_INSTRUCTION, parser.instructionType());
        assertEquals("AM", parser.dest());
        assertEquals("D-1", parser.comp());
        assertEquals("JLT", parser.jump());
    }

}
