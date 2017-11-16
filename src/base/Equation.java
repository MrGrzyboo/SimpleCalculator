package base;

import javafx.util.Pair;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class Equation {
    private String value;

    public Equation(String value) {
        this.value = value;
    }

    public String calculate() throws InvalidOperatorException {
        Stack<String> stack = new Stack<>();
        LinkedList<String> queue = new LinkedList<>();

        Map<String, Operator> map = Operator.getMappedRepresentations();

        for(String symbol : getExpressionList()) {
            System.out.println("symbol: " + symbol);
            if(isNumber(symbol)) {
                queue.add(symbol);
                d("Dodaj " + symbol, queue, stack);
            }
            else if(isFunction(symbol)) {
                stack.push(symbol);
                d("Push " + symbol, queue, stack);
            }
            else if(isOperator(symbol, Operator.COMMA)) {
                while(!stack.empty() && !Operator.PARENTHESIS_LEFT.isEqual(stack.peek())) {
                    String top = stack.pop();
                    queue.add(top);
                    d("Dodaj " + symbol, queue, stack);
                }

                if(stack.isEmpty() || isOperator(stack.peek(), Operator.PARENTHESIS_LEFT))
                    throw new InvalidOperatorException("Error: Invalid commas or parenthesis");

            }
            else if(isOperator(symbol, Operator.PARENTHESIS_LEFT)) {
                stack.push(symbol);
                d("Push " + symbol, queue, stack);
            }
            else if(isOperator(symbol, Operator.PARENTHESIS_RIGHT)) {
                while(!stack.empty() && !Operator.PARENTHESIS_LEFT.isEqual(stack.peek())) {
                    String top = stack.pop();
                    d("Zdejmij " + symbol, queue, stack);
                    queue.add(top);
                    d("Dodaj " + symbol, queue, stack);
                }

                try {
                    if (Operator.PARENTHESIS_LEFT.isEqual(stack.peek())) {
                        stack.pop();
                        d("Zdejmij " + symbol, queue, stack);
                    }
                    else throw new InvalidOperatorException("Parenthesis don't match 1");

                    if(isFunction(stack.peek())) {
                        queue.add(stack.pop());
                        d("Zdejmij " + symbol, queue, stack);
                    }


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
                d("Push " + symbol, queue, stack);
            }
        }

        while(!stack.empty())
            queue.add(stack.pop());

        for(String str : queue)
            System.out.print(str + " ");

        return "0";
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
                else // Single char operator, parenthesis, comma etc
                    list.add(String.valueOf(c));

                if(++i == value.length())
                    list.add(expression.toString());
            }

            else if(type == ExpressionType.NUMERIC) {
                char c;
                while(isNumeric(c = value.charAt(i))) {
                    expression.append(c);
                    ++i;
                }
                list.add(expression.toString());
                type = ExpressionType.NONE;
            }

            else {
                char c;
                while(isLetter(c = value.charAt(i))) {
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

    private void d(String co, LinkedList<String> queue, Stack<String> stack) {
        /*System.out.println(co);
        for(String str : queue)
            System.out.print(str + " ");
        System.out.println();
        for(String str : stack)
            System.out.print(str + " ");
        System.out.println();*/
    }
}
