package base;

public class Equation {
    private String value;

    public Equation(String value) {
        this.value = value;
    }

    public String calculate() {
        System.out.println("Calculating: " + value);

        return "0";
    }
}
