package base;

import java.util.HashMap;
import java.util.Map;

public enum Operator {
    PARENTHESIS_LEFT("("),
    PARENTHESIS_RIGHT(")"),
    COMMA(","),
    PLUS("+", true, true, 1, a -> a[0]+a[1]),
    MINUS("-", true, true, 1, a -> a[0]-a[1]), // Won't work for -12 etc. (-5+4)
    MULTIPLY("*", true, true, 2, a -> a[0]*a[1]),
    DIVIDE("/", true, false, 2, a -> a[0]/a[1]),
    POWER("^", false, false, 3, a -> Math.pow(a[0], a[1])),
    SQRT("sqrt", a -> Math.sqrt(a[0]), 1),
    ROOT("root", a -> Math.pow(a[0], 1.0/a[1]), 2),
    SIN("sin", a -> Math.sin(a[0]), 1),
    COS("cos", a -> Math.cos(a[0]), 1),
    TAN("tg", a -> Math.tan(a[0]), 1),
    CTAN("ctg", a -> 1/Math.tan(a[0]), 1);


    private String strRepresentation;
    private boolean leftJoined;
    private boolean rightJoined;
    private int priority;

    private MathFunction function;
    private int argumentsCount;

    // For operators
    Operator(String strRepresentation, boolean leftJoined, boolean rightJoined, int priority, MathFunction function) {
        this.strRepresentation = strRepresentation;
        this.leftJoined = leftJoined;
        this.rightJoined = rightJoined;
        this.priority = priority;
        this.function = function;
        this.argumentsCount = 2;
    }

    // For functions
    Operator(String strRepresentation, MathFunction function, int argumentsCount) {
        this.strRepresentation = strRepresentation;
        this.leftJoined = false;
        this.rightJoined = false;
        this.priority = 3;
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

    public double evaluate(double... args) throws InvalidOperatorException {
        if(args.length < this.argumentsCount)
            throw new InvalidOperatorException("Not enough arguments passed to a function or operator");

        return function.evaluate(args);
    }
}

