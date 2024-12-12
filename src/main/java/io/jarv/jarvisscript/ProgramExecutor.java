package io.jarv.jarvisscript;

import io.jarv.jarvisscript.Exp.*;
import io.jarv.jarvisscript.Stmt.*;

import java.util.Hashtable;
import java.util.Objects;

public class ProgramExecutor {
    private final Hashtable<String, Integer> state;

    public ProgramExecutor() {
        state = new Hashtable<>();
    }

    public void setVar(String var, Integer value) {
        state.put(var, value);
    }

    public Integer lookupVar(String var) {
        Integer value = state.get(var);

        if (value == null) {
            state.put(var, 0);
            return 0;
        } else {
            return value;
        }
    }

    public void printState() {
        System.out.println(state);
    }

    public Integer evaluateArithmetic(Exp expression) {
        if (expression instanceof NumExp numExp) {
            return numExp.value;
        } else if (expression instanceof VarExp varExp) {
            return lookupVar(varExp.name);
        } else if (expression instanceof PlusExp addExp) {
            return evaluateArithmetic(addExp.left) + evaluateArithmetic(addExp.right);
        } else if (expression instanceof MinusExp minusExp) {
            return evaluateArithmetic(minusExp.left) - evaluateArithmetic(minusExp.right);
        } else if (expression instanceof TimesExp timesExp) {
            return evaluateArithmetic(timesExp.left) * evaluateArithmetic(timesExp.right);
        } else {
            throw new IllegalArgumentException("Unknown arithmetic expression type");
        }
    }

    public Boolean evaluateBoolean(Exp expression) {
        if (expression instanceof TrueExp) {
            return true;
        } else if (expression instanceof FalseExp) {
            return false;
        } else if (expression instanceof NotExp notExp) {
            return !evaluateBoolean(notExp.exp);
        } else if (expression instanceof AndExp andExp) {
            return evaluateBoolean(andExp.left) && evaluateBoolean(andExp.right);
        } else if (expression instanceof OrExp orExp) {
            return evaluateBoolean(orExp.left) || evaluateBoolean(orExp.right);
        } else if (expression instanceof EqExp eqExp) {
            return Objects.equals(evaluateArithmetic(eqExp.left), evaluateArithmetic(eqExp.right));
        } else if (expression instanceof LessExp lessExp) {
            return evaluateArithmetic(lessExp.left) < evaluateArithmetic(lessExp.right);
        } else {
            throw new IllegalArgumentException("Unknown boolean expression type");
        }
    }

    public void executeStatement(Stmt statement) {
        if (statement instanceof SkipStmt) {
            // Do nothing
        } else if (statement instanceof PrintStmt printStmt) {
            System.out.print(evaluateArithmetic(printStmt.exp));
        } else if (statement instanceof PrintlnStmt printStmt) {
            System.out.println(evaluateArithmetic(printStmt.exp));
        } else if (statement instanceof SleepStmt sleepStmt) {
            try {
                Thread.sleep(evaluateArithmetic(sleepStmt.exp));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else if (statement instanceof AssnStmt assnStmt) {
            setVar(assnStmt.var, evaluateArithmetic(assnStmt.exp));
        } else if (statement instanceof SeqStmt seqStmt) {
            executeStatement(seqStmt.first);
            executeStatement(seqStmt.second);
        } else if (statement instanceof CondStmt condStmt) {
            if (evaluateBoolean(condStmt.condition)) {
                executeStatement(condStmt.thenBranch);
            } else {
                executeStatement(condStmt.elseBranch);
            }
        } else if (statement instanceof WhileStmt whileStmt) {
            while (evaluateBoolean(whileStmt.condition)) {
                executeStatement(whileStmt.body);
            }
        } else {
            throw new IllegalArgumentException("Unknown statement type");
        }
    }

}
