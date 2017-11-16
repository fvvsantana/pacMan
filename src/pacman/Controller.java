package pacman;

import java.util.ArrayList;
import java.util.Iterator;
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
import model.grid.EmptyCellModel;
import utils.AudioManager;

import utils.Orientation;
import utils.Updatable;

import view.characters.PacManView;
import view.characters.RedGhostView;
import view.characters.PinkGhostView;
import view.characters.OrangeGhostView;
import view.characters.CyanGhostView;

class Controller{
    private View view;
    private GridModel mapModel;
    private PacManModel pacManModel;
    private RedGhostModel redGhostModel;
    private PinkGhostModel pinkGhostModel;
    private OrangeGhostModel orangeGhostModel;
    private CyanGhostModel cyanGhostModel;
    private AudioManager audioManager;
    private ArrayList<Updatable> updates;
    
    private final Random rand = new Random();
    private final int maxrand = 10000;
    private final int maxdist = 8 * CharacterModel.FACTOR;
    
    public void run(Stage primaryStage){
        
        //initialize the audio manager
        audioManager = new AudioManager();
        
        //initialize the updates array
        updates = new ArrayList<>();
        
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

        //create a PacManModel setting his position as (23, 13)
        pacManModel = new PacManModel(23, 13);
        redGhostModel = new RedGhostModel (25,20);
        pinkGhostModel = new PinkGhostModel (20,2);
        orangeGhostModel = new OrangeGhostModel (2,20);
        cyanGhostModel = new CyanGhostModel (20,20);

        //add a controller to the PacManModel
        addPacManModelController(view.getScene());

        //set PacManView in the View
        view.setPacManView(new PacManView(view.getGrid().getCellWidth()/2, view.getGrid().getCellHeight()/2));
        view.addPacManToTheMapContainer();
        
        // create the ghosts views and add their updates
        view.setRedGhostView(new RedGhostView());
        view.addRedGhostToTheMapContainer();
        updates.add(view.getRedGhostView());
        
        view.setPinkGhostView(new PinkGhostView());
        view.addPinkGhostToTheMapContainer();
        updates.add(view.getPinkGhostView());
        
        view.setOrangeGhostView(new OrangeGhostView());
        view.addOrangeGhostToTheMapContainer();
        updates.add(view.getOrangeGhostView());
        
        view.setCyanGhostView(new CyanGhostView());
        view.addCyanGhostToTheMapContainer();
        updates.add(view.getCyanGhostView());
        
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                //update the position, width, height and orientation of the pacManView according to the pacManModel and the grid's dimensions
                updatePacManModel();
                updatePacManView(pacManModel);
                
                updateRedGhostModel();
                updateRedGhostView(redGhostModel);
                
                updatePinkGhostModel();
                updatePinkGhostView(pinkGhostModel);
                
                updateCyanGhostModel();
                updateCyanGhostView(cyanGhostModel);
                
                updateOrangeGhostModel();
                updateOrangeGhostView(orangeGhostModel);
                
                updates.forEach((updatable) -> {
                    updatable.update();
                });
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
                    updates.add((PowerPelletCellView)cellView);
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
    private void updateCharacterModel(CharacterModel characterModel){

        // verifica se esta num tunel
        if (checkTunnel(characterModel)){
            applyTunnel(characterModel);
            return;
        }
        
        // verifica se deve atualizar a orientacao atual
        if (!checkCollisionNext(characterModel)){
            characterModel.setOrientation(characterModel.getNextOrientation());
        }
            
        // depois atualiza a posicao atual
        if (!checkCollision(characterModel)) {
            characterModel.setMoving(true);
            characterModel.move();
        } else {
            characterModel.setMoving(false);
        }
    } 
    
    public boolean checkTunnel(CharacterModel characterModel) {
        double row = characterModel.getRealRow();
        double col = characterModel.getRealCol();
        return row < 1 || row+1 >= mapModel.getRows() || col < 1 || col+1 >= mapModel.getCols();
    }
    
    public void applyTunnel (CharacterModel characterModel) {
        Orientation orientation = characterModel.getOrientation();
        Orientation nextOrientation = characterModel.getNextOrientation();
         
        // verifica se está mudando a direção dentro do tunel
        if (orientation != nextOrientation) {
            if (orientation == nextOrientation.getOpposite()){
                characterModel.setOrientation(nextOrientation);
            }
        }
        characterModel.move();
        
        // verifica se está terminando o tunel
        if (characterModel.getRealCol() < -1)
            characterModel.setRealCol(mapModel.getCols());
        else if (characterModel.getRealCol() > mapModel.getCols())
            characterModel.setRealCol(-1);
        else if (characterModel.getRealRow() < -1)
            characterModel.setRealRow(mapModel.getRows());
        else if (characterModel.getRealRow() > mapModel.getRows())
            characterModel.setRealRow(-1);
    }
    
    public void updatePacManModel(){
        updateCharacterModel(pacManModel);
        
        if (!checkTunnel(pacManModel)){
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
    }
    
    private void updateRedGhostModel(){
        int num = rand.nextInt(4);
        if (num == 3)
            randomWalk(redGhostModel);
        else
            chasePoint(redGhostModel, pacManModel.getRealRow(), pacManModel.getRealCol());
        updateCharacterModel(redGhostModel);
    }
    
    private void updatePinkGhostModel(){
        if (pinkGhostModel.getRealCol() % 1 == 0 && pinkGhostModel.getRealRow() % 1 == 0){
            System.out.println();
            randomWalk(pinkGhostModel);
        }
        updateCharacterModel(pinkGhostModel);
    }
    
    private void updateCyanGhostModel(){
        int num = rand.nextInt(4);
        if (DistanceBetweenCharacters(cyanGhostModel, redGhostModel) < maxdist && num != 3)
            chasePoint(cyanGhostModel, pacManModel.getRealRow(), pacManModel.getRealCol());
        else
            randomWalk(cyanGhostModel);
        updateCharacterModel(cyanGhostModel);
    }
    
    private void updateOrangeGhostModel(){
        int num = rand.nextInt(4);
        if (DistanceBetweenCharacters(orangeGhostModel, pacManModel) > maxdist && num != 3)
            chasePoint(orangeGhostModel, pacManModel.getRealRow(), pacManModel.getRealCol());
        else
            randomWalk(orangeGhostModel);
        updateCharacterModel(orangeGhostModel);
    }
    
    //update the position, width, height and orientation of the pacManView according to the pacManModel and the grid's dimensions
    public void updatePacManView(PacManModel pacManModel){
        view.getPacManView().setPosition(view.getGrid().getCellPosition(pacManModel.getRealRow(), pacManModel.getRealCol()));
        view.getPacManView().setOrientation(pacManModel.getOrientation());
        if (pacManModel.isMoving())
            view.getPacManView().updateArc();
    }
            
    public void updateRedGhostView(RedGhostModel redGhostModel){
        view.getRedGhostView().setPosition(view.getGrid().getCellPosition(redGhostModel.getRealRow(), redGhostModel.getRealCol()));
    }        
    
    public void updatePinkGhostView(PinkGhostModel pinkGhostModel){
        view.getPinkGhostView().setPosition(view.getGrid().getCellPosition(pinkGhostModel.getRealRow(), pinkGhostModel.getRealCol()));
    }
    
    public void updateCyanGhostView(CyanGhostModel cyanGhostModel){
        view.getCyanGhostView().setPosition(view.getGrid().getCellPosition(cyanGhostModel.getRealRow(), cyanGhostModel.getRealCol()));
    }
    
    public void updateOrangeGhostView(OrangeGhostModel orangeGhostModel){
        view.getOrangeGhostView().setPosition(view.getGrid().getCellPosition(orangeGhostModel.getRealRow(), orangeGhostModel.getRealCol()));
    }

    public int DistanceBetweenCharacters (CharacterModel characterModel1, CharacterModel characterModel2){
        return (int)Math.sqrt(Math.pow(characterModel1.getCol()-characterModel2.getCol(),2) +
                              Math.pow(characterModel1.getRow()-characterModel2.getRow(),2));
    }

    public void randomWalk(CharacterModel characterModel){
        
        // se estiver num tunel nao deve alterar a orientacao
        if (checkTunnel(characterModel))
            return;
        
        ArrayList<Orientation> orientations = new ArrayList(4);
        orientations.add(Orientation.UP);
        orientations.add(Orientation.DOWN);
        orientations.add(Orientation.LEFT);
        orientations.add(Orientation.RIGHT);
        
        orientations.remove(characterModel.getOrientation().getOpposite());
        
        Iterator<Orientation> it = orientations.iterator();
        while (it.hasNext()) {
            if (checkCollisionOrientation(characterModel, it.next()))
                it.remove();
        }
        
        int num = rand.nextInt(orientations.size());
        characterModel.setNextOrientation(orientations.get(num));
        
    }
    
    public void chasePoint(CharacterModel characterModel, double xPoint, double yPoint){
        
        double x = characterModel.getCol()- xPoint * CharacterModel.FACTOR;
        double y = characterModel.getRow() - yPoint * CharacterModel.FACTOR;
        
        switch (characterModel.getOrientation()){
            case UP:
                if (!characterModel.isMoving()){
                    if (!checkCollisionOrientation(characterModel, Orientation.LEFT))
                        characterModel.setNextOrientation(Orientation.LEFT);
                    else
                        characterModel.setNextOrientation(Orientation.RIGHT);
                }else{
                    if (y > 0)
                        characterModel.setNextOrientation(Orientation.LEFT);
                    else if (y < 0)
                        characterModel.setNextOrientation(Orientation.RIGHT);
                    else
                        characterModel.setNextOrientation(Orientation.UP);
                }
                break;
                
            case DOWN:
                if (!characterModel.isMoving()){
                    if (!checkCollisionOrientation(characterModel, Orientation.LEFT))
                        characterModel.setNextOrientation(Orientation.LEFT);
                    else
                        characterModel.setNextOrientation(Orientation.RIGHT);
                }else{
                    if (y > 0)
                        characterModel.setNextOrientation(Orientation.LEFT);
                    else if (y < 0)
                        characterModel.setNextOrientation(Orientation.RIGHT);
                    else
                        characterModel.setNextOrientation(Orientation.DOWN);
                }
                break;
                
            case LEFT:
                if (!characterModel.isMoving()){
                    if (!checkCollisionOrientation(characterModel, Orientation.UP))
                        characterModel.setNextOrientation(Orientation.UP);
                    else
                        characterModel.setNextOrientation(Orientation.DOWN);
                }else{
                    if (x > 0)
                        characterModel.setNextOrientation(Orientation.UP);
                    else if (x < 0)
                        characterModel.setNextOrientation(Orientation.DOWN);
                    else
                        characterModel.setNextOrientation(Orientation.LEFT);
                }
                break;
                
            case RIGHT:
                if (!characterModel.isMoving()){
                    if (!checkCollisionOrientation(characterModel, Orientation.UP))
                        characterModel.setNextOrientation(Orientation.UP);
                    else
                        characterModel.setNextOrientation(Orientation.DOWN);
                }else{
                    if (x > 0)
                        characterModel.setNextOrientation(Orientation.UP);
                    else if (x < 0)
                        characterModel.setNextOrientation(Orientation.DOWN);
                    else
                        characterModel.setNextOrientation(Orientation.RIGHT);
                }
                break;
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
            default:
                return true;
        }
    }
    

    public boolean checkCollisionOrientation(CharacterModel characterModel, Orientation orientation){
        int row = characterModel.getRow();
        int col = characterModel.getCol();
        switch (orientation){
            case UP:
                return (col%CharacterModel.FACTOR != 0 ||
                        mapModel.getCell((row-1)/CharacterModel.FACTOR, col/CharacterModel.FACTOR) instanceof ObstacleCellModel);
            case DOWN:
                return (col%CharacterModel.FACTOR != 0 ||
                        mapModel.getCell((row+1)/CharacterModel.FACTOR+1, col/CharacterModel.FACTOR) instanceof ObstacleCellModel);
            case LEFT:
                return (row%CharacterModel.FACTOR != 0 ||
                        mapModel.getCell(row/CharacterModel.FACTOR, (col-1)/CharacterModel.FACTOR) instanceof ObstacleCellModel);
            case RIGHT:
                return (row%CharacterModel.FACTOR != 0 ||
                        mapModel.getCell(row/CharacterModel.FACTOR, (col+1)/CharacterModel.FACTOR + 1) instanceof ObstacleCellModel);
        }
        return false;
    }
    
    public boolean checkCollisionNext(CharacterModel characterModel) {
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
