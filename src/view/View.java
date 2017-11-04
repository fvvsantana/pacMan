
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
import javafx.stage.Stage;

import utils.Position;


public class View{
    private final double SCREEN_WIDTH = 600;
    private final double SCREEN_HEIGHT = 660;

    private Stage stage;
    private BorderPane root;
    private Pane centerPane; //the maze
    private Group mapContainer;
    private Scene scene;

    private GridView grid;
    private final Position gridPosition;
    private final double gridWidth;
    private final double gridHeight;

    private PacManView pacManView;
    
    private RedGhostView redGhostView;
    private OrangeGhostView orangeGhostView;
    private CyanGhostView cyanGhostView;
    private PinkGhostView pinkGhostView;
    
    public View(Stage stage){
        //layout configuration
        this.stage = stage;
        root = new BorderPane();
		root.setStyle("-fx-background-color: black");
        centerPane = new Pane();
        root.setCenter(centerPane);
        mapContainer = new Group();
        centerPane.getChildren().add(mapContainer);
        scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setTitle("Pacman");
        stage.setScene(scene);

        gridPosition = new Position(0, 0);
        gridWidth = SCREEN_WIDTH;
        gridHeight = SCREEN_HEIGHT;
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
    //-------------------------------------

}
