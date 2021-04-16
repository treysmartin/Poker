package views.components.custom;

import javax.swing.*;
import java.awt.*;

public class ScaledComponentFactory {
    private static Font defaultFont = new Font("Symbola", Font.PLAIN, 22);

    public static JComponent createComponent(ScaledComponentType type, String value) {
        switch (type) {
            case BUTTON:
                return new ScaledButton(defaultFont, value);
            case LABEL:
                return new ScaledLabel(defaultFont, value);
            default:
                return null;
        }
    }

    public static JComponent createComponent(ScaledComponentType type, int value) {
        switch (type) {
            case SLIDER:
                return new ScaledSlider(defaultFont, value);
            case TEXTFIELD:
                return new ScaledTextField(defaultFont, value);
            default:
                return null;
        }
    }

    public static void setDefaultFont(Font defaultFont) {
        ScaledComponentFactory.defaultFont = defaultFont;
    }
}
