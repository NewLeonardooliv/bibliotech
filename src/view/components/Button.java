package view.components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Button {
    Color backgrouColor = GREEN;

    public static final Color BLUE = new Color(71, 138, 255);
    public static final Color RED = new Color(255, 71, 71);
    public static final Color YELLOW = new Color(255, 227, 71);
    public static final Color GREEN = new Color(0, 237, 59);
    public static final Color CYAN = new Color(0, 237, 226);
    public static final Color ORANGE = new Color(255, 158, 0);

    public JButton get(String buttonName) {
        JButton button = new JButton();

        button.setText(buttonName);
        button.setBackground(this.getBackgroundColor());
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);

        int padding = 6;
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY, 1),
                new EmptyBorder(padding, padding, padding, padding)));

        return button;
    }

    public Button setBackgroundColor(Color color) {
        this.backgrouColor = color;

        return this;
    }

    private Color getBackgroundColor() {
        return this.backgrouColor;
    }
}
