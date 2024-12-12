package io.jarv.jarvisscript.Stmt;

import io.jarv.jarvisscript.Exp.Exp;

public class CondStmt implements Stmt {
    public final Exp condition;
    public final Stmt thenBranch;
    public final Stmt elseBranch;

    public CondStmt(Exp condition, Stmt thenBranch, Stmt elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public String toString() {
        return "CondStmt{" +
                "condition=" + condition +
                ", thenBranch=" + thenBranch +
                ", elseBranch=" + elseBranch +
                '}';
    }
}
