package views.components.custom;

import javax.swing.*;

class VerticalPanel extends JPanel {
    VerticalPanel(JComponent... content) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (JComponent component : content) {
            component.setAlignmentX(CENTER_ALIGNMENT);
            add(component);
        }
    }
}
