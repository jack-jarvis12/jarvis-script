package io.jarv.jarvisscript.Stmt;

import io.jarv.jarvisscript.Exp.*;

public class AssnStmt implements Stmt {
    public final String var;
    public final Exp exp;

    public AssnStmt(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "AssnStmt{" +
                "var='" + var + '\'' +
                ", exp=" + exp +
                '}';
    }
    
}
