package io.jarv.jarvisscript.Exp;

public class EqExp implements Exp {
    public final Exp left;
    public final Exp right;

    public EqExp(Exp left, Exp right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "EqExp{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
