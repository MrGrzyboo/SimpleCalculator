package base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EquationTest {

    @Test
    public void testEquationCalculation() {
        testCase("2+2*2", "6");
        testCase("(2+2)*2", "8");
        testCase("12+5/2*5-12", "12.5");
        testCase("12+5/(2*5-12)", "-9.5");
        testCase("12+2*4^3*0.5-3", "73");
    }

    private void testCase(String equation, String expectedResult) {
        String result = new Equation(equation).calculate();
        Assertions.assertEquals(expectedResult, result);
    }
}