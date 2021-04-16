package views;

import javax.swing.*;

public interface HandView {
    JPanel getView();
    CardView[] getCardPanels();
    JLabel getBetLabel();
    JLabel getMoneyLabel();
    void isRevealed(boolean isRevealed);
}
