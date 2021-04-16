package views;

import javax.swing.*;

public interface OptionsView {
    JSlider getOpponentSlider();
    JTextField getStartingMoneyTextField();
    JTextField getAnteTextField();
    JButton getStartGameButton();
    JPanel getView();
}
