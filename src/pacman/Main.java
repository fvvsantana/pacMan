package pacman;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
    Controller controller;

    @Override
    public void init() throws Exception{
        try {
            controller = new Controller();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            controller.run(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
