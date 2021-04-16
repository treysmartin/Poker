package views.components;

import model.Card;
import model.Hand;
import model.Player;
import views.CardView;
import views.HandView;
import views.components.custom.PanelFactory;
import views.components.custom.PanelType;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class HandPanel extends JPanel implements HandView {
    private JLabel labelMoney;
    private JLabel labelBet;
    private CardView[] cardPanels;

    public HandPanel(Player player, boolean isRevealed, boolean isHorizontal) {
        this(player.getHand(), player.getName(), player.getTotalMoney(), player.getCurrentBet(), isRevealed, isHorizontal);
    }

    public HandPanel(Hand hand, String playerName, int money, int bet, boolean isRevealed, boolean isHorizontal) {
        setOpaque(false);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel cardsPanel = PanelFactory.createPanel(isHorizontal ? PanelType.HORIZONTAL : PanelType.VERTICAL);
        cardPanels = new CardView[5];
        int index = 0;
        cardsPanel.setOpaque(false);

        for (Card card: hand.getCards()) {
            CardPanel cardPanel = new CardPanel(card, isHorizontal, isRevealed);
            cardsPanel.add(cardPanel);
            cardPanels[index++] = cardPanel;
            if (!isHorizontal) cardsPanel.add(Box.createVerticalStrut(4));
        }

        add(cardsPanel);

        String betLabelText = "Bet: " + bet;

        labelBet = new JLabel(betLabelText);
        labelBet.setForeground(Color.WHITE);
        add(labelBet);

        String labelText = playerName + "'s Money: " + money;
        labelMoney = new JLabel(labelText);
        labelMoney.setForeground(Color.WHITE);
        add(labelMoney);
    }

    @Override
    public JLabel getBetLabel() {
        return labelBet;
    }

    @Override
    public JLabel getMoneyLabel() {
        return labelMoney;
    }

    @Override
    public JPanel getView() {
        return this;
    }

    @Override
    public CardView[] getCardPanels() {
        return cardPanels;
    }

    @Override
    public void isRevealed(boolean isRevealed) {
        Arrays.stream(cardPanels).forEach(card -> card.isRevealed(isRevealed));
    }
}
