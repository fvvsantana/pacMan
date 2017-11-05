package pacman;

import java.util.Random;

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
import model.characters.RedGhostModel;
import model.characters.PinkGhostModel;
import model.characters.OrangeGhostModel;
import model.characters.CyanGhostModel;

import utils.Orientation;

import view.characters.PacManView;
import view.characters.RedGhostView;
import view.characters.PinkGhostView;
import view.characters.OrangeGhostView;
import view.characters.CyanGhostView;

class Controller{
    View view;
    GridModel mapModel;
    PacManModel pacManModel;
    RedGhostModel redGhostModel;
    PinkGhostModel pinkGhostModel;
    OrangeGhostModel orangeGhostModel;
    CyanGhostModel cyanGhostModel;
    
    Random rand = new Random();
    int num;
    
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
        pacManModel = new PacManModel(2,2);
        redGhostModel = new RedGhostModel (3,2);
        pinkGhostModel = new PinkGhostModel (4,2);
        orangeGhostModel = new OrangeGhostModel (5,2);
        cyanGhostModel = new CyanGhostModel (6,2);

        //add a controller to the PacManModel
        addPacManModelController(view.getScene());
        
        

        //set PacManView in the View
        view.setPacManView(new PacManView(view.getGrid().getCellWidth()/2, view.getGrid().getCellHeight()/2));
        
        view.addPacManToTheMapContainer();
        
        
        view.setRedGhostView(new RedGhostView());
        
        view.addRedGhostToTheMapContainer();
        
        
        view.setPinkGhostView(new PinkGhostView());
        
        view.addPinkGhostToTheMapContainer();
        
        
        view.setOrangeGhostView(new OrangeGhostView());
        
        view.addOrangeGhostToTheMapContainer();
        
        
        view.setCyanGhostView(new CyanGhostView());
        
        view.addCyanGhostToTheMapContainer();
        
        
        
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                //update the position, width, height and orientation of the pacManView according to the pacManModel and the grid's dimensions
                updateModels(pacManModel);
                updatePacManView(pacManModel);
                
                updateModels(redGhostModel);
                updateRedGhost(redGhostModel);
                
                updatePinkGhost(pinkGhostModel);
                updateModels(pinkGhostModel);
                
                updateCyanGhost(cyanGhostModel);
                updateModels(cyanGhostModel);
                
                updateOrangeGhost(orangeGhostModel);
                updateModels(orangeGhostModel);
                
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
    private void updateModels(CharacterModel characterModel){
        // verifica se esta num tunel
        if (characterModel.getOrientation() == characterModel.getNextOrientation() && checkTunnel(characterModel)){
            characterModel.move();
            return;
        }
        
        // verifica se deve atualizar a orientacao atual
        if (!checkCollisionNext(characterModel))
            characterModel.setOrientation(characterModel.getNextOrientation());
        
        // depois atualiza a posicao atual
        if (!checkCollision(characterModel)) {
            characterModel.setMoving(true);
            characterModel.move();
        } else {
            characterModel.setMoving(false);
        }
    } 
    //update the position, width, height and orientation of the pacManView according to the pacManModel and the grid's dimensions
    public void updatePacManView(PacManModel pacManModel){
        view.getPacManView().setPosition(view.getGrid().getCellPosition(pacManModel.getRealRow(), pacManModel.getRealCol()));
        view.getPacManView().setOrientation(pacManModel.getOrientation());
        if (pacManModel.isMoving())
            view.getPacManView().updateArc();
    }
    
    public void updateRedGhost(RedGhostModel redGhostModel){
        view.getRedGhostView().setPosition(view.getGrid().getCellPosition(redGhostModel.getRealRow(), redGhostModel.getRealCol()));
        view.getRedGhostView().UpgradeImg();
        
        if (checkCollision(redGhostModel)){
            num = rand.nextInt(1001)+1;
            if (num%2 == 1){
            redGhostModel.setNextOrientation(pacManModel.getOrientation());
            }else{
                RandomWalk(redGhostModel, num);
            }
        }
    // view.getRedGhostView().setOrientation(redGhostModel.getOrientation());
    }
    
    public void updatePinkGhost(PinkGhostModel pinkGhostModel){
        view.getPinkGhostView().setPosition(view.getGrid().getCellPosition(pinkGhostModel.getRealRow(), pinkGhostModel.getRealCol()));
        view.getPinkGhostView().UpgradeImg();
        
        if(checkCollisionNext(pinkGhostModel)){
            num = rand.nextInt(1000)+1;
            if (num%2 == 1){
            pinkGhostModel.setNextOrientation(pacManModel.getOrientation());
            }else{
                RandomWalk(pinkGhostModel, num);
            }
        }
        
    // view.getRedGhostView().setOrientation(redGhostModel.getOrientation());
    }
    
    public void updateCyanGhost(CyanGhostModel cyanGhostModel){
        view.getCyanGhostView().setPosition(view.getGrid().getCellPosition(cyanGhostModel.getRealRow(), cyanGhostModel.getRealCol()));
        view.getCyanGhostView().UpgradeImg();
        
        if(checkCollisionNext(cyanGhostModel)){
            
            if (DistanceBetweenCharacters(cyanGhostModel,pacManModel) <= 10){
            cyanGhostModel.setNextOrientation(redGhostModel.getOrientation());
            }else{
                num = rand.nextInt(1000)+1;
                RandomWalk(cyanGhostModel, num);
            }
        }
    // view.getRedGhostView().setOrientation(redGhostModel.getOrientation());
    }
    
    public void updateOrangeGhost(OrangeGhostModel orangeGhostModel){
        view.getOrangeGhostView().setPosition(view.getGrid().getCellPosition(orangeGhostModel.getRealRow(), orangeGhostModel.getRealCol()));
        view.getOrangeGhostView().UpgradeImg();
        
        
        if(checkCollisionNext(orangeGhostModel)){
            num = rand.nextInt(1000)+1;
            RandomWalk(orangeGhostModel, num);
        }
    // view.getRedGhostView().setOrientation(redGhostModel.getOrientation());
    }
    
    public void RandomWalk (CharacterModel characterModel,int num){
        
       
                if(num > 750){
                    characterModel.setNextOrientation(Orientation.DOWN);
                }else if (num > 500 && num <= 750){
                    characterModel.setNextOrientation(Orientation.UP);
                }else if (num > 250 && num <= 500){
                    characterModel.setNextOrientation(Orientation.RIGHT);
                }else{
                    characterModel.setNextOrientation(Orientation.LEFT);
                }
            
    }
    public int DistanceBetweenCharacters (CharacterModel characterModel1, CharacterModel characterModel2){
        return (int)Math.sqrt(Math.pow(characterModel1.getCol()+characterModel2.getCol(),2)    +
                              Math.pow(characterModel1.getRow()+characterModel2.getRow(),2)    );
    }
    
    
    public boolean checkTunnel(CharacterModel characterModel) {
        Orientation orientation = characterModel.getOrientation();
        int row = characterModel.getRow();
        int col = characterModel.getCol();
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
                return col%CharacterModel.FATOR == 0 &&
                  mapModel.getCell((row-1)/CharacterModel.FATOR, col/CharacterModel.FATOR) instanceof ObstacleCellModel;
            case DOWN:
                return col%CharacterModel.FATOR == 0 &&
                  mapModel.getCell((row+1)/CharacterModel.FATOR+1,col/CharacterModel.FATOR) instanceof ObstacleCellModel;
            case LEFT:
                return row%CharacterModel.FATOR == 0 &&
                  mapModel.getCell(row/CharacterModel.FATOR, (col-1)/CharacterModel.FATOR) instanceof ObstacleCellModel;
            case RIGHT:
                return row%CharacterModel.FATOR == 0 &&
                  mapModel.getCell(row/CharacterModel.FATOR,(col+1)/CharacterModel.FATOR+1) instanceof ObstacleCellModel;
        }
        return true;
    }
    public boolean checkCollisionNextUP(CharacterModel characterModel) {
        Orientation orientation = characterModel.getNextOrientation();
        int row = characterModel.getRow();
        int col = characterModel.getCol();

                if( col%CharacterModel.FATOR != 0 ||
                    mapModel.getCell(row/CharacterModel.FATOR-1, col/CharacterModel.FATOR) instanceof ObstacleCellModel) return false;
            
            return true;
    }
    
    public boolean checkCollisionNextDOWN(CharacterModel characterModel) {
        Orientation orientation = characterModel.getNextOrientation();
        int row = characterModel.getRow();
        int col = characterModel.getCol();

                if( col%CharacterModel.FATOR != 0 ||
                    mapModel.getCell(row/CharacterModel.FATOR+1, col/CharacterModel.FATOR) instanceof ObstacleCellModel) return false;
                
            return true;
    }
    public boolean checkCollisionNextLEFT(CharacterModel characterModel) {
        Orientation orientation = characterModel.getNextOrientation();
        int row = characterModel.getRow();
        int col = characterModel.getCol();

                if( row%CharacterModel.FATOR != 0 ||
                    mapModel.getCell(row/CharacterModel.FATOR, col/CharacterModel.FATOR-1) instanceof ObstacleCellModel) return false;
             return true;
    }
    public boolean checkCollisionNextRIGHT(CharacterModel characterModel) {
        Orientation orientation = characterModel.getNextOrientation();
        int row = characterModel.getRow();
        int col = characterModel.getCol();

               if (row%CharacterModel.FATOR != 0 ||
                    mapModel.getCell(row/CharacterModel.FATOR, col/CharacterModel.FATOR+1) instanceof ObstacleCellModel) return false;
        
            return true;
    }
    
    
    public boolean checkCollisionNext(CharacterModel characterModel) {
        Orientation orientation = characterModel.getNextOrientation();
        int row = characterModel.getRow();
        int col = characterModel.getCol();
        switch (orientation) {
            case UP:
                return col%CharacterModel.FATOR != 0 ||
                    mapModel.getCell(row/CharacterModel.FATOR-1, col/CharacterModel.FATOR) instanceof ObstacleCellModel;
            case DOWN:
                return col%CharacterModel.FATOR != 0 ||
                    mapModel.getCell(row/CharacterModel.FATOR+1, col/CharacterModel.FATOR) instanceof ObstacleCellModel;
            case LEFT:
                return row%CharacterModel.FATOR != 0 ||
                    mapModel.getCell(row/CharacterModel.FATOR, col/CharacterModel.FATOR-1) instanceof ObstacleCellModel;
            case RIGHT:
                return row%CharacterModel.FATOR != 0 ||
                    mapModel.getCell(row/CharacterModel.FATOR, col/CharacterModel.FATOR+1) instanceof ObstacleCellModel;
        }
        return true;
    }
}
