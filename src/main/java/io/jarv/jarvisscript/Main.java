package io.jarv.jarvisscript;


import io.jarv.jarvisscript.Exp.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
//        testLexer();
//        testParser();
//        testExecutor();

        Path programPath = Paths.get(args[0]);
//        Path configPath = Paths.get(args[1]);

        try {
            String programContent = Files.readString(programPath);
//            String configContent = Files.readString(configPath);

//            runProgram("{n <- 5; result <- 1; while 1 < n do {result <- result * n; n <- n - 1}}");
            runProgram(programContent);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

    }

    private static void runProgram(String program) {
        ProgramExecutor executor = new ProgramExecutor();
        executor.executeStatement(
                (new Parser(
                        (new Lexer(program)).lex()
                )).parse()
        );
        executor.printState();
    }

    public static void testLexer() {
        Lexer test1 = new Lexer("myVar <- x * (foo + bar)");
        System.out.println(test1.lex());

        Lexer test2 = new Lexer("if x = 0 then x <- x * y else skip");
        System.out.println(test2.lex());

        Lexer test3 = new Lexer("while foo < 32 do { x <- x + 1; y <- y - 1 }");
        System.out.println(test3.lex());
    }

    public static void testParser() {
        Parser test4 = new Parser(Arrays.asList(
                new Token(Terminal.WHILE),
                new Token(Terminal.TRUE),
                new Token(Terminal.DO),
                new Token(Terminal.SKIP),
                new Token(Terminal.END)
        ));
        System.out.println(test4.parse());

        Parser test5 = new Parser(Arrays.asList(
                new Token(Terminal.IF),
                new Token(Terminal.ID, "x"),
                new Token(Terminal.LESS_THAN),
                new Token(Terminal.EQUALS),
                new Token(Terminal.NUM, "3"),
                new Token(Terminal.THEN),
                new Token(Terminal.ID, "x"),
                new Token(Terminal.LEFT_ARROW),
                new Token(Terminal.ID, "x"),
                new Token(Terminal.MINUS),
                new Token(Terminal.NUM, "1"),
                new Token(Terminal.ELSE),
                new Token(Terminal.ID, "y"),
                new Token(Terminal.LEFT_ARROW),
                new Token(Terminal.ID, "y"),
                new Token(Terminal.PLUS),
                new Token(Terminal.NUM, "1"),
                new Token(Terminal.END)
        ));
        System.out.println(test5.recognise());

        Parser test6 = new Parser(Arrays.asList(
                new Token(Terminal.WHILE),
                new Token(Terminal.ID, "y"),
                new Token(Terminal.PLUS),
                new Token(Terminal.NUM, "3"),
                new Token(Terminal.LESS_THAN),
                new Token(Terminal.NUM, "2"),
                new Token(Terminal.DO),
                new Token(Terminal.ID, "y"),
                new Token(Terminal.LEFT_ARROW),
                new Token(Terminal.ID, "y"),
                new Token(Terminal.PLUS),
                new Token(Terminal.NUM, "1"),
                new Token(Terminal.SEMICOLON),
                new Token(Terminal.ID, "x"),
                new Token(Terminal.LEFT_ARROW),
                new Token(Terminal.NUM, "0"),
                new Token(Terminal.END)
        ));
        System.out.println(test6.parse());

        Parser test7 = new Parser(Arrays.asList(
                new Token(Terminal.ID, "y"),
                new Token(Terminal.LEFT_ARROW),
                new Token(Terminal.ID, "y"),
                new Token(Terminal.PLUS),
                new Token(Terminal.NUM, "1"),
                new Token(Terminal.SEMICOLON),
                new Token(Terminal.ID, "x"),
                new Token(Terminal.LEFT_ARROW),
                new Token(Terminal.NUM, "0"),
                new Token(Terminal.SEMICOLON),
                new Token(Terminal.END)
        ));
        System.out.println(test7.recognise());

        Parser test8 = new Parser(Arrays.asList(
                new Token(Terminal.ID, "whiley"),
                new Token(Terminal.LEFT_ARROW),
                new Token(Terminal.LEFT_BRACKET),
                new Token(Terminal.ID, "iff"),
                new Token(Terminal.PLUS),
                new Token(Terminal.ID, "sskip"),
                new Token(Terminal.RIGHT_BRACKET),
                new Token(Terminal.STAR),
                new Token(Terminal.ID, "doo"),
                new Token(Terminal.MINUS),
                new Token(Terminal.ID, "thenn"),
                new Token(Terminal.END)
        ));
        System.out.println(test8.parse());
    }

    public static void testExecutor() {
        ProgramExecutor test9 = new ProgramExecutor();
        test9.setVar("x", 5);
        test9.setVar("y", 10);
        System.out.println(
                test9.evaluateArithmetic(
                        new PlusExp(
                                new VarExp("x"),
                                new PlusExp(
                                        new VarExp("x"),
                                        new VarExp("y")
                                )
                        )
                )
        );

        ProgramExecutor test10 = new ProgramExecutor();
        test10.setVar("x", 2);
        test10.setVar("y", 3);
        System.out.println(
                test10.evaluateBoolean(
                        new AndExp(
                                new LessExp(
                                        new VarExp("x"),
                                        new VarExp("y")
                                ),
                                new TrueExp()
                        )
                )
        );

        ProgramExecutor test11 = new ProgramExecutor();
        test11.setVar("x", 2);
        test11.setVar("foo", 3);
        test11.setVar("bar", 4);
        test11.executeStatement(
                (new Parser(
                        (new Lexer("myVar <- x * (foo + bar)")).lex()
                )).parse()
        );
        test11.printState();

        ProgramExecutor test12 = new ProgramExecutor();
        test12.setVar("x", 2);
        test12.setVar("y", 3);
        test12.executeStatement(
                (new Parser(
                        (new Lexer("if x = 2 then x <- x * y else skip")).lex()
                )).parse()
        );
        test12.printState();

        ProgramExecutor test13 = new ProgramExecutor();
        test13.executeStatement(
                (new Parser(
                        (new Lexer("while x < 32 do { x <- x + 1; y <- y - 1 }")).lex()
                )).parse()
        );
        test13.printState();
    }
}
