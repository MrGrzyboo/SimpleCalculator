package base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EquationsTextFieldTest {

    @Test
    public void testTextValidation() {
        String text = "abc123+ad3-4&-12*4*A+B-C  12^2+(A12+11*3)";

        EquationsTextField field = new EquationsTextField();
        field.setText(text);

        Assertions.assertEquals(field.getText(), "123+3-4-12*4*+-  12^2+(12+11*3)");
    }
}