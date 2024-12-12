package io.jarv.jarvisscript;

import io.jarv.jarvisscript.Exp.*;
import io.jarv.jarvisscript.Stmt.*;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int pos = 0;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }


    private Token peek() {
        return tokens.get(pos);
    }

    private void eat(Terminal terminal) {
        if (peek().terminal == terminal) {
            pos++;
        } else {
            throw new RuntimeException("Unexpected character");
        }
    }

    private Stmt parseProg() {
//        System.out.println("parseProg");

        if (
                peek().terminal == Terminal.IF
                        || peek().terminal == Terminal.WHILE
                        || peek().terminal == Terminal.SKIP
                        || peek().terminal == Terminal.ID
                        || peek().terminal == Terminal.LEFT_CURLY_BRACKET
        ) {
            Stmt stmt = parseStmt();
            Stmt stmts = parseStmts();
            eat(Terminal.END);
            if (stmts != null) {
                return new SeqStmt(stmt, stmts);  // Combine into sequence if there's more than one stmt
            } else {
                return stmt;
            }
        } else {
            throw new RuntimeException("Unexpected token");
        }
    }

    private Stmt parseStmt() {
//        System.out.println("parseStmt");

        if (peek().terminal == Terminal.IF) {
            eat(Terminal.IF);
            Exp condition = parseBExp();
            eat(Terminal.THEN);
            Stmt thenBranch = parseStmt();
            eat(Terminal.ELSE);
            Stmt elseBranch = parseStmt();
            return new CondStmt(condition, thenBranch, elseBranch);  // Conditional statement
        } else if (peek().terminal == Terminal.WHILE) {
            eat(Terminal.WHILE);
            Exp condition = parseBExp();
            eat(Terminal.DO);
            Stmt body = parseStmt();
            return new WhileStmt(condition, body);  // While loop
        } else if (peek().terminal == Terminal.SKIP) {
            eat(Terminal.SKIP);
            return new SkipStmt();  // Skip statement
        } else if (peek().terminal == Terminal.ID) {
            String varName = peek().lexeme;
            eat(Terminal.ID);
            eat(Terminal.LEFT_ARROW);
            Exp value = parseAExp();
            return new AssnStmt(varName, value);  // Assignment statement
        } else if (peek().terminal == Terminal.LEFT_CURLY_BRACKET) {
            eat(Terminal.LEFT_CURLY_BRACKET);
            Stmt stmt = parseStmt();
            Stmt stmts = parseStmts();
            eat(Terminal.RIGHT_CURLY_BRACKET);
            if (stmts != null) {
                return new SeqStmt(stmt, stmts);  // Block of sequential statements
            } else {
                return stmt;
            }
        } else {
            throw new RuntimeException("Unexpected token");
        }
    }

    private Stmt parseStmts() {
//        System.out.println("parseStmts");

        if (peek().terminal == Terminal.RIGHT_CURLY_BRACKET || peek().terminal == Terminal.END) {
            return null;
        } else if (peek().terminal == Terminal.SEMICOLON) {
            eat(Terminal.SEMICOLON);
            Stmt stmt = parseStmt();
            Stmt stmts = parseStmts();
            if (stmts != null) {
                return new SeqStmt(stmt, stmts);  // Sequential composition of statements
            } else {
                return stmt;
            }
        } else {
            throw new RuntimeException("Unexpected token");
        }
    }

    private Exp parseBExp() {
//        System.out.println("parseBExp");

        if (peek().terminal == Terminal.ID
                || peek().terminal == Terminal.BANG
                || peek().terminal == Terminal.NUM
                || peek().terminal == Terminal.TRUE
                || peek().terminal == Terminal.FALSE
                || peek().terminal == Terminal.LEFT_BRACKET
        ) {
            Exp left = parseBFac();
            return parseBExps(left);
        } else {
            throw new RuntimeException("Unexpected token");
        }
    }

    private Exp parseBExps(Exp left) {
//        System.out.println("parseBExps");

        if (peek().terminal == Terminal.THEN
                || peek().terminal == Terminal.DO
                || peek().terminal == Terminal.RIGHT_BRACKET
        ) {
            return left;
        } else if (peek().terminal == Terminal.OR) {
            eat(Terminal.OR);
            Exp right = parseBFac();
            return parseBExps(new OrExp(left, right));
        } else {
            throw new RuntimeException("Unexpected token");
        }
    }

    private Exp parseBFac() {
//        System.out.println("parseBFac");

        if (peek().terminal == Terminal.ID
                || peek().terminal == Terminal.BANG
                || peek().terminal == Terminal.NUM
                || peek().terminal == Terminal.TRUE
                || peek().terminal == Terminal.FALSE
                || peek().terminal == Terminal.LEFT_BRACKET
        ) {
            Exp left = parseBNeg();
            return parseBFacs(left);
        } else {
            throw new RuntimeException("Unexpected token");
        }
    }

    private Exp parseBFacs(Exp left) {
//        System.out.println("parseBFacs");

        if (peek().terminal == Terminal.THEN
                || peek().terminal == Terminal.DO
                || peek().terminal == Terminal.OR
                || peek().terminal == Terminal.RIGHT_BRACKET) {
            return left;
        } else if (peek().terminal == Terminal.AND) {
            eat(Terminal.AND);
            Exp right = parseBNeg();
            return parseBFacs(new AndExp(left, right));
        } else {
            throw new RuntimeException("Unexpected token");
        }

    }

    private Exp parseBNeg() {
//        System.out.println("parseBNeg");

        if (peek().terminal == Terminal.ID
                || peek().terminal == Terminal.NUM
                || peek().terminal == Terminal.TRUE
                || peek().terminal == Terminal.FALSE
                || peek().terminal == Terminal.LEFT_BRACKET) {
            return parseBRel();
        } else if (peek().terminal == Terminal.BANG) {
            eat(Terminal.BANG);
            Exp negatedExp = parseBNeg();  // Recursively parse the negated expression
            return new NotExp(negatedExp);  // Wrap it in a NotExp
        } else {
            throw new RuntimeException("Unexpected token");
        }
    }

    private Exp parseBRel() {
//        System.out.println("parseBRel");

        if (peek().terminal == Terminal.ID
                || peek().terminal == Terminal.NUM
                || peek().terminal == Terminal.TRUE
                || peek().terminal == Terminal.FALSE
                || peek().terminal == Terminal.LEFT_BRACKET) {
            Exp left = parseAExp();  // Parse the left-hand side arithmetic expression
            return parseBRels(left);  // Pass the left-hand side to handle relational operators
        } else {
            throw new RuntimeException("Unexpected token");
        }
    }

    private Exp parseBRels(Exp left) {
//        System.out.println("parseBRels");

        if (peek().terminal == Terminal.THEN
                || peek().terminal == Terminal.DO
                || peek().terminal == Terminal.OR
                || peek().terminal == Terminal.AND
                || peek().terminal == Terminal.RIGHT_BRACKET) {
            return left;
        } else if (peek().terminal == Terminal.LESS_THAN) {
            eat(Terminal.LESS_THAN);
            Exp right = parseAExp();  // Parse the right-hand side arithmetic expression
            return new LessExp(left, right);  // Create and return a LessExp
        } else if (peek().terminal == Terminal.EQUALS) {
            eat(Terminal.EQUALS);
            Exp right = parseAExp();  // Parse the right-hand side arithmetic expression
            return new EqExp(left, right);  // Create and return an EqExp
        } else {
            throw new RuntimeException("Unexpected token");
        }
    }

    private Exp parseAExp() {
//        System.out.println("parseAExp");

        if (peek().terminal == Terminal.ID
                || peek().terminal == Terminal.NUM
                || peek().terminal == Terminal.TRUE
                || peek().terminal == Terminal.FALSE
                || peek().terminal == Terminal.LEFT_BRACKET) {
            Exp left = parseAFac();
            return parseAExps(left);
        } else {
            throw new RuntimeException("Unexpected token");
        }
    }

    private Exp parseAExps(Exp left) {
//        System.out.println("parseAExps");

        if (peek().terminal == Terminal.THEN
                || peek().terminal == Terminal.ELSE
                || peek().terminal == Terminal.DO
                || peek().terminal == Terminal.RIGHT_CURLY_BRACKET
                || peek().terminal == Terminal.SEMICOLON
                || peek().terminal == Terminal.OR
                || peek().terminal == Terminal.AND
                || peek().terminal == Terminal.LESS_THAN
                || peek().terminal == Terminal.EQUALS
                || peek().terminal == Terminal.RIGHT_BRACKET
                || peek().terminal == Terminal.END
        ) {
            return left;
        } else if (peek().terminal == Terminal.PLUS) {
            eat(Terminal.PLUS);
            Exp right = parseAFac();
            return parseAExps(new PlusExp(left, right));
        } else if (peek().terminal == Terminal.MINUS) {
            eat(Terminal.MINUS);
            Exp right = parseAFac();
            return parseAExps(new MinusExp(left, right));
        } else {
            throw new RuntimeException("Unexpected token");
        }
    }

    private Exp parseAFac() {
//        System.out.println("parseAFac");

        if (peek().terminal == Terminal.ID
                || peek().terminal == Terminal.NUM
                || peek().terminal == Terminal.TRUE
                || peek().terminal == Terminal.FALSE
                || peek().terminal == Terminal.LEFT_BRACKET) {
            Exp left = parseAtom();
            return parseAFacs(left);
        } else {
            throw new RuntimeException("Unexpected token");
        }

    }

    private Exp parseAFacs(Exp left) {
//        System.out.println("parseAFacs");

        if (peek().terminal == Terminal.THEN
                || peek().terminal == Terminal.ELSE
                || peek().terminal == Terminal.DO
                || peek().terminal == Terminal.RIGHT_CURLY_BRACKET
                || peek().terminal == Terminal.SEMICOLON
                || peek().terminal == Terminal.OR
                || peek().terminal == Terminal.AND
                || peek().terminal == Terminal.LESS_THAN
                || peek().terminal == Terminal.EQUALS
                || peek().terminal == Terminal.PLUS
                || peek().terminal == Terminal.MINUS
                || peek().terminal == Terminal.RIGHT_BRACKET
                || peek().terminal == Terminal.END
        ) {
            return left;
        } else if (peek().terminal == Terminal.STAR) {
            eat(Terminal.STAR);
            Exp right = parseAtom();
            return parseAFacs(new TimesExp(left, right));
        } else {
            throw new RuntimeException("Unexpected token");
        }
    }

    private Exp parseAtom() {
//        System.out.println("parseAtom");

        if (peek().terminal == Terminal.ID) {
            String name = peek().lexeme;
            eat(Terminal.ID);
            return new VarExp(name);  // Variable expression
        } else if (peek().terminal == Terminal.NUM) {
            int value = Integer.parseInt(peek().lexeme);
            eat(Terminal.NUM);
            return new NumExp(value);  // Number expression
        } else if (peek().terminal == Terminal.TRUE) {
            eat(Terminal.TRUE);
            return new TrueExp();  // True expression
        } else if (peek().terminal == Terminal.FALSE) {
            eat(Terminal.FALSE);
            return new FalseExp();  // False expression
        } else if (peek().terminal == Terminal.LEFT_BRACKET) {
            eat(Terminal.LEFT_BRACKET);
            Exp e = parseBExp();
            eat(Terminal.RIGHT_BRACKET);
            return e;  // Expression in parentheses
        } else {
            throw new RuntimeException("Unexpected token");
        }

    }

    public boolean recognise() {
        try {
            parseProg();
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public Stmt parse() {
        return parseProg();
    }

}
