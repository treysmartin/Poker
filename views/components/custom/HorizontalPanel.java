package views.components.custom;

import javax.swing.*;
import java.awt.*;

class HorizontalPanel extends JPanel {
    HorizontalPanel(JComponent... content) {
        super(new FlowLayout());
        for (JComponent component : content) {
            component.setAlignmentY(CENTER_ALIGNMENT);
            add(component);
        }
    }
}
