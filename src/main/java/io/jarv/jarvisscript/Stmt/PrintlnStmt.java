package io.jarv.jarvisscript.Stmt;

import io.jarv.jarvisscript.Exp.Exp;

public class PrintlnStmt implements Stmt {
    public final Exp exp;

    public PrintlnStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "PrintlnStmt{var='" + exp +'}';
    }
}
