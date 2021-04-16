package views.components.custom;

import javax.swing.*;
import java.awt.*;

public class ScaledSlider extends JSlider {
    public ScaledSlider(Font defaultFont, int value) {
        super(value > 0 ? 1 : 0, value);
        setMaximumSize(new Dimension(getPreferredSize().width, 65));
    }
}
