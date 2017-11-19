package base;

import java.util.HashMap;
import java.util.Map;

public enum Operator {
    PARENTHESIS_LEFT("("),
    PARENTHESIS_RIGHT(")"),
    PLUS("+", true, true, 1, a -> a[0]+a[1], 2),
    MINUS("-", true, true, 1, a -> a[0]-a[1], 2), // Won't work for -12 etc. (-5+4)
    MULTIPLY("*", true, true, 2, a -> a[0]*a[1], 2),
    DIVIDE("/", true, false, 2, a -> a[0]/a[1], 2),
    POWER("^", false, false, 3, a -> Math.pow(a[0], a[1]), 2),
    SQRT("sqrt", false, false, 3, a -> Math.sqrt(a[0]), 1),
    COMMA(",");

    private String strRepresentation;
    private boolean leftJoined;
    private boolean rightJoined;
    private int priority;

    private MathFunction function;
    private int argumentsCount;

    // For functions
    Operator(String strRepresentation, boolean leftJoined, boolean rightJoined, int priority, MathFunction function, int argumentsCount) {
        this.strRepresentation = strRepresentation;
        this.leftJoined = leftJoined;
        this.rightJoined = rightJoined;
        this.priority = priority;
        this.function = function;
        this.argumentsCount = argumentsCount;
    }

    // For special symbols
    Operator(String strRepresentation) {
        this.strRepresentation = strRepresentation;
        this.leftJoined = false;
        this.rightJoined = false;
        this.priority = 0;
        this.function = null;
        this.argumentsCount = 0;
    }

    @Override
    public String toString() {
        return strRepresentation;
    }

    static Map<String, Operator> getMappedRepresentations() {
        Map<String, Operator> map = new HashMap<>();
        for(Operator op : Operator.values())
            map.put(op.strRepresentation, op);

        return map;
    }

    public boolean isLeftJoined() {
        return leftJoined;
    }

    public boolean isRightJoined() {
        return rightJoined;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isEqual(String s) {
        return strRepresentation.equals(s);
    }

    public int getArgumentsCount() {
        return argumentsCount;
    }

    public double evaluate(double... args) {
        if(args.length < this.argumentsCount)
            return 0; // throw something

        return function.evaluate(args);
    }
}

