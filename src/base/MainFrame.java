package base;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private JTextField equationsField;
    private JButton calculateButton;

    MainFrame(String title) {
        super(title);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setPreferredSize(new Dimension(200, 200));
        add(mainPanel);

        createContent();
    }

    private void createContent() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        equationsField = new EquationsTextField();
        equationsField.setPreferredSize(new Dimension(180, 25));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(equationsField, gbc);

        JPanel emptyPanel = new JPanel();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        mainPanel.add(emptyPanel, gbc);

        calculateButton = new JButton("Calculate");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        mainPanel.add(calculateButton, gbc);
    }
}
