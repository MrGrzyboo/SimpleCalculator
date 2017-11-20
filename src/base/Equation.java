package base;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class Equation {
    private String value;

    public Equation(String value) {
        this.value = value.replaceAll("\\s+", "");
    }

    public String calculate() throws InvalidOperatorException {
        Stack<Double> stack = new Stack<>();
        LinkedList<String> queue = getReversedPolishNotationQueue();

        Map<String, Operator> map = Operator.getMappedRepresentations();

        try {
            for (String symbol : queue) {
                if (isNumber(symbol)) {
                    double val = Double.valueOf(symbol);
                    stack.push(val);
                }
                else if(isOperator(symbol)) {
                    Operator op = map.get(symbol);
                    double v1 = stack.pop();
                    double v2 = stack.pop();
                    double val = op.evaluate(v2, v1);
                    stack.push(val);
                }
                else if(isFunction(symbol)) {
                    Operator op = map.get(symbol);
                    int paramCount = op.getArgumentsCount();
                    double params[] = new double[paramCount];
                    for(int i = paramCount - 1; i >= 0; --i)
                        params[i] = stack.pop();

                    double val = op.evaluate(params);
                    stack.push(val);
                }
            }
        } catch(NumberFormatException e) {
            throw new InvalidOperatorException("Error: Cannot convert to number: " + e);
        }

        if(stack.empty())
            return "0";

        return cutUnnecessaryZeroes(String.valueOf(stack.pop()));
    }

    private String cutUnnecessaryZeroes(String str) {
        int cut = 0;

        for(int i = str.length() - 1; i >= 0; --i) {
            char c = str.charAt(i);
            if(c == '0')
                ++cut;
            else if(c == '.') {
                ++cut;
                break;
            }
            else break;
        }

        if(cut > 0)
            str = str.substring(0, str.length() - cut);

        return str;
    }

    private LinkedList<String> getReversedPolishNotationQueue() throws InvalidOperatorException {
        Stack<String> stack = new Stack<>();
        LinkedList<String> queue = new LinkedList<>();

        Map<String, Operator> map = Operator.getMappedRepresentations();

        for(String symbol : getExpressionList()) {
            if(isNumber(symbol))
                queue.add(symbol);
            else if(isFunction(symbol))
                stack.push(symbol);
            else if(isOperator(symbol, Operator.COMMA)) {
                while(!stack.empty() && !Operator.PARENTHESIS_LEFT.isEqual(stack.peek())) {
                    String top = stack.pop();
                    queue.add(top);
                }

                if(stack.isEmpty() || !isOperator(stack.peek(), Operator.PARENTHESIS_LEFT))
                    throw new InvalidOperatorException("Error: Invalid commas or parenthesis");

            }
            else if(isOperator(symbol, Operator.PARENTHESIS_LEFT))
                stack.push(symbol);
            else if(isOperator(symbol, Operator.PARENTHESIS_RIGHT)) {
                while(!stack.empty() && !Operator.PARENTHESIS_LEFT.isEqual(stack.peek())) {
                    String top = stack.pop();
                    queue.add(top);
                }

                try {
                    if (Operator.PARENTHESIS_LEFT.isEqual(stack.peek()))
                        stack.pop();
                    else throw new InvalidOperatorException("Parenthesis don't match 1");

                    if(!stack.isEmpty() && isFunction(stack.peek()))
                        queue.add(stack.pop());


                } catch(EmptyStackException e) {
                    throw new InvalidOperatorException("Parenthesis don't match 2");
                }

            }
            else if(isOperator(symbol)) {
                Operator o1 = map.get(symbol);
                while (!stack.empty()) {
                    Operator o2 = map.get(stack.peek());
                    if((o1.isLeftJoined() && o1.getPriority() <= o2.getPriority()) || (o1.isRightJoined() && o1.getPriority() < o2.getPriority()) )
                        queue.add(stack.pop());
                    else
                        break;
                }

                stack.push(symbol);
            }
        }

        while(!stack.empty())
            queue.add(stack.pop());

        return queue;
    }

    private enum ExpressionType {
        NONE,
        NUMERIC,
        FUNCTION
    }

    private LinkedList<String> getExpressionList() {
        LinkedList<String> list = new LinkedList<>();

        ExpressionType type = ExpressionType.NONE;
        StringBuilder expression = new StringBuilder(16);

        int i = 0;
        while (i < value.length()) {
            if (type == ExpressionType.NONE) {
                if(expression.length() > 0)
                    expression.setLength(0);

                char c = value.charAt(i);
                if(isNumeric(c)) {
                    type = ExpressionType.NUMERIC;
                    expression.append(c);
                }
                else if(isLetter(c)){
                    type = ExpressionType.FUNCTION;
                    expression.append(c);
                }
                else { // Single char operator, parenthesis, comma etc
                    String str = String.valueOf(c);
                    if(str.equals(Operator.PLUS.toString()) || str.equals(Operator.MINUS.toString())) {
                        if(i == 0)
                            list.add("0");
                        else {
                            String previous = String.valueOf(value.charAt(i - 1));
                            if(previous.equals(Operator.COMMA.toString()) || previous.equals(Operator.PARENTHESIS_LEFT.toString()))
                                list.add("0");
                        }
                    }

                    list.add(str);
                }

                if(++i == value.length() && expression.length() > 0)
                    list.add(expression.toString());
            }

            else if(type == ExpressionType.NUMERIC) {
                char c;
                while(i < value.length() && isNumeric(c = value.charAt(i))) {
                    expression.append(c);
                    ++i;
                }
                list.add(expression.toString());
                type = ExpressionType.NONE;
            }

            else {
                char c;
                while(i < value.length() && isLetter(c = value.charAt(i))) {
                    expression.append(c);
                    ++i;
                }
                list.add(expression.toString());
                type = ExpressionType.NONE;
            }
        }

        return list;
    }

    private boolean isNumeric(char c) {
        return Character.isDigit(c) || c == '.';
    }

    private boolean isNumber(String s) {
        return isNumeric(s.charAt(0));
    }

    private boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private boolean isFunction(String s) {
        return isLetter(s.charAt(0));
    }

    private boolean isOperator(String s) {
        char c = s.charAt(0);
        return (!isLetter(c) && !isNumeric(c));
    }

    private boolean isOperator(String s, Operator op) {
        return s.equals(op.toString());
    }
}
