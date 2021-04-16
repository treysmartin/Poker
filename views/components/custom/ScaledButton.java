package views.components.custom;

import javax.swing.*;
import java.awt.*;

public class ScaledButton extends JButton {
    public ScaledButton(Font defaultFont, String value) {
        super(value);
        setFont(defaultFont);
    }
}
