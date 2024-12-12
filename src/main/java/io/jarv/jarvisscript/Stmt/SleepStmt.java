package io.jarv.jarvisscript.Stmt;

import io.jarv.jarvisscript.Exp.Exp;

public class SleepStmt implements Stmt {
    public final Exp exp;

    public SleepStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "SleepStmt{var='" + exp +'}';
    }
}
