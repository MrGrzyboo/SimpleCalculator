package base;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
	    JFrame frame = new MainFrame("Simple calculator");
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
    }
}
