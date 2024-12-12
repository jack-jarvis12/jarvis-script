package io.jarv.jarvisscript.Exp;

public class VarExp implements Exp {
    public final String name;

    public VarExp(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "VarExp{" +
                "name='" + name + '\'' +
                '}';
    }
}
