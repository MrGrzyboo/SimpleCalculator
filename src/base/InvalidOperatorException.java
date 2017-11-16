package base;

public class InvalidOperatorException extends Exception {
    String msg;
    InvalidOperatorException(String msg) {
        this.msg = msg;
    }

    public String toString() {
        return msg;
    }
}
