package Exp;

public class PlusExp implements Exp {
    public final Exp left;
    public final Exp right;

    public PlusExp(Exp left, Exp right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "PlusExp{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
