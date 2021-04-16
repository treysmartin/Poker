package model;
import java.util.ArrayList;

public class Rules
{
   /**
    * Determines the outcome of the game by comparing h1 to h2
    * The result is reported in terms of h1
    * @param h1 the hand being compared against
    * @param h2 the hand being compared with
    * @return WIN if h1 beats h2, LOOSE if h2 beats h1, TIE if h1 and h2 tie
    */
   public static ArrayList<Player> compareHands(ArrayList<Player> playerList)
   {
	   ArrayList<Hand> handList = new ArrayList<Hand>();
	   ArrayList<int[]> handValueList = new ArrayList<int[]>();
	   HandEvaluater evaluater = new HandEvaluater();
	   for(int i = 0; i < playerList.size(); i++)
	   {
		   handList.add(playerList.get(i).getHand());
		   handValueList.add(evaluater.getHandValue(handList.get(i)));
	   }
	   int maxValue = 0;
	   ArrayList<Player> winnersList = new ArrayList<Player>();
	   ArrayList<int[]> winnersHandValueList = new ArrayList<int[]>();
	   for(int i = 0; i < handValueList.size(); i++)
	   {
		   if(handValueList.get(i)[0] > maxValue)
		   {
			   maxValue = handValueList.get(i)[0];
		   }
	   }
	   for(int i = 0; i < handValueList.size(); i++)
	   {
		   if(handValueList.get(i)[0] == maxValue)
		   {
			   winnersList.add(playerList.get(i));
			   winnersHandValueList.add(handValueList.get(i));
		   }
	   }
	   if(winnersList.size() > 1 && winnersHandValueList.get(0).length > 1)
	   {
		   int tiebreaker = 0;
		   ArrayList<Player> newWinnersList;
		   ArrayList<int[]> newWinnersHandValueList;
		   for(int i = 0; i < winnersHandValueList.get(0).length; i++)
		   {
			   newWinnersList = new ArrayList<Player>();
			   newWinnersHandValueList = new ArrayList<int[]>();
			   for(int j = 0; j < winnersHandValueList.size(); j++)
			   {
				   if(winnersHandValueList.get(j)[i] > tiebreaker)
				   {
					   tiebreaker = winnersHandValueList.get(j)[i];
				   }
			   }
			   for(int j = 0; j < winnersHandValueList.size(); j++)
			   {
				   if(winnersHandValueList.get(j)[i] == tiebreaker)
				   {
					   newWinnersHandValueList.add(winnersHandValueList.get(j));
					   newWinnersList.add(winnersList.get(j));
				   }
			   }
			   if(newWinnersList.size() == 1)
			   {
				   return newWinnersList;
			   }
			   tiebreaker = 0;
			   winnersList = newWinnersList;
			   winnersHandValueList = newWinnersHandValueList;
		   }
	   }
	   return winnersList;
   }
}
