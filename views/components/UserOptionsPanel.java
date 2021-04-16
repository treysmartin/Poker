package views.components;

import views.UserActionsView;
import views.components.custom.ScaledComponentFactory;
import views.components.custom.ScaledComponentType;

import javax.swing.*;
import java.awt.*;

public class UserOptionsPanel extends JPanel implements UserActionsView {
    private static final Dimension PREFERRED_SIZE = new Dimension(500, 20);
    private static final int SLIDER = 0, CHECK = 1, CALL = 2, BET = 3, FOLD = 4, DONE = 5, CHECK_BOXES = 6;
    private boolean[] isVisible;
    private JButton buttonBet, buttonCall, buttonCheck, buttonFold, commitBetButton, buttonDone;
    private JSlider sliderBet;
    private JLabel labelBet;
    private JCheckBox firstCardToReplace, secondCardToReplace, thirdCardToReplace, fourthCardToReplace, fifthCardToReplace;

    public UserOptionsPanel(int totalMoney) {
        isVisible = new boolean[7];
        isVisible[SLIDER] = false;
        isVisible[CHECK] = true;
        isVisible[CALL] = false;
        isVisible[BET] = true;
        isVisible[FOLD] = true;
        isVisible[DONE] = false;
        isVisible[CHECK_BOXES] = false;

        sliderBet = (JSlider) ScaledComponentFactory.createComponent(ScaledComponentType.SLIDER, totalMoney);
        sliderBet.setPaintLabels(true);
        labelBet = new JLabel("" + sliderBet.getValue());
        commitBetButton = new JButton("Bet?");
        buttonBet = new JButton("Raise");
        buttonCall = new JButton("Call");
        buttonCheck = new JButton("Check");
        buttonFold = new JButton("Fold");
        buttonDone = new JButton("Done");
        firstCardToReplace = new JCheckBox("0");
        secondCardToReplace = new JCheckBox("1");
        thirdCardToReplace = new JCheckBox("2");
        fourthCardToReplace = new JCheckBox("3");
        fifthCardToReplace = new JCheckBox("4");

        addActions();
    }

    private void addActions() {
        removeAll();
        if (isVisible[SLIDER]) {
            add(sliderBet);
            add(labelBet);
            add(commitBetButton);
        }
        if (isVisible[BET]) {
            add(buttonBet);
        }
        if (isVisible[CHECK]) {
            add(buttonCheck);
        }
        if (isVisible[CALL]) {
            add(buttonCall);
        }
        if (isVisible[FOLD]) {
            add(buttonFold);
        }
        if (isVisible[CHECK_BOXES]) {
            add(firstCardToReplace);
            add(secondCardToReplace);
            add(thirdCardToReplace);
            add(fourthCardToReplace);
            add(fifthCardToReplace);
        }
        if (isVisible[DONE]) {
            add(buttonDone);
        }
        revalidate();
    }

    @Override
    public void setCheckBoxesIsVisible(boolean isVisible) {
        this.isVisible[CHECK_BOXES] = isVisible;
        addActions();
    }

    @Override
    public JCheckBox getFirstCheckBox() {
        return firstCardToReplace;
    }

    @Override
    public JCheckBox getSecondCheckBox() {
        return secondCardToReplace;
    }

    @Override
    public JCheckBox getThirdCheckBox() {
        return thirdCardToReplace;
    }

    @Override
    public JCheckBox getFourthCheckBox() {
        return fourthCardToReplace;
    }

    @Override
    public JCheckBox getFifthCheckBox() {
        return fifthCardToReplace;
    }

    @Override
    public JButton getDoneButton() {
        return buttonDone;
    }

    @Override
    public JButton getCommitBetButton() {
        return commitBetButton;
    }

    @Override
    public JSlider getBetSlider() {
        return sliderBet;
    }

    @Override
    public JLabel getLabelBet() {
        return labelBet;
    }

    @Override
    public JButton getBetButton() {
        return buttonBet;
    }

    @Override
    public JButton getCheckButton() {
        return buttonCheck;
    }

    @Override
    public JButton getCallButton() {
        return buttonCall;
    }

    @Override
    public JButton getFoldButton() {
        return buttonFold;
    }

    @Override
    public JPanel getView() {
        return this;
    }

    @Override
    public void setDoneButtonIsVisible(boolean isVisible) {
        this.isVisible[DONE] = isVisible;
        addActions();
    }

    @Override
    public void setBetSliderIsVisible(boolean isVisible) {
        this.isVisible[SLIDER] = isVisible;
        addActions();
    }

    @Override
    public void setCallButtonIsVisible(boolean isVisible) {
        this.isVisible[CALL] = isVisible;
        addActions();
    }

    @Override
    public void setCheckButtonIsVisible(boolean isVisible) {
        this.isVisible[CHECK] = isVisible;
        addActions();
    }

    @Override
    public void setBetButtonIsVisible(boolean isVisible) {
        this.isVisible[BET] = isVisible;
        addActions();
    }

    @Override
    public void setFoldButtonIsVisible(boolean isVisible) {
        this.isVisible[FOLD] = isVisible;
        addActions();
    }
}
