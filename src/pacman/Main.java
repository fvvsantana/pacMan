package pacman;

import menu.MenuController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    MenuController menuController;

    @Override
    public void init() throws Exception{
        try {
            menuController = new MenuController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            menuController.run(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
