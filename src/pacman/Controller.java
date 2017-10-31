package pacman;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.animation.AnimationTimer;

import view.View;
import view.grid.GridView;
import view.grid.CellView;
import view.grid.ObstacleCellView;
import view.grid.PacDotCellView;
import view.grid.PowerPelletCellView;

import model.Maps;
import model.grid.GridModel;
import model.grid.CellModel;
import model.grid.ObstacleCellModel;
import model.grid.PacDotCellModel;
import model.grid.PowerPelletCellModel;
import model.characters.PacManModel;

import utils.Orientation;
import view.characters.PacManView;

class Controller{
    View view;
    GridModel mapModel;
    PacManModel pacManModel;
    
    public void run(Stage primaryStage){
        //generate the layout
        view = new View(primaryStage);

        //get the mapModel from the Maps class
        mapModel = Maps.mainMap();

        //generate the visual grid
        view.setGrid(generateGridView(mapModel));

        //remove all the childrem from the mapContainer
        view.clearMapContainer();

        //add the nodes of the cells from the grid to the map container
        view.drawMap();

        //create a PacManModel setting his position as (0,0)
        pacManModel = new PacManModel(1, 1);

        //add a controller to the PacManModel
        addPacManModelController(view.getScene());

        //set PacManView in the View
        view.setPacManView(new PacManView(view.getGrid().getCellWidth()/2, view.getGrid().getCellHeight()/2));

        view.addPacManToTheMapContainer();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                //update the position, width, height and orientation of the pacManView according to the pacManModel and the grid's dimensions
                updatePacManModel();
                updatePacManView(pacManModel);
            }

        }.start();

        //update the screen
        view.show();
    }

    //generate a GridView based on the passing argument GridModel
    private GridView generateGridView(final GridModel mapModel){
        int rows = mapModel.getRows();
        int cols = mapModel.getCols();
        GridView mapView = new GridView(rows, cols, view.getGridWidth(), view.getGridHeight(), view.getGridPosition());

        CellView cellView;
        CellModel cellModel;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){

                cellModel = mapModel.getCell(i, j);

                if(cellModel instanceof ObstacleCellModel){
                    cellView = new ObstacleCellView();
                }else if(cellModel instanceof PacDotCellModel){
                    cellView = new PacDotCellView();
                }else if(cellModel instanceof PowerPelletCellModel){
                    cellView = new PowerPelletCellView();
                }else{
                    cellView = null;
                }

                mapView.addCell(cellView, i, j);
            }
        }
        return mapView;
    }

    //add a controller to the PacManModel
    private void addPacManModelController(Scene scene){
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch(event.getCode()){
                case UP:
                    pacManModel.setOrientation(Orientation.UP);
                    break;
                    
                case RIGHT:
                    pacManModel.setOrientation(Orientation.RIGHT);
                    break;
                    
                case DOWN:
                    pacManModel.setOrientation(Orientation.DOWN);
                    break;
                    
                case LEFT:
                    pacManModel.setOrientation(Orientation.LEFT);
                    break;
            }
        });
    }
    
    //move the pacManModel to the specified orientation 
    private void updatePacManModel(){
        pacManModel.setMoving(false);
        switch(pacManModel.getOrientation()){ 
            case UP:  
                pacManModel.moveUp();
                pacManModel.setMoving(true);
                if (collisionCheckUp()){
                    pacManModel.setMoving(false);
                    pacManModel.setRow((int)(pacManModel.getRow() + 0.1));
                } 
                pacManModel.setOrientation(Orientation.UP); 
                break; 
 
            case DOWN:  
                pacManModel.moveDown(); 
                pacManModel.setMoving(true);
                if (collisionCheckDown()){
                    pacManModel.setMoving(false);
                    pacManModel.setRow((int)(pacManModel.getRow()));
                }
                pacManModel.setOrientation(Orientation.DOWN); 
                break; 
 
            case LEFT: 
                pacManModel.moveLeft(); 
                pacManModel.setMoving(true);
                if (collisionCheckLeft()){
                    pacManModel.setMoving(false);
                    pacManModel.setCol((int)(pacManModel.getCol() + 0.9));
                }
                pacManModel.setOrientation(Orientation.LEFT); 
                break; 
 
            case RIGHT: 
                pacManModel.moveRight(); 
                pacManModel.setMoving(true);
                if (collisionCheckRight()){
                    pacManModel.setMoving(false);
                    pacManModel.setCol((int)(pacManModel.getCol()));
                }
                pacManModel.setOrientation(Orientation.RIGHT); 
                break; 
        } 
    } 

    //update the position, width, height and orientation of the pacManView according to the pacManModel and the grid's dimensions
    public void updatePacManView(PacManModel pacManModel){
        view.getPacManView().setPosition(view.getGrid().getCellPosition(pacManModel.getRealRow(), pacManModel.getRealCol()));
        view.getPacManView().setOrientation(pacManModel.getOrientation());
        if (pacManModel.isMoving())
            view.getPacManView().updateArc();
    }
    
    public boolean collisionCheckUp(){
        int x = (int)(pacManModel.getRow());
        int y1 = (int)(pacManModel.getCol());
        int y2 = (int)(pacManModel.getCol() + 0.99);
        return (mapModel.getCell(x, y1) instanceof ObstacleCellModel ||
                mapModel.getCell(x, y2) instanceof ObstacleCellModel);
    }
    
    public boolean collisionCheckDown(){
        int x = (int)(pacManModel.getRow() + 0.99);
        int y1 = (int)(pacManModel.getCol());
        int y2 = (int)(pacManModel.getCol() + 0.99);
        return (mapModel.getCell(x, y1) instanceof ObstacleCellModel ||
                mapModel.getCell(x, y2) instanceof ObstacleCellModel);
    }
    
    public boolean collisionCheckLeft(){
        int x1 = (int)(pacManModel.getRow());
        int x2 = (int)(pacManModel.getRow() + 0.99);
        int y = (int)(pacManModel.getCol());
        return (mapModel.getCell(x1, y) instanceof ObstacleCellModel ||
                mapModel.getCell(x2, y) instanceof ObstacleCellModel);
    }
    
    public boolean collisionCheckRight(){
        int x1 = (int)(pacManModel.getRow());
        int x2 = (int)(pacManModel.getRow() + 0.99);
        int y = (int)(pacManModel.getCol() + 0.99);
        return (mapModel.getCell(x1, y) instanceof ObstacleCellModel ||
                mapModel.getCell(x2, y) instanceof ObstacleCellModel);
    }
}
