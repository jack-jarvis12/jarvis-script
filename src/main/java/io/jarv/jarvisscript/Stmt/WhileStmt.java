package io.jarv.jarvisscript.Stmt;

import io.jarv.jarvisscript.Exp.Exp;


public class WhileStmt implements Stmt {
    public final Exp condition;
    public final Stmt body;

    public WhileStmt(Exp condition, Stmt body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return "WhileStmt{" +
                "condition=" + condition +
                ", body=" + body +
                '}';
    }
}
