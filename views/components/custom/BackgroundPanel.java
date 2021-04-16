package views.components.custom;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private ImageIcon backgroundImageIcon;

    public BackgroundPanel(String backgroundPath) {
        super();
        backgroundImageIcon = new ImageIcon(backgroundPath);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Image unscaledImage = backgroundImageIcon.getImage();
        Image scaledImage = unscaledImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage); //For some unknown reason this line of code is necessary
        g.drawImage(scaledImage, 0, 0, null);
    }
}
