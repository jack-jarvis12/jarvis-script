package Exp;

public class NumExp implements Exp {
    public final int value;

    public NumExp(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "NumExp{" +
                "value=" + value +
                '}';
    }
}
