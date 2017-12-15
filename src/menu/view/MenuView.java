package menu.view;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.view.itens.MenuContinueView;
import menu.view.itens.MenuItemView;
import menu.view.itens.MenuMapView;
import javafx.scene.paint.Color;


public class MenuView {
    public static final int HEADER_HEIGHT = 45;
    public static final int BOTTOM_HEIGHT = 30;
    
    public static final double SCREEN_WIDTH = 600;
    public static final double SCREEN_HEIGHT = 660 + BOTTOM_HEIGHT + HEADER_HEIGHT;
    
    
    private Stage stage;
    private Pane root;
    private Scene scene;
    private final ImageView titleImage;
    private final ImageView backgroundImage;
    private final ImageView rightArrow;
    private final ImageView leftArrow;
    private final ImageView frame;
    private Text text;

    
    private ArrayList<MenuItemView> itemsView;
    
    public MenuView(Stage stage){
      this.stage = stage;
      root = new Pane();
      root.setStyle("-fx-background-color: black");
      scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
      stage.setTitle("Pacman");
      stage.setScene(scene);
      
      text = new Text();
      text.setFont(new Font("Arial", 20));
      text.setFill(Color.BLUE);
      
      itemsView = new ArrayList<>();
      
      backgroundImage = new ImageView("images/background.png");
      backgroundImage.setFitHeight(SCREEN_HEIGHT);
      backgroundImage.setFitWidth(SCREEN_WIDTH);
      
      titleImage = new ImageView("images/title.png");
      titleImage.setFitHeight(130);
      titleImage.setFitWidth(540);
      
      rightArrow = new ImageView("images/right.png");
      rightArrow.setFitHeight(70);
      rightArrow.setFitWidth(70);
      
      
      leftArrow = new ImageView("images/left.png");
      leftArrow.setFitHeight(70);
      leftArrow.setFitWidth(70);
      
      frame = new ImageView("images/frame.png");
      frame.setFitHeight(350);
      frame.setFitWidth(300);
      
    }
    
    public Scene getScene(){
        return scene;
    }
    
    public void show(){
        stage.show();
    }
    
    public void addMenuMap(int stage){
        itemsView.add(new MenuMapView(stage));
    }
    
    public void addMenuContinue(){
        itemsView.add(new MenuContinueView());
    }
    
    public void printMenu(int option){
        root.getChildren().clear();
        root.getChildren().add(backgroundImage);
        root.getChildren().add(titleImage);
        root.getChildren().get(1).setLayoutX(30);
        root.getChildren().get(1).setLayoutY(50);
        root.getChildren().add(frame);
        root.getChildren().get(2).setLayoutX(154);
        root.getChildren().get(2).setLayoutY(275);
        root.getChildren().add(itemsView.get(option).getImg());
        root.getChildren().get(3).setLayoutX(180);
        root.getChildren().get(3).setLayoutY(300);
        setText(itemsView.get(option));
        root.getChildren().add(text);
        root.getChildren().get(4).setLayoutX(270);
        root.getChildren().get(4).setLayoutY(600);
        printArrow(option);


        
    }
    
    public void setText(MenuItemView item){
        if (item instanceof MenuContinueView){
            text.setText("Continue");
            return;
        }
        text.setText("Stage " + Integer.toString(item.getStage()));
        
    }
    
    public void printArrow(int option){
        if (option != 0){
            root.getChildren().add(leftArrow);
            root.getChildren().get(root.getChildren().size() - 1).setLayoutX(20);
            root.getChildren().get(root.getChildren().size() - 1).setLayoutY(370);
        }
        if (option != itemsView.size() - 1){
            root.getChildren().add(rightArrow);
            root.getChildren().get(root.getChildren().size() - 1).setLayoutX(520);
            root.getChildren().get(root.getChildren().size() - 1).setLayoutY(370);
        }
    
    }
    
    public void updateStage(){
        stage.setScene(scene);
    }
    
    public void clearMenu(){
        itemsView.clear();
    }
}
