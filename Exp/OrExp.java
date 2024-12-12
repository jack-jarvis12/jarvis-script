package Exp;

public class OrExp implements Exp {
    public final Exp left;
    public final Exp right;

    public OrExp(Exp left, Exp right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "OrExp{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
