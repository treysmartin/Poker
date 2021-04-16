package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Hand implements Serializable
{
   private Card[] cards = new Card[5];

   public Hand()
   {
	   for(int i = 0; i < 5; i++) {
           cards[i] = new Card(2, Card.Suit.SPADE);
       }
   }

    public Card[] getCards() {
        return cards;
    }

/*   @Override
   public Iterator<Card> iterator()
   {
       return cards.iterator();
   }*/

   private void replace(Card newCard, int n)
   {
	   cards[n] = newCard;
   }

   public void replaceCards(Card[] newCards)
   {
	   for(int i = 0; i< 5; i++)
	   {
		   if(newCards[i]!=null)
		   {
			   replace(newCards[i], i);
		   }
	   }
   }

   public void reveal()
   {
       for (int i = 0; i < 5; i++)
       {
          System.out.println(i + ". " + cards[i]);
       }
   }
}
