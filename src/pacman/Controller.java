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
import model.characters.CharacterModel;
import model.grid.GridModel;
import model.grid.CellModel;
import model.grid.ObstacleCellModel;
import model.grid.PacDotCellModel;
import model.grid.PowerPelletCellModel;
import model.characters.PacManModel;
import model.grid.EmptyCellModel;

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
                    pacManModel.setNextOrientation(Orientation.UP);
                    break;
                    
                case RIGHT:
                    pacManModel.setNextOrientation(Orientation.RIGHT);
                    break;
                    
                case DOWN:
                    pacManModel.setNextOrientation(Orientation.DOWN);
                    break;
                    
                case LEFT:
                    pacManModel.setNextOrientation(Orientation.LEFT);
                    break;
            }
        });
    }
    
    //move the pacManModel to the specified orientation 
    private void updatePacManModel(){
        // verifica se esta num tunel
        if (pacManModel.getOrientation() == pacManModel.getNextOrientation() && checkTunnel(pacManModel)){
            pacManModel.move();
            return;
        }
        
        // verifica se deve atualizar a orientacao atual
        if (!checkCollisionNext(pacManModel))
            pacManModel.setOrientation(pacManModel.getNextOrientation());
        
        // depois atualiza a posicao atual
        if (!checkCollision(pacManModel)) {
            pacManModel.setMoving(true);
            pacManModel.move();
        } else {
            pacManModel.setMoving(false);
        }
        
        // verifica se está pegando um item
        double row = pacManModel.getRealRow();
        double col = pacManModel.getRealCol();
        if (row%1 == 0 && col%1 == 0) {
            if (mapModel.getCell((int)row, (int)col) instanceof PacDotCellModel) {
                mapModel.addCell(new EmptyCellModel(), (int)row, (int)col);
                view.removeCellView((int)row, (int)col);
            } else if (mapModel.getCell((int)row, (int)col) instanceof PowerPelletCellModel) {
                /// TODO: definir fantasmas como comiveis
                mapModel.addCell(new EmptyCellModel(), (int)row, (int)col);
                view.removeCellView((int)row, (int)col);
            }
        }
    } 

    //update the position, width, height and orientation of the pacManView according to the pacManModel and the grid's dimensions
    public void updatePacManView(PacManModel pacManModel){
        view.getPacManView().setPosition(view.getGrid().getCellPosition(pacManModel.getRealRow(), pacManModel.getRealCol()));
        view.getPacManView().setOrientation(pacManModel.getOrientation());
        if (pacManModel.isMoving())
            view.getPacManView().updateArc();
    }
    
    public boolean checkTunnel(CharacterModel characterModel) {
        Orientation orientation = characterModel.getOrientation();
        switch (orientation) {
            case RIGHT:
                if (characterModel.getRealCol() > mapModel.getCols())
                    characterModel.setRealCol(-1);
                return characterModel.getRealCol()+1 >= mapModel.getCols();
            case LEFT:
                if (characterModel.getRealCol() <= -1)
                    characterModel.setRealCol(mapModel.getCols());
                return characterModel.getRealCol() < 1;
            default:
                return false;
        }
    }
    
    public boolean checkCollision(CharacterModel characterModel) {
        Orientation orientation = characterModel.getOrientation();
        int row = characterModel.getRow();
        int col = characterModel.getCol();
        switch (orientation) {
            case UP:
                return col%CharacterModel.FACTOR == 0 &&
                  mapModel.getCell((row-1)/CharacterModel.FACTOR, col/CharacterModel.FACTOR) instanceof ObstacleCellModel;
            case DOWN:
                return col%CharacterModel.FACTOR == 0 &&
                  mapModel.getCell((row+1)/CharacterModel.FACTOR+1,col/CharacterModel.FACTOR) instanceof ObstacleCellModel;
            case LEFT:
                return row%CharacterModel.FACTOR == 0 &&
                  mapModel.getCell(row/CharacterModel.FACTOR, (col-1)/CharacterModel.FACTOR) instanceof ObstacleCellModel;
            case RIGHT:
                return row%CharacterModel.FACTOR == 0 &&
                  mapModel.getCell(row/CharacterModel.FACTOR,(col+1)/CharacterModel.FACTOR+1) instanceof ObstacleCellModel;
        }
        return true;
    }
    
    public boolean checkCollisionNext(PacManModel characterModel) {
        Orientation orientation = characterModel.getNextOrientation();
        int row = characterModel.getRow();
        int col = characterModel.getCol();
        switch (orientation) {
            case UP:
                return col%CharacterModel.FACTOR != 0 ||
                    mapModel.getCell(row/CharacterModel.FACTOR-1, col/CharacterModel.FACTOR) instanceof ObstacleCellModel;
            case DOWN:
                return col%CharacterModel.FACTOR != 0 ||
                    mapModel.getCell(row/CharacterModel.FACTOR+1, col/CharacterModel.FACTOR) instanceof ObstacleCellModel;
            case LEFT:
                return row%CharacterModel.FACTOR != 0 ||
                    mapModel.getCell(row/CharacterModel.FACTOR, col/CharacterModel.FACTOR-1) instanceof ObstacleCellModel;
            case RIGHT:
                return row%CharacterModel.FACTOR != 0 ||
                    mapModel.getCell(row/CharacterModel.FACTOR, col/CharacterModel.FACTOR+1) instanceof ObstacleCellModel;
        }
        return true;
    }
}
