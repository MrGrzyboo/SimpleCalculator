package base;

public class InvalidExpressionException extends Exception {
    private String message;

    public InvalidExpressionException(String message) {
        this.message = message;
    }

    public String toString() {
        return message;
    }
}
