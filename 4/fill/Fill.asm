// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, 
// the screen should be cleared.

(RESET)
    @SCREEN
    D=A
    @pointer
    M=D
(READ)
    @KBD
    D=M
    @WHITE
    D;JEQ
    @BLACK
    0;JMP
(WHITE)    
    @color
    M=0;
    @PAINT
    0;JMP
(BLACK) 
    @color
    M=-1
    @PAINT
    0;JMP
(PAINT)
    //Paint the pixel of pointer
    @color
    D=M
    @pointer
    A=M
    M=D
    //Increment
    @pointer
    MD=M+1
    @KBD
    D=D-A
    @RESET
    D;JEQ
    @READ
    0;JMP
