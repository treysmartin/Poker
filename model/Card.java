package model;

import java.io.Serializable;
import java.util.HashMap;

public class Card implements Serializable
{
   public enum Suit {CLUB, DIAMOND, HEART, SPADE}
   public enum Face  {
       ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13), ACE(14);

       private final int value;
       Face(int v)
       {
           this.value = v;
       }

       private static HashMap<Integer, Face> map = new HashMap<Integer, Face>();
       static
       {
          for (Face face: Face.values())
          {
              map.put(face.value, face);
          }
       }
       public static Face valueOf(int v)
       {
           return map.get(v);
       }
       public int getValue()
       {
           return this.value;
       }
   }

   protected Face face;
   protected Suit suit;

   public Card(int v, Suit s)
   {
       this.face = Face.valueOf(v);
       this.suit = s;
   }

   public Suit getSuit()
   {
       return this.suit;
   }
   public Face getFace()
   {
       return this.face;
   }
   public int getValue()
   {
	   return this.face.getValue();
   }

   @Override
   public String toString()
   {
       String result = new String();
       switch (face)
       {
           case ACE:
               result+="Ace";
               break;
           case KING:
               result+="King";
               break;
           case QUEEN:
               result+="Queen";
               break;
           case JACK:
               result+="Jack";
               break;
           default:
               result+=String.valueOf(face);

       }
       result+=" - "+suit;
       return result;
   }
}
