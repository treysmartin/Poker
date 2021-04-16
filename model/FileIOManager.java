package model;

import java.util.ArrayList;
import java.io.*;
import java.lang.ClassNotFoundException;

public class FileIOManager
{
	public static boolean saveGame(ArrayList<Opponent> opponentList, Player user, int anteAmount, String path)
	{
		try{
			SaveGameModel save = new SaveGameModel(opponentList, user, anteAmount);
			FileOutputStream fileStream = new FileOutputStream(path, false);
			ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
			objectStream.writeObject(save);
			objectStream.close();
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public static SaveGameModel loadGame(String path)
	{
		try{
			FileInputStream fileStream = new FileInputStream(path);
			ObjectInputStream objectStream = new ObjectInputStream(fileStream);
			SaveGameModel save;
			save = (SaveGameModel) objectStream.readObject();
			objectStream.close();
			return save;
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
