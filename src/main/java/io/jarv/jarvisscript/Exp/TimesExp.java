package io.jarv.jarvisscript.Exp;

public class TimesExp implements Exp {
    public final Exp left;
    public final Exp right;

    public TimesExp(Exp left, Exp right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "TimesExp{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
