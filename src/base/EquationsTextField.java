package base;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.event.KeyEvent;

public class EquationsTextField extends JTextField {

    EquationsTextField() {
        super(getFormattingDocument(), null, 0);
    }

    private static int acceptedKeys[] = {
            KeyEvent.VK_0,KeyEvent.VK_1,KeyEvent.VK_2,KeyEvent.VK_3,KeyEvent.VK_4,KeyEvent.VK_5,KeyEvent.VK_6,KeyEvent.VK_7,KeyEvent.VK_8,KeyEvent.VK_9,
            KeyEvent.VK_PLUS,KeyEvent.VK_MINUS,KeyEvent.VK_ASTERISK,KeyEvent.VK_SLASH
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
                    for(int acceptedKey : acceptedKeys) {
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
