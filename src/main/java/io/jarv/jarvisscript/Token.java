package io.jarv.jarvisscript;

public class Token {
    public String lexeme;
    public Terminal terminal;

    Token(Terminal terminal, String lexeme) {
        this.terminal = terminal;
        this.lexeme = lexeme;
    }

    Token(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public String toString() {
        if (lexeme == null) {
            return "(" + terminal + ")";
        } else {
            return "(" + terminal + "," + lexeme + ")";
        }
    }
}
