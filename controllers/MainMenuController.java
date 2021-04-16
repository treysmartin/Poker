package controllers;

import model.*;
import views.MainMenuView;
import views.Window;
import views.screens.StartScreen;

public class MainMenuController {
    public MainMenuController(Window window) {
        MainMenuView mainMenu = new StartScreen();
        window.setContent(mainMenu.getView());
        mainMenu.getNewGameButton().addActionListener(event -> {
            new OptionsController(window);
        });

        mainMenu.getLoadGameButton().addActionListener(event -> {
            SaveGameModel savedGame = FileIOManager.loadGame("Team2.poker");
            new TableController(window, savedGame.getOpponentList(), savedGame.getUser(), savedGame.getAnteAmount());
        });
    }
}
