package model;

public class HandEvaluater
{
	public static int[] getHandValue(Hand hand)
	{
		Card[] cards = cardSort(hand.getCards());

		int similarCards = determineSimilarCards(cards);
		if(similarCards == 4)
		{
			if(cards[2].getValue() == cards[0].getValue())
			{
				return new int[]{7,cards[0].getValue()};
			}
			else
			{
				return new int[]{7,cards[4].getValue()};
			}
		}else if(similarCards == 6)
		{
			if(cards[0].getValue() == cards[1].getValue())
			{
				return new int[]{8,cards[0].getValue()};
			}
			else
			{
				return new int[]{8,cards[1].getValue()};
			}
		}else if(similarCards == 1)
		{
			if(cards[0].getValue()==cards[1].getValue())
			{
				return new int[]{2,cards[0].getValue(),cards[2].getValue(),cards[3].getValue(),cards[4].getValue()};
			}else if(cards[1].getValue()==cards[2].getValue())
			{
				return new int[]{2,cards[1].getValue(),cards[0].getValue(),cards[3].getValue(),cards[4].getValue()};
			}else if(cards[2].getValue()==cards[3].getValue())
			{
				return new int[]{2,cards[2].getValue(),cards[0].getValue(),cards[1].getValue(),cards[4].getValue()};
			}else
			{
				return new int[]{2,cards[3].getValue(),cards[0].getValue(),cards[1].getValue(),cards[2].getValue()};
			}
		}else if(similarCards == 3)
		{
			if(cards[0].getValue()==cards[2].getValue())
			{
				return new int[]{4,cards[0].getValue()};
			}else if(cards[1].getValue()==cards[3].getValue())
			{
				return new int[]{4,cards[1].getValue()};
			}else
			{
				return new int[]{4,cards[2].getValue()};
			}
		}else if(similarCards == 2)
		{
			if(cards[2].getValue()==cards[3].getValue())
			{
				return new int[]{3,cards[0].getValue(),cards[2].getValue(),cards[4].getValue()};
			}else if(cards[1].getValue()==cards[2].getValue())
			{
				return new int[]{3,cards[1].getValue(),cards[3].getValue(),cards[0].getValue()};
			}else
			{
				return new int[]{3,cards[0].getValue(),cards[3].getValue(),cards[2].getValue()};
			}
		}else
		{
			if(isStraight(cards))
			{
				if(isFlush(cards))
				{
					if(cards[4].getValue() == 10)
					{
						return new int[]{10};
					}else{
						return new int[]{9,cards[0].getValue()};
					}
				}else{
					return new int[]{5,cards[0].getValue()};
				}
			}else if(isAceLowStraight(cards))
			{
				if(isFlush(cards))
				{
					return new int[]{9,cards[1].getValue()};
				}else{
					return new int[]{5,cards[1].getValue()};
				}
			}else if(isFlush(cards))
			{
				return new int[]{6,cards[0].getValue(),cards[1].getValue(),cards[2].getValue(),cards[3].getValue(),cards[4].getValue()};
			}else{
				return new int[]{1,cards[0].getValue(),cards[1].getValue(),cards[2].getValue(),cards[3].getValue(),cards[4].getValue()};
			}
		}
	}

	private static Card[] cardSort(Card[] nonSortedCards)
	{
		   Card[] sortedCards = new Card[5];

		   for(int i = 0; i < 5; i++)
		   {
			   sortedCards[i] = nonSortedCards[i];
		   }

		   for(int i = 3; i > -1; i--)
		   {
			   for(int j = i; j < 4; j++)
			   {
				   if(sortedCards[j].getValue() < sortedCards[j+1].getValue())
				   {
					   Card temp = sortedCards[j];
					   sortedCards[j] = sortedCards[j+1];
					   sortedCards[j+1] = temp;
				   }else{
					   break;
				   }
			   }
		   }
	      	   return sortedCards;
	}

	//A(3)A(2)A(1)A(0)K = 6
	//A(2)A(1)A(0)K(1)K(0) = 4
	//A(2)A(1)AKQ = 3
	//A(1)AK(1)KQ = 2
	//A(1)AKQJ = 1

	private static int determineSimilarCards(Card[] cards)
	   {
		   int similarCards = 0;
		   for(int i = 0; i < 4; i++)
		   {
			   for(int j = i+1; j < 5; j++)
			   {
				   if(cards[i].getValue() == cards[j].getValue())
				   {
					   similarCards++;
				   }
			   }
		   }
		   return similarCards;
	   }

	public Hand handSort(Hand hand)
	{
		Card[] sortedCards = hand.getCards();
		for(int i = 3; i > -1; i--)
		   {
			   for(int j = i; j < 4; j++)
			   {
				   if(sortedCards[j].getValue() < sortedCards[j+1].getValue())
				   {
					   Card temp = sortedCards[j];
					   sortedCards[j] = sortedCards[j+1];
					   sortedCards[j+1] = temp;
				   }else{
					   break;
				   }
			   }
		   }
		Hand newHand = new Hand();
		newHand.replaceCards(sortedCards);
		return newHand;
	}


	private static boolean isStraight(Card[] cards)
	{
		for(int i = 0; i < 4; i++)
		{
			if(cards[i].getValue() != cards[i+1].getValue()+1)
			{
				return false;
			}
		}
		return true;
	}

	private static boolean isFlush(Card[] cards)
	{
		for(int i = 0; i < 4; i++)
		{
			if(cards[i].getSuit() != cards[i+1].getSuit())
			{
				return false;
			}
		}
		return true;
	}

	public static boolean isAceLowStraight(Card[] cards)
	{
		if((cards[0].getValue() != 14)||(cards[1].getValue() !=5))
		{
			return false;
		}
		for(int i = 1; i < 4; i++)
		{
			if(cards[i].getValue() != cards[i+1].getValue()+1)
			{
				return false;
			}
		}
		return true;
	}
}
