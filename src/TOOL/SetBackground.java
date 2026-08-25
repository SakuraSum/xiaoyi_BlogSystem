package TOOL;

import javax.swing.*;
import java.awt.*;

public class SetBackground extends JPanel {
    private Image backgroundImage;

    public SetBackground(String fileName) {
        backgroundImage = new ImageIcon(fileName).getImage();
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
