package base;

public class Equation {
    private String value;

    Equation(String value) {
        this.value = value;
    }

    String calculate() {
        System.out.println("Calculating: " + value);

        return "0";
    }
}
