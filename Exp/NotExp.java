package Exp;

public class NotExp implements Exp {
    public final Exp exp;

    public NotExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "NotExp{" +
                "exp=" + exp +
                '}';
    }
}
