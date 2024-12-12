package io.jarv.jarvisscript.Exp;

public class LessExp implements Exp {
    public final Exp left;
    public final Exp right;

    public LessExp(Exp left, Exp right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "LessExp{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
