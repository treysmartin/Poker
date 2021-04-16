package views.components.custom;

import javax.swing.*;

public class PanelFactory {
    public static JPanel createPanel(PanelType type, JComponent... content) {
        switch (type) {
            case HORIZONTAL:
                return new HorizontalPanel(content);
            case VERTICAL:
                return new VerticalPanel(content);
            default:
                return new JPanel();
        }
    }
}
