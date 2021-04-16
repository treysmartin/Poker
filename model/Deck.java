package model;

import model.*;
import java.util.ArrayList;
import java.util.Random;

public class Deck
{
   private ArrayList<Card> cards;
   Random randomizer;

   public Deck()
   {
     cards = new ArrayList<Card>();
     for (int i = 2; i <= 14; i++)
     {
         cards.add(new Card(i, Card.Suit.SPADE));
         cards.add(new Card(i, Card.Suit.HEART));
         cards.add(new Card(i, Card.Suit.CLUB));
         cards.add(new Card(i, Card.Suit.DIAMOND));
     }
     randomizer = new Random();
   }

   /**
    * Shuffles the deck in random order
    */
   public void shuffle()
   {
       for (int i = 0; i < cards.size(); i++)
       {
           int swap_with = randomizer.nextInt(cards.size());
           Card a = cards.get(i);
           cards.set(i, cards.get(swap_with));
           cards.set(swap_with, a);
       }
   }

   /**
    * Deals one card to the given player
    * @param p the player who gets one card from the top of the deck
    */
   public Card dealOne()
   {
       Card c = cards.remove(cards.size()-1);
       return c;
   }

   public void dealNewHand(Player p)
   {
	   Card[] newCards = new Card[5];
	   for(int i = 0; i < 5; i++)
	   {
		   newCards[i] = dealOne();
	   }
	   p.replaceCardsInHand(newCards);
   }

   public void resetDeck()
   {
     cards = new ArrayList<Card>();
     for (int i = 2; i <= 14; i++)
     {
         cards.add(new Card(i, Card.Suit.SPADE));
         cards.add(new Card(i, Card.Suit.HEART));
         cards.add(new Card(i, Card.Suit.CLUB));
         cards.add(new Card(i, Card.Suit.DIAMOND));
     }
   }
}
