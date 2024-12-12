package io.jarv.jarvisscript.Stmt;

import io.jarv.jarvisscript.Exp.Exp;

public class PrintStmt implements Stmt {
    public final Exp exp;

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "PrintStmt{var='" + exp +'}';
    }
}
