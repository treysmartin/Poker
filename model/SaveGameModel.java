package model;

import java.util.ArrayList;
import java.io.Serializable;

public class SaveGameModel implements Serializable
{
	private ArrayList<Opponent> opponentList;
	private Player user;
	private int anteAmount;

	public SaveGameModel(ArrayList<Opponent> opponents, Player player, int ante)
	{
		opponentList = opponents;
		user = player;
		anteAmount = ante;
	}

	public ArrayList<Opponent> getOpponentList()
	{
		return opponentList;
	}

	public Player getUser()
	{
		return user;
	}

	public int getAnteAmount()
	{
		return anteAmount;
	}
}
