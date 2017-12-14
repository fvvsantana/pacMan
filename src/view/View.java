package view;

import view.characters.PacManView;
import view.characters.RedGhostView;
import view.characters.PinkGhostView;
import view.characters.OrangeGhostView;
import view.characters.CyanGhostView;

import view.grid.GridView;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

import utils.Position;
import view.lives.liveView;
import view.fruits.CherryView;
import view.fruits.StrawberryView;


public class View{
    public static final int HEADER_HEIGHT = 45;
    public static final int BOTTOM_HEIGHT = 30;
    
    public static final double SCREEN_WIDTH = 600;
    public static final double SCREEN_HEIGHT = 660 + BOTTOM_HEIGHT + HEADER_HEIGHT;

    // layout variables
    private Stage stage;
    private BorderPane root;
    private Pane centerPane; //the maze
    private Group mapContainer;
    private Scene scene;
    
    private GridView grid;
    private final Position gridPosition;
    private final double gridWidth;
    private final double gridHeight;

    // pacman view
    private PacManView pacManView;
    
    // ghost views
    private RedGhostView redGhostView;
    private OrangeGhostView orangeGhostView;
    private CyanGhostView cyanGhostView;
    private PinkGhostView pinkGhostView;
    
    // fruits views
    private CherryView cherryView;
    private StrawberryView strawberryView;
    
    private Text scoreText;
    private Text stageText;
    private Font font = new Font("Arial", 20);
    private Pane bottomPane;
    private HBox topBox;
    private ArrayList<Node> livesImages;
    private ArrayList<Node> fruitsImages;
    
    
    public View(Stage stage){
        //layout configuration
        this.stage = stage;
        root = new BorderPane();
        root.setStyle("-fx-background-color: black");
        centerPane = new Pane();
        bottomPane = new Pane();
        topBox = new HBox(SCREEN_WIDTH/4);
        root.setCenter(centerPane);
        root.setBottom(bottomPane);
        root.setTop(topBox);
        mapContainer = new Group();
        centerPane.getChildren().add(mapContainer);
        scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setTitle("Pacman");
        stage.setScene(scene);
        
        livesImages = new ArrayList<>();
        fruitsImages = new ArrayList<>();
        scoreText = new Text();
        stageText = new Text();
        scoreText.setFont(font);
        stageText.setFont(font);
        scoreText.setFill(Color.WHITE);
        stageText.setFill(Color.WHITE);
        scoreText.setTextAlignment(TextAlignment.CENTER);
        stageText.setTextAlignment(TextAlignment.CENTER);
        topBox.getChildren().addAll(scoreText, stageText);
        topBox.setAlignment(Pos.CENTER);

        gridPosition = new Position(0, 0);
        gridWidth = SCREEN_WIDTH;
        gridHeight = SCREEN_HEIGHT - HEADER_HEIGHT - BOTTOM_HEIGHT;
        
        // create fruits
        cherryView = new CherryView();
        strawberryView = new StrawberryView();
    }

    //remove all the childrem from the mapContainer
    public void clearMapContainer(){
        mapContainer.getChildren().clear();
    }

    //add the nodes of the cells from the grid to the map container
    public void drawMap(){
        for(int i=0; i < grid.getRows(); i++){
            for(int j=0; j < grid.getCols(); j++){
                if(grid.getCell(i, j) != null){
                    mapContainer.getChildren().add(grid.getCell(i, j).getNode());
                }
            }
        }
        // insert fruits on the map
        cherryView.setSize(grid.getCellWidth()*1.1, grid.getCellHeight()*1.1);
        mapContainer.getChildren().add(cherryView.getNode());
        strawberryView.setSize(grid.getCellWidth()*1.1, grid.getCellHeight()*1.1);
        mapContainer.getChildren().add(strawberryView.getNode());
    }

    public void addPacManToTheMapContainer(){
        mapContainer.getChildren().add(pacManView.getArc());
    }
    
    public void addRedGhostToTheMapContainer(){
        mapContainer.getChildren().add(redGhostView.getImg());
    }
    public void addCyanGhostToTheMapContainer(){
        mapContainer.getChildren().add(cyanGhostView.getImg());
    }
    public void addOrangeGhostToTheMapContainer(){
        mapContainer.getChildren().add(orangeGhostView.getImg());
    }
    public void addPinkGhostToTheMapContainer(){
        mapContainer.getChildren().add(pinkGhostView.getImg());
    }

    //update the screen
    public void show(){
        stage.show();
    }
    
    // remove one cell
    public void removeCellView (int row, int col) {
        mapContainer.getChildren().remove(grid.getCell(row, col).getNode());
    }

    //setters and getters
    //-------------------------------------

    public void setGrid(GridView grid) {
        this.grid = grid;
    }

    public GridView getGrid() {
        return grid;
    }

    public Position getGridPosition() {
        return gridPosition;
    }

    public double getGridWidth() {
        return gridWidth;
    }

    public double getGridHeight() {
        return gridHeight;
    }

    public Scene getScene() {
        return scene;
    }

    public Group getMapContainer() {
        return mapContainer;
    }

    public void setPacManView(PacManView pacManView) {
        this.pacManView = pacManView;
    }
    
    public void setRedGhostView(RedGhostView redGhostView){
        this.redGhostView = redGhostView;
    }
     public void setOrangeGhostView(OrangeGhostView orangeGhostView){
        this.orangeGhostView = orangeGhostView;
    }
      public void setPinkGhostView(PinkGhostView pinkGhostView){
        this.pinkGhostView = pinkGhostView;
    }
       public void setCyanGhostView(CyanGhostView cyanGhostView){
        this.cyanGhostView = cyanGhostView;
    }
    

    public PacManView getPacManView() {
        return pacManView;
    }
    
    public RedGhostView getRedGhostView (){
        return redGhostView;
    }
    public CyanGhostView getCyanGhostView (){
        return cyanGhostView;
    }
    public OrangeGhostView getOrangeGhostView (){
        return orangeGhostView;
    }
    public PinkGhostView getPinkGhostView (){
        return pinkGhostView;
    }
    public CherryView getCherryView() {
        return cherryView;
    }
    public StrawberryView getStrawberryView() {
        return strawberryView;
    }
    
    //-------------------------------------
    
    public void setBottom(int lives){
        
        bottomPane.getChildren().clear();
        
        if (!fruitsImages.isEmpty()){
            bottomPane.getChildren().addAll(fruitsImages);
            for (int i = 0; i < fruitsImages.size(); i++){
                bottomPane.getChildren().get(i).setLayoutX(SCREEN_WIDTH - (i + 1) * BOTTOM_HEIGHT);
            }
        }
        
        if (lives  > -1){
            livesImages.clear();
            for (int i = 0; i < lives; i++){
                livesImages.add(new liveView(BOTTOM_HEIGHT, BOTTOM_HEIGHT).getImg());
            }
        }
        bottomPane.getChildren().addAll(livesImages);
        
        for (int i = fruitsImages.size(); i < bottomPane.getChildren().size(); i++){
            bottomPane.getChildren().get(i).setLayoutX((i - fruitsImages.size()) * BOTTOM_HEIGHT);
        }
        
    }
    
    public void addFruit(String fruit){
        
        if (fruit.equals("strawberry")){
            fruitsImages.add(new StrawberryView(BOTTOM_HEIGHT, BOTTOM_HEIGHT).getNode());
        }
        
        else if (fruit.equals("cherry")){
            fruitsImages.add(new CherryView(BOTTOM_HEIGHT, BOTTOM_HEIGHT).getNode());
        }
        
        setBottom(-1);
        
    }
    
    public void updateLives(int lives){
        if (lives > livesImages.size()){
            addLive();
        }
        else if (lives < livesImages.size()){
            deductLive();
        }
    }
    
    public void addLive(){
        livesImages.add(new liveView(BOTTOM_HEIGHT, BOTTOM_HEIGHT).getImg());
        bottomPane.getChildren().add(livesImages.get(livesImages.size()-1));
        bottomPane.getChildren().get(bottomPane.getChildren().size() - 1).setLayoutX((livesImages.size() - 1) * BOTTOM_HEIGHT);
    }
    
    public void deductLive(){
        livesImages.remove(livesImages.size() - 1);
        bottomPane.getChildren().remove(bottomPane.getChildren().size() - 1);
    }
    
    //Score an stage box methods
    //-------------------------------------
    
    public void updateScore(int score){
        scoreText.setText("Score\n" + Integer.toString(score));
    }
    
    public void updateStage(int stage){
        stageText.setText("Stage\n" + Integer.toString(stage));
    }
}
