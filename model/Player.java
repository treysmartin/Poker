package model;

import java.io.Serializable;
import java.io.IOException;
import java.lang.ClassNotFoundException;

public class Player implements Serializable
{
   protected Hand hand;
   protected int totalMoney;
   protected int currentBet;
   protected boolean inHand;
   protected String playerName;
   protected int playerId;

   public Player(int startingMoney, String name, int id)
   {
	   setTotalMoney(startingMoney);
	   playerName = name;
       playerId = id;
	   hand = new Hand();
	   currentBet = 0;
	   inHand = false;
   }

   public int getPlayerId() {
       return playerId;
   }

   public Hand getHand()
   {
       return hand;
   }

   public int getCurrentBet()
   {
	   return currentBet;
   }

   public String getName()
   {
	   return playerName;
   }

   public boolean inHand()
   {
	   return inHand;
   }

   public void bet(int betAmount)
   {
	   subtractMoney(betAmount);
	   setCurrentBet(currentBet+betAmount);
   }

   public int call(int totalBet)
   {
       int difference = totalBet - currentBet;
       if (difference > totalMoney) difference = totalMoney;
	   bet(difference);
	   return difference;
   }

   public void check()
   {
   }

   public void fold()
   {
	   setInHand(false);
   }

   public int getTotalMoney()
   {
	   return totalMoney;
   }

   public void setTotalMoney(int newTotal)
   {
	   totalMoney = newTotal;
   }

   public void addMoney(int moneyAmount)
   {
	   setTotalMoney(totalMoney+moneyAmount);
   }

   public void subtractMoney(int moneyAmount)
   {
	   setTotalMoney(totalMoney-moneyAmount);
   }

   public void replaceCardsInHand(Card[] newCards)
   {
	   hand.replaceCards(newCards);
   }

   public void setCurrentBet(int newCurrentBet)
   {
	   currentBet = newCurrentBet;
   }

   public void setInHand(boolean state)
   {
	   inHand = state;
   }
}
