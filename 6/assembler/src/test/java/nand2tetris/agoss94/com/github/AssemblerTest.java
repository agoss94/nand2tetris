package nand2tetris.agoss94.com.github;

import org.junit.jupiter.api.Test;

import java.net.URL;

public class AssemblerTest {

    private static final URL ADD =
            ParserTest.class.getResource("/Add.asm");
    private static final URL MAX =
            ParserTest.class.getResource("/Max.asm");
    private static final URL PONG =
            ParserTest.class.getResource("/Pong.asm");
    private static final URL RECT =
            ParserTest.class.getResource("/Rect.asm");

    @Test
    void testAdd() {
        Assembler.main(new String[] {ADD.getPath().toString()});
    }

    @Test
    void testMax() {
        Assembler.main(new String[] {MAX.getPath().toString()});
    }

    @Test
    void testPong() {
        Assembler.main(new String[] {PONG.getPath().toString()});
    }

    @Test
    void testRect() {
        Assembler.main(new String[] {RECT.getPath().toString()});
    }
}
