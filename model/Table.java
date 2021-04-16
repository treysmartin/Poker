package model;

import java.util.ArrayList;
import java.io.Serializable;

public class Table
{
	private int pot;
	private Deck deck;
	private int anteAmount;
	private int maxBet;

	public Table(int ante)
	{
		anteAmount = ante;
		pot = 0;
		maxBet = 0;
		deck = new Deck();
	}

	public int getMaxBet()
	{
		return maxBet;
	}

	public int getAnteAmount()
	{
		return anteAmount;
	}

	public void addToPot(int betAmount)
	{
		pot += betAmount;
	}

	public void resetPot()
	{
		pot = 0;
	}

	public int getPot()
	{
		return pot;
	}

	public void setMaxBet(int newMaxBet)
	{
		maxBet = newMaxBet;
	}

	public void shuffleDeck()
	{
		deck.resetDeck();
		deck.shuffle();
	}

	public Deck getDeck()
	{
		return deck;
	}
}
