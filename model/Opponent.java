package model;

public class Opponent extends Player {
    private boolean betMade;

    public Opponent(int startingMoney, String name, int id) {
        super(startingMoney, name, id);
    }

    public int playBettingRound(Table table) {
        if (table.getMaxBet() > this.currentBet) {
            int money = this.call(table.getMaxBet());
            betMade = true;
            return money;
        } else if (table.getMaxBet() == table.getAnteAmount()) {
            if (table.getAnteAmount() > this.totalMoney) {
                int money = totalMoney;
                this.bet(totalMoney);
                betMade = true;
                return money;
            } else {
                this.bet(table.getAnteAmount());
                betMade = true;
                table.setMaxBet(this.getCurrentBet());
                return table.getAnteAmount();
            }
        } else if (table.getMaxBet() == this.currentBet) {
            return 0;
        } else {
            fold();
            return 0;
        }
    }

    public boolean isBetMade() {
        return betMade;
    }

    public void playReplaceRound(Deck deck) {
        deck.dealNewHand(this);
    }

}
