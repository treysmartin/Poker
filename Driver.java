import controllers.MainMenuController;
import views.screens.StartScreen;
import views.Window;

public class Driver {
    public static void main(String[] args) {
        Window window = new Window("Poker", 1200, 800);
        new MainMenuController(window);
    }
}
