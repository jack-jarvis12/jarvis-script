package Stmt;

public class SeqStmt implements Stmt {
    public final Stmt first;
    public final Stmt second;

    public SeqStmt(Stmt first, Stmt second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "SeqStmt{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
