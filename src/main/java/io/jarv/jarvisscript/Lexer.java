package io.jarv.jarvisscript;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Lexer {
    private final String input;
    private int pos = 0;

    private HashMap<String, String> terminalsMap;

    Lexer(String input) {
        this.input = input;
    }

    Lexer(String input, HashMap<String, String> terminalsMap) {
        this.terminalsMap = terminalsMap;
        this.input = input;
    }

    private char peek() {
        return input.charAt(pos);
    }

    private void eat(char c) {
        if (peek() == c) {
            pos++;
        } else {
            throw new RuntimeException("Unexpected character");
        }
    }

    private boolean is_more() {
        return pos < input.length();
    }

    private Token lex_number() {
        StringBuilder lexeme = new StringBuilder();
        while (peek() >= '0' && peek() <= '9') {
            char c = peek();
            eat(c);
            lexeme.append(c);
        }
        return new Token(Terminal.NUM, lexeme.toString());
    }

    private Token lex_kw_or_id() {
        StringBuilder lexeme = new StringBuilder();
        while (is_more() && ((peek() >= '0' && peek() <= '9' || peek() >= 'a' && peek() <= 'z' || peek() >= 'A' && peek() <= 'Z' || peek() == '\'' || peek() == '-'))) {
            char c = peek();
            lexeme.append(c);
            eat(c);
        }

        if (lexeme.toString().equals(terminalsMap.get("if"))) {
            return new Token(Terminal.IF);
        } else if (lexeme.toString().equals(terminalsMap.get("then"))) {
            return new Token(Terminal.THEN);
        } else if (lexeme.toString().equals(terminalsMap.get("else"))) {
            return new Token(Terminal.ELSE);
        } else if (lexeme.toString().equals(terminalsMap.get("while"))) {
            return new Token(Terminal.WHILE);
        } else if (lexeme.toString().equals(terminalsMap.get("do"))) {
            return new Token(Terminal.DO);
        } else if (lexeme.toString().equals(terminalsMap.get("skip"))) {
            return new Token(Terminal.SKIP);
        } else if (lexeme.toString().equals(terminalsMap.get("true"))) {
            return new Token(Terminal.TRUE);
        } else if (lexeme.toString().equals(terminalsMap.get("false"))) {
            return new Token(Terminal.FALSE);
        } else if (lexeme.toString().equals(terminalsMap.get("print"))) {
            return new Token(Terminal.PRINT);
        } else if (lexeme.toString().equals(terminalsMap.get("println"))) {
            return new Token(Terminal.PRINTLN);
        } else if (lexeme.toString().equals(terminalsMap.get("sleep"))) {
            return new Token(Terminal.SLEEP);
        }
        return new Token(Terminal.ID, lexeme.toString());


//        return switch (lexeme.toString()) {
//            case ifTerminal -> new Token(Terminal.IF);
//            case "then" -> new Token(Terminal.THEN);
//            case "else" -> new Token(Terminal.ELSE);
//            case "while" -> new Token(Terminal.WHILE);
//            case "do" -> new Token(Terminal.DO);
//            case "skip" -> new Token(Terminal.SKIP);
//            case "true" -> new Token(Terminal.TRUE);
//            case "false" -> new Token(Terminal.FALSE);
//            default -> new Token(Terminal.ID, lexeme.toString());
//        };
    }

    public List<Token> lex() {
        List<Token> tokens = new ArrayList<>();

        while (is_more()) {
            char c = peek();
            if (c == '=') {
                eat(c);
                tokens.add(new Token(Terminal.EQUALS));
            } else if (c == '!') {
                eat(c);
                tokens.add(new Token(Terminal.BANG));
            } else if (c == '+') {
                eat(c);
                tokens.add(new Token(Terminal.PLUS));
            } else if (c == '-') {
                eat(c);
                tokens.add(new Token(Terminal.MINUS));
            } else if (c == '*') {
                eat(c);
                tokens.add(new Token(Terminal.STAR));
            } else if (c == ';') {
                eat(c);
                tokens.add(new Token(Terminal.SEMICOLON));
            } else if (c == '&') {
                eat(c);
                eat('&');
                tokens.add(new Token(Terminal.AND));
            } else if (c == '(') {
                eat(c);
                tokens.add(new Token(Terminal.LEFT_BRACKET));
            } else if (c == ')') {
                eat(c);
                tokens.add(new Token(Terminal.RIGHT_BRACKET));
            } else if (c == '{') {
                eat(c);
                tokens.add(new Token(Terminal.LEFT_CURLY_BRACKET));
            } else if (c == '}') {
                eat(c);
                tokens.add(new Token(Terminal.RIGHT_CURLY_BRACKET));
            } else if (c == '|') {
                eat(c);
                eat('|');
                tokens.add(new Token(Terminal.AND));
            } else if (c == '<') {
                eat(c);
                if (peek() == '-') {
                    eat('-');
                    tokens.add(new Token(Terminal.LEFT_ARROW));
                } else {
                    tokens.add(new Token(Terminal.LESS_THAN));
                }
            } else if (peek() >= '0' && peek() <= '9') {
                tokens.add(lex_number());
            } else if (peek() >= 'a' && peek() <= 'z') {
                tokens.add(lex_kw_or_id());
            } else if (Character.isWhitespace(c)) {
                eat(c);
            } else {
                throw new RuntimeException("Unexpected character: " + c + " (position " + pos+")");
            }
        }

        tokens.add(new Token(Terminal.END));

        return tokens;
    }
}
