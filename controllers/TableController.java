package controllers;

import model.*;
import views.HandView;
import views.TableView;
import views.UserActionsView;
import views.Window;
import views.screens.TableScreen;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.util.ArrayList;

public class TableController {
    private boolean isVisible = true;
    private Window window;
    private Table table;
    private Deck deck;
    private ArrayList<Opponent> opponents;
    private Player user;
    private int anteAmount;
    private boolean betMade;
    private int round;
    private boolean isBeginning;

    public TableController(Window window, ArrayList<Opponent> opponents, Player user, int anteAmount) {
        this.window = window;
        table = new Table(anteAmount);
        deck = table.getDeck();
        this.opponents = opponents;
        this.user = user;
        this.anteAmount = anteAmount;
        betMade = false;
        round = 1;
        startGame(true);
        isBeginning = true;

        TableView tableView = new TableScreen(user, opponents, round, false, table.getPot());
        window.setContent(tableView.getView());

        addActionListenerBettingRound(tableView);
        addActionListenerReplaceRound(tableView);
    }

    public TableController(Window window, int numOpponents, int startingMoney, int anteAmount) {
        table = new Table(anteAmount);
        this.anteAmount = anteAmount;
        this.window = window;
        deck = table.getDeck();
        betMade = false;
        opponents = new ArrayList<Opponent>();
        round = 1;
        isBeginning = true;

        user = new Player(startingMoney, "Your", 0);
        opponents = new ArrayList<Opponent>();
        for (int i = 0; i < numOpponents; i++) {
            opponents.add(new Opponent(startingMoney, "Opponent " + (i + 1), i + 1));
        }
        startGame(false);

        TableView tableView = new TableScreen(user, opponents, round, false, table.getPot());
        window.setContent(tableView.getView());

        addActionListenerBettingRound(tableView);
        addActionListenerReplaceRound(tableView);
    }

    private void startGame(boolean loaded) {
        table.setMaxBet(anteAmount);
        deck.resetDeck();
        deck.shuffle();
        deck.dealNewHand(user);
        if (loaded != true) {
            user.bet(anteAmount);
        } else {
            FileIOManager.saveGame(opponents, user, anteAmount, "Team2.poker");
        }
        table.addToPot(anteAmount);
        user.setInHand(true);

        for (Opponent opponent : opponents) {
            deck.dealNewHand(opponent);
            if (loaded != true) {
                opponent.bet(anteAmount);
            }
            table.addToPot(anteAmount);
            opponent.setInHand(true);
        }
    }

    private void addActionListenerBettingRound(TableView tableView) {
        UserActionsView userActions = tableView.getUserActionsView();
        userActions.getCheckButton().addActionListener(event -> {
            if (round == 1) {
                firstBettingRoundCheck(tableView);
            } else {
                secondBettingRoundCheck(tableView);
            }
        });

        userActions.getFoldButton().addActionListener(event -> {
            if (round == 1) {
                firstBettingRoundFold(tableView);
            } else {
                secondBettingRoundFold(tableView);
            }
        });

        userActions.getCallButton().addActionListener(event -> {
            if (round == 1) {
                firstBettingRoundCall(tableView);
            } else {
                secondBettingRoundCall(tableView);
            }
        });

        tableView.getUserActionsView().getBetSlider().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                tableView.getUserActionsView().getLabelBet().setText(String.valueOf(userActions.getBetSlider().getValue()));
            }
        });

        userActions.getBetButton().addActionListener(event -> {
            userActions.setBetSliderIsVisible(true);
            showBettingRoundButtons(tableView, false);
            userActions.getCommitBetButton().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (round == 1) {
                        firstBettingRoundBet(tableView, tableView.getUserActionsView().getBetSlider().getValue());
                    } else {
                        secondBettingRoundBet(tableView, tableView.getUserActionsView().getBetSlider().getValue());
                    }
                }
            });
        });
    }

    private void enableBettingRoundButtons(TableView tableView, boolean enabled) {
        tableView.getUserActionsView().getCheckButton().setEnabled(enabled);
        tableView.getUserActionsView().getCallButton().setEnabled(enabled);
        tableView.getUserActionsView().getFoldButton().setEnabled(enabled);
        tableView.getUserActionsView().getBetButton().setEnabled(enabled);
    }

    private void showBettingRoundButtons(TableView tableView, boolean revealed) {
        tableView.getUserActionsView().setCheckButtonIsVisible(revealed);
        tableView.getUserActionsView().setCallButtonIsVisible(revealed);
        tableView.getUserActionsView().setBetButtonIsVisible(revealed);
        tableView.getUserActionsView().setFoldButtonIsVisible(revealed);
    }

    private void showReplaceRoundComponents(TableView tableView, boolean revealed) {
        tableView.getUserActionsView().setCheckBoxesIsVisible(revealed);
        tableView.getUserActionsView().setDoneButtonIsVisible(revealed);
    }


    private void errorDialog(String message, String errorType) {
        JOptionPane.showMessageDialog(window.getFrame(), message, errorType, JOptionPane.ERROR_MESSAGE);
    }

    private void opponentsBettingRound(TableView tableView) {
        for (int i = 0; i < opponents.size(); i++) {
            Opponent opponent = opponents.get(i);
            int betAmount = opponent.playBettingRound(table);
            if (opponent.getCurrentBet() > table.getMaxBet()) {
                table.setMaxBet(opponent.getCurrentBet());
            }
            table.addToPot(betAmount);

            HandView[] handViews = tableView.getHands();
            tableView.getPotLabel().setText("Pot: " + table.getPot());
            handViews[i + 1].getBetLabel().setText("Bet: " + opponent.getCurrentBet());
            handViews[i + 1].getMoneyLabel().setText(opponent.getName()+ "'s money: " + opponent.getTotalMoney());
        }
    }

    private void opponentsReplaceRound() {
        for (int i = 0; i < opponents.size(); i++) {
            opponents.get(i).playReplaceRound(deck);
        }
    }

    private void firstBettingRoundCheck(TableView tableView) {
        enableBettingRoundButtons(tableView, false);
        opponentsBettingRound(tableView);
        if (shouldPlayBetRoundAgain()) {
            enableBettingRoundButtons(tableView, true);
            tableView.getUserActionsView().setCheckButtonIsVisible(false);
            tableView.getUserActionsView().setCallButtonIsVisible(true);
        } else {
            tableView.getRoundLabel().setText("Replace round");
            showReplaceRoundComponents(tableView, true);
            showBettingRoundButtons(tableView, false);
            round++;
        }
    }

    private void secondBettingRoundCheck(TableView tableView) {
        enableBettingRoundButtons(tableView, false);
        opponentsBettingRound(tableView);
        if (shouldPlayBetRoundAgain()) {
            enableBettingRoundButtons(tableView, true);
            tableView.getUserActionsView().setCheckButtonIsVisible(false);
            tableView.getUserActionsView().setCallButtonIsVisible(true);
        } else {
            showDown(tableView);
        }
    }

    private void firstBettingRoundFold(TableView tableView) {
        int choice = JOptionPane.showConfirmDialog(window.getFrame(), "Are you sure you want to fold?", "Fold options", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            user.fold();
            enableBettingRoundButtons(tableView, false);
            do {
                opponentsBettingRound(tableView);
            } while(shouldPlayBetRoundAgain());
            tableView.getRoundLabel().setText("Replace round");
            opponentsReplaceRound();
            round++;
            // No need for this after debugging
            /*TableView newTableView = new TableScreen(user, opponents, 2, table.getPot());
            window.setContent(newTableView.getView());*/
            enableBettingRoundButtons(tableView, false);
            do {
                opponentsBettingRound(tableView);
            } while(shouldPlayBetRoundAgain());
            showDown(tableView);
        }
    }

    private void secondBettingRoundFold(TableView tableView) {
        int choice = JOptionPane.showConfirmDialog(window.getFrame(), "Are you sure you want to fold?", "Fold options", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            user.fold();
            do {
                opponentsBettingRound(tableView);
            } while(shouldPlayBetRoundAgain());
            showDown(tableView);
        }
    }

    private void firstBettingRoundCall(TableView tableView) {
        enableBettingRoundButtons(tableView, false);
        table.addToPot(user.call(table.getMaxBet()));
        HandView userHand = tableView.getHands()[0];
        userHand.getBetLabel().setText("Bet: " + user.getCurrentBet());
        userHand.getMoneyLabel().setText("Your money: " + user.getTotalMoney());
        opponentsBettingRound(tableView);
        if (shouldPlayBetRoundAgain()) {
            enableBettingRoundButtons(tableView, true);
        } else {
            tableView.getRoundLabel().setText("Replace round");
            showReplaceRoundComponents(tableView, true);
            showBettingRoundButtons(tableView, false);
            round++;
        }
    }

    private void secondBettingRoundCall(TableView tableView) {
        enableBettingRoundButtons(tableView, false);
        table.addToPot(user.call(table.getMaxBet()));
        HandView userHand = tableView.getHands()[0];
        userHand.getBetLabel().setText("Bet: " + user.getCurrentBet());
        userHand.getMoneyLabel().setText("Your money: " + user.getTotalMoney());
        opponentsBettingRound(tableView);
        if (shouldPlayBetRoundAgain()) {
            enableBettingRoundButtons(tableView, true);
        } else {
            showDown(tableView);
        }
    }

    private void firstBettingRoundBet(TableView tableView, int betAmount) {
        if (betAmount <= user.getTotalMoney()) {
            user.bet(betAmount);
            if (user.getCurrentBet() > table.getMaxBet()) {
                table.setMaxBet(user.getCurrentBet());
            }
            enableBettingRoundButtons(tableView, false);
            tableView.getUserActionsView().setBetSliderIsVisible(false);
            table.addToPot(betAmount);
            HandView userHand = tableView.getHands()[0];
            userHand.getBetLabel().setText("Bet: " + user.getCurrentBet());
            userHand.getMoneyLabel().setText("Your money: " + user.getTotalMoney());
            opponentsBettingRound(tableView);
            if (shouldPlayBetRoundAgain()) {
                enableBettingRoundButtons(tableView, true);
                tableView.getUserActionsView().setCheckButtonIsVisible(false);
                tableView.getUserActionsView().setCallButtonIsVisible(true);
            } else {
                tableView.getRoundLabel().setText("Replace round");
                showReplaceRoundComponents(tableView, true);
                showBettingRoundButtons(tableView, false);
                round++;
            }
        } else {
            String message = "You can not bet pass " + user.getTotalMoney();
            errorDialog(message, "Bet error");
        }
    }

    private void secondBettingRoundBet(TableView tableView, int betAmount) {
        if (betAmount <= user.getTotalMoney()) {
            user.bet(betAmount);
            if (user.getCurrentBet() > table.getMaxBet()) {
                table.setMaxBet(user.getCurrentBet());
            }
            enableBettingRoundButtons(tableView, false);
            tableView.getUserActionsView().setBetSliderIsVisible(false);
            table.addToPot(betAmount);
            HandView userHand = tableView.getHands()[0];
            userHand.getBetLabel().setText("Bet: " + user.getCurrentBet());
            userHand.getMoneyLabel().setText("Your money: " + user.getTotalMoney());
            opponentsBettingRound(tableView);
            if (shouldPlayBetRoundAgain()) {
                enableBettingRoundButtons(tableView, true);
                tableView.getUserActionsView().setCheckButtonIsVisible(false);
                tableView.getUserActionsView().setCallButtonIsVisible(true);
            } else {
                showDown(tableView);
            }

        } else {
            String message = "You can not bet pass " + user.getTotalMoney();
            errorDialog(message, "Bet error");
        }
    }

    private void addActionListenerReplaceRound(TableView tableView) {
        tableView.getUserActionsView().getDoneButton().addActionListener(event -> {
            userReplaceCards(tableView);
            opponentsReplaceRound();

            TableView newTableView = new TableScreen(user, opponents, 2, false, table.getPot());
            window.setContent(newTableView.getView());
            enableBettingRoundButtons(newTableView, true);
            addActionListenerBettingRound(newTableView);
        });
    }

    private void userReplaceCards(TableView tableView) {
        if (user.inHand()) {
            UserActionsView userActions = tableView.getUserActionsView();
            showReplaceRoundComponents(tableView, false);
            showBettingRoundButtons(tableView, true);

            Card[] newCards = new Card[5];
            for (int i = 0; i < 5; i++) {
                newCards[i] = null;
            }
            ArrayList<Integer> cardsToReplace = new ArrayList<Integer>();
            int card = 0;
            if (userActions.getFirstCheckBox().isSelected()) {
                card = Integer.parseInt(userActions.getFirstCheckBox().getText());
                cardsToReplace.add(card);
            }

            if (userActions.getSecondCheckBox().isSelected()) {
                card = Integer.parseInt(userActions.getSecondCheckBox().getText());
                cardsToReplace.add(card);
            }

            if (userActions.getThirdCheckBox().isSelected()) {
                card = Integer.parseInt(userActions.getThirdCheckBox().getText());
                cardsToReplace.add(card);
            }

            if (userActions.getFourthCheckBox().isSelected()) {
                card = Integer.parseInt(userActions.getFourthCheckBox().getText());
                cardsToReplace.add(card);
            }
            if (tableView.getUserActionsView().getFifthCheckBox().isSelected()) {
                card = Integer.parseInt(userActions.getFifthCheckBox().getText());
                cardsToReplace.add(card);
            }

            for (int i = 0; i < cardsToReplace.size(); i++) {
                newCards[cardsToReplace.get(i)] = deck.dealOne();
            }
            user.replaceCardsInHand(newCards);
        }
    }

    private void showDown(TableView tableView) {
        ArrayList<Player> players = new ArrayList<Player>();
        HandView[] handViews = tableView.getHands();
        if (user.inHand()) {
            players.add(user);
        }
        players.addAll(opponents);

        Rules rules = new Rules();
        ArrayList<Player> winners = rules.compareHands(players);

        for (int i = 0; i < winners.size(); i++) {
            Player winner = winners.get(i);
            winner.addMoney(table.getPot() / winners.size());
            String moneyUpdate = winner.getName() + "'s money: " + winner.getTotalMoney();
            handViews[winner.getPlayerId()].getMoneyLabel().setText(moneyUpdate);
        }

        table.resetPot();
        TableView revealedView = new TableScreen(user, opponents, round, true, table.getPot());
        enableBettingRoundButtons(revealedView, false);
        window.setContent(revealedView.getView());

        round = 1;
        deck.resetDeck();
        deck.shuffle();
        deck.dealNewHand(user);
        user.bet(anteAmount);
        user.setCurrentBet(anteAmount);
        table.addToPot(anteAmount);
        user.setInHand(true);

        table.setMaxBet(anteAmount);

        ArrayList<Opponent> tempOpponent = new ArrayList<>();
        for (Opponent opponent : opponents) {
            if (opponent.getTotalMoney() >= anteAmount) {
                deck.dealNewHand(opponent);
                opponent.bet(anteAmount);
                opponent.setCurrentBet(anteAmount);
                table.addToPot(anteAmount);
                opponent.setInHand(true);
                tempOpponent.add(opponent);
            }
        }
        opponents = tempOpponent;
        if (user.getTotalMoney() >= anteAmount && !tempOpponent.isEmpty()) FileIOManager.saveGame(opponents, user, anteAmount, "Team2.poker");

        if (playAgain(winners)) {
            if (user.getTotalMoney() < anteAmount) {
                new OptionsController(window);
            } else {
                if (tempOpponent.isEmpty()) {
                    new OptionsController(window);
                } else {
                    TableView newTableView = new TableScreen(user, opponents, round, false, table.getPot());
                    window.setContent(newTableView.getView());
                    addActionListenerBettingRound(newTableView);
                    addActionListenerReplaceRound(newTableView);
                }
            }
        }
    }

    private boolean playAgain(ArrayList<Player> winners) {
        boolean sameWinner = false;
        for (int i = 0; i < winners.size() - 1; i++) {
            if (winners.get(i).getName().equals(winners.get(i + 1).getName())) {
                sameWinner = true;
            }
        }
        String message = winners.get(0).getName();
        if (sameWinner) {
            if (winners.contains(user)) {
                message += " win.";
            } else {
                message += " wins.";
            }
        } else {
            for (int i = 1; i < winners.size(); i++) {
                message += ", " + winners.get(i).getName();
            }
            if (winners.size() < 2) {
                if (winners.contains(user)) {
                    message += " win.";
                } else {
                    message += " wins.";
                }
            } else {
                message += " win.";
            }
        }
        if (user.getTotalMoney() < anteAmount) {
            message += " You don't have enough money to continue. Start new game?";
        } else {
            message += " Do you want to play again?";
        }
        int choice = JOptionPane.showConfirmDialog(window.getFrame(), message, "Play again options", JOptionPane.YES_NO_OPTION);
        return choice == JOptionPane.YES_OPTION;
    }

    private boolean shouldPlayBetRoundAgain() {
        if (user.inHand() && user.getTotalMoney() != 0 && user.getCurrentBet() != table.getMaxBet()) return true;
        for (Opponent opponent: opponents) {
            if (opponent.inHand() && opponent.getTotalMoney() != 0 && opponent.getCurrentBet() != table.getMaxBet()) return true;
        }
        return false;
    }
}
