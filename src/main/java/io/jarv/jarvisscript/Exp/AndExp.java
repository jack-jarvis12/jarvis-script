package io.jarv.jarvisscript.Exp;

public class AndExp implements Exp {
    public final Exp left;
    public final Exp right;

    public AndExp(Exp left, Exp right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "AndExp{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
