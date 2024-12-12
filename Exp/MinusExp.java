package Exp;

public class MinusExp implements Exp {
    public final Exp left;
    public final Exp right;

    public MinusExp(Exp left, Exp right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "MinusExp{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
