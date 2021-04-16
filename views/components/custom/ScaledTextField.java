package views.components.custom;

import javax.swing.*;
import java.awt.*;

public class ScaledTextField extends JTextField {
    public ScaledTextField(Font defaultFont, int value) {
        super(value);
        setFont(defaultFont);
        setMaximumSize(getPreferredSize());
        setHorizontalAlignment(CENTER);
    }
}
