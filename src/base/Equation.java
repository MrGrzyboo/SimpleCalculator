package base;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class Equation {
    private String value;

    private Map<String, Operator> map;
    private Stack<Pair<String, ExpressionType>> stack;
    private LinkedList<Pair<String, ExpressionType>> queue;

    public Equation(String value) {
        this.value = value.replaceAll("\\s","");
        map = Operator.getMappedRepresentations();
    }

    private enum ExpressionType {
        NONE,
        NUMERIC,
        FUNCTION,
        SINGLE_CHAR_OPERATOR
    }

    public String calculate() throws InvalidExpressionException {
        stack = new Stack<>();
        queue = new LinkedList<>();

        LinkedList<Pair<String, ExpressionType>> listOfExpressions = getExpressionList();
        for(Pair<String, ExpressionType> expr : listOfExpressions) {
            String str = expr.getKey();
            ExpressionType type = expr.getValue();

            if(type == ExpressionType.NUMERIC) {
                queue.add(expr);
            }
            else if(type == ExpressionType.FUNCTION) {
                stack.push(expr);
            }
            else if(type == ExpressionType.SINGLE_CHAR_OPERATOR) {
                Operator op = map.get(str);
                if(op == Operator.COMMA) {
                    while(map.get(stack.peek().getKey()) != Operator.PARENTHESIS_LEFT) {
                        Pair<String, ExpressionType> top = stack.pop();
                        queue.add(top);

                        if (stack.empty())
                            throw new InvalidExpressionException("Comma not placed correctly");
                    }
                }
                else if(op == Operator.PARENTHESIS_LEFT)
                    stack.push(expr);
                else if(op == Operator.PARENTHESIS_RIGHT){
                    while(!stack.empty() && map.get(stack.peek().getKey()) != Operator.PARENTHESIS_LEFT) {
                        Pair<String, ExpressionType> top = stack.pop();
                        queue.add(top);

                        //if (stack.empty())
                        //    throw new InvalidExpressionException("Parenthesis doesn't match");
                    }

                    if(!stack.empty()) {
                        stack.pop();

                        if (stack.peek().getValue() == ExpressionType.FUNCTION)
                            queue.add(stack.pop());
                    }
                }
                else {
                    if(!stack.empty())
                    {
                        Operator nextOp = map.get(stack.peek().getKey());
                        while (( (op.isLeftJoined() && op.getPriority() <= nextOp.getPriority())
                                || (op.isRightJoined() && op.getPriority() < nextOp.getPriority())) && !stack.empty()) {
                            queue.add(stack.pop());
                        }
                    }

                    stack.push(expr);
                }


            }
        }

        while(!stack.empty()) {
            if(stack.peek().getValue() != ExpressionType.SINGLE_CHAR_OPERATOR)
                throw new InvalidExpressionException("Parenthesis doesn't match 2");

            queue.add(stack.pop());
        }

        String xd = "";
        for(Pair<String, ExpressionType> e : queue) {
            xd += " ";
            xd += e.getKey();
        }

        System.out.println(xd);

        return "0";
    }

    private LinkedList<Pair<String, ExpressionType>> getExpressionList() {
        LinkedList<Pair<String, ExpressionType>> list = new LinkedList<>();

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
                else // Single char operator, parenthesis, comma etc
                    list.add(new Pair<>(String.valueOf(c), ExpressionType.SINGLE_CHAR_OPERATOR));

                ++i;
            }

            else if(type == ExpressionType.NUMERIC) {
                char c;
                while(isNumeric(c = value.charAt(i))) {
                    expression.append(c);
                    ++i;
                }
                list.add(new Pair<>(expression.toString(), ExpressionType.NUMERIC));
                type = ExpressionType.NONE;
            }

            else {
                char c;
                while(isLetter(c = value.charAt(i))) {
                    expression.append(c);
                    ++i;
                }

                list.add(new Pair<>(expression.toString(), ExpressionType.FUNCTION));
                type = ExpressionType.NONE;
            }
        }

        return list;
    }

    private boolean isNumeric(char c) {
        return Character.isDigit(c) || c == '.';
    }

    private boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }


    // https://pl.wikipedia.org/wiki/Odwrotna_notacja_polska
}



/*
    expr jest liczba -> values.add
    expr jest funkcja -> operators.add
    expr jest , ->

 */