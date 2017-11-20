package base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EquationTest {

    @Test
    public void testEquationCalculation() throws Exception {
        testCase("2+2*2", "6.0");
        testCase("(2+2)*2", "8.0");
        testCase("12+5/2*5-12", "12.5");
        testCase("0-12-5/(2*5-12)", "-9.5");
        testCase("-12-5/(-2*5-10)", "-11.75");
        testCase("+2-5*(-2)-10", "2.0");
        testCase("12+2*4^3*0.5-3", "73.0");
        testCase("1+(-1 + (0.25/2.5) * 12+4.8)/2 - 4", "-0.5");
        testCase("-sin(" + (Math.PI*0.5) + ") + root(16, 4) - cos(-" + Math.PI +")", "2.0");
        testCase("-2^2+tg(" + (Math.PI*0.25) + ") - 2*ctg(" + (Math.PI*0.25) + ")", "-5.0");
        testCase("sqrt(2*4^(0.5*sin(" + (Math.PI/2) + "))) - root(25*4 + 25, 3*cos(0))", "-3.0");
    }

    private void testCase(String equation, String expectedResult) throws Exception {
        String result = new Equation(equation).calculate();
        double val1 = Double.valueOf(result);
        double val2 = Double.valueOf(expectedResult);
        Assertions.assertTrue(Math.abs(val1 - val2) < 0.00000001);
    }
}