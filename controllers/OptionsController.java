package controllers;

import java.util.*;
import model.*;
import views.OptionsView;
import views.Window;
import views.screens.SettingsScreen;
import javax.swing.*;

public class OptionsController {
    public OptionsController(Window window) {
        OptionsView options = new SettingsScreen(3);
        window.setContent(options.getView());
        options.getStartGameButton().addActionListener(event -> {
            String errorMessage = "";
            if (options.getStartingMoneyTextField().getText().equals("") || options.getAnteTextField().getText().equals("")) {
                errorMessage = "Enter a value for starting money and ante amount";
                JOptionPane.showMessageDialog(window.getFrame(), errorMessage, "User Options Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int numOpponents = options.getOpponentSlider().getValue();
            int startingMoney = Integer.parseInt(options.getStartingMoneyTextField().getText());
            if (startingMoney <= 0) {
                errorMessage = "Starting money must be positive";
                JOptionPane.showMessageDialog(window.getFrame(), errorMessage, "User Options Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int anteAmount = Integer.parseInt(options.getAnteTextField().getText());
            if (anteAmount <= 0) {
                errorMessage = "Ante amount must be positive";
                JOptionPane.showMessageDialog(window.getFrame(), errorMessage, "User Options Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (anteAmount > startingMoney) {
                errorMessage = "Ante amount must be less than starting money";
                JOptionPane.showMessageDialog(window.getFrame(), errorMessage, "User Options Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new TableController(window, options.getOpponentSlider().getValue(), Integer.parseInt(options.getStartingMoneyTextField().getText()), Integer.parseInt(options.getAnteTextField().getText()));
        });
    }
}
