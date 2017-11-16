package base;

import java.util.HashMap;
import java.util.Map;

public enum Operator {
    PARENTHESIS_LEFT("(", false, false, 0),
    PARENTHESIS_RIGHT(")", false, false, 1),
    PLUS("+", true, true, 1),
    MINUS("-", true, true, 1),
    MULTIPLY("*", true, true, 2),
    DIVIDE("/", true, false, 2),
    POWER("^", false, false, 3),
    SQRT("sqrt", false, false, 3),
    COMMA(",", false, false, 3);

    private String strRepresentation;
    private boolean leftJoined;
    private boolean rightJoined;
    private int priority;

    Operator(String strRepresentation, boolean leftJoined, boolean rightJoined, int priority) {
        this.strRepresentation = strRepresentation;
        this.leftJoined = leftJoined;
        this.rightJoined = rightJoined;
        this.priority = priority;
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
}

