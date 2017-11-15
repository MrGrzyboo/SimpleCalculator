package base;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

public class EquationsTextField extends JTextField {

    EquationsTextField() {
        super(getFormattingDocument(), null, 0);
    }

    private static char acceptedKeys[] = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '*', '/', '^', '%', '(', ')', '.', ' '
    };

    private static PlainDocument getFormattingDocument() {
        PlainDocument document = new PlainDocument();
        document.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
                fb.insertString(offset, filter(text), attrs);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                fb.replace(offset, length, filter(text), attrs);
            }

            private String filter(String text) {
                StringBuilder builder = new StringBuilder(text);
                for (int i = 0; i < builder.length();) {

                    boolean found = false;

                    char c = builder.charAt(i);

                    if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
                        found = true;
                    else for(int acceptedKey : acceptedKeys) {
                        if(builder.charAt(i) == acceptedKey) {
                            found = true;
                            break;
                        }
                    }

                    if(found)
                        ++i;
                    else
                        builder.deleteCharAt(i);
                }
                return builder.toString();
            }
        });

        return document;
    }




}
