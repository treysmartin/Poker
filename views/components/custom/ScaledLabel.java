package views.components.custom;

import javax.swing.*;
import java.awt.*;

public class ScaledLabel extends JLabel {
    public ScaledLabel(Font font, String text) {
        super(text);
        setFont(font);
    }
}
