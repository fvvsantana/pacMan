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
import model.characters.GhostModel;
import model.grid.EmptyCellModel;
import utils.AudioManager;
import utils.GhostState;

import utils.Orientation;
import utils.Updatable;

import view.characters.PacManView;
import view.characters.RedGhostView;
import view.characters.PinkGhostView;
import view.characters.OrangeGhostView;
import view.characters.CyanGhostView;
import view.characters.GhostView;

class Controller{
    private View view;
    private GridModel mapModel;
    private PacManModel pacManModel;
    private GhostModel redGhostModel;
    private GhostModel pinkGhostModel;
    private GhostModel orangeGhostModel;
    private GhostModel cyanGhostModel;
    private AudioManager audioManager;
    private ArrayList<Updatable> updates;
    
    private final Random rand = new Random();
    
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
        
        //create the ghosts models and add their updates
        redGhostModel = new GhostModel(25,20);
        updates.add(redGhostModel);
        
        pinkGhostModel = new GhostModel(20,2);
        updates.add(pinkGhostModel);
        
        orangeGhostModel = new GhostModel(2,20);
        updates.add(orangeGhostModel);
        
        cyanGhostModel = new GhostModel(20,20);
        updates.add(cyanGhostModel);

        //add a controller to the PacManModel
        addPacManModelController(view.getScene());

        //set PacManView in the View
        view.setPacManView(new PacManView(view.getGrid().getCellWidth()/2, view.getGrid().getCellHeight()/2));
        view.addPacManToTheMapContainer();
        
        // create the ghosts views and add their updates
        view.setRedGhostView(new RedGhostView(view.getGrid().getCellWidth(), view.getGrid().getCellHeight()));
        view.addRedGhostToTheMapContainer();
        updates.add(view.getRedGhostView());
        
        view.setPinkGhostView(new PinkGhostView(view.getGrid().getCellWidth(), view.getGrid().getCellHeight()));
        view.addPinkGhostToTheMapContainer();
        updates.add(view.getPinkGhostView());
        
        view.setOrangeGhostView(new OrangeGhostView(view.getGrid().getCellWidth(), view.getGrid().getCellHeight()));
        view.addOrangeGhostToTheMapContainer();
        updates.add(view.getOrangeGhostView());
        
        view.setCyanGhostView(new CyanGhostView(view.getGrid().getCellWidth(), view.getGrid().getCellHeight()));
        view.addCyanGhostToTheMapContainer();
        updates.add(view.getCyanGhostView());
        
        updates.add(pacManModel);
        
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                //update the position, width, height and orientation of the pacManView according to the pacManModel and the grid's dimensions
                updatePacmanModel(pacManModel);
                updatePacManView(pacManModel);
                
                updateRedGhostModel();
                updateGhostView(redGhostModel, view.getRedGhostView());
                
                updatePinkGhostModel();
                updateGhostView(pinkGhostModel, view.getPinkGhostView());
                
                updateCyanGhostModel();
                updateGhostView(cyanGhostModel, view.getCyanGhostView());
                
                updateOrangeGhostModel();
                updateGhostView(orangeGhostModel, view.getOrangeGhostView());
                
                
                
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
    private void updateChracterModel(CharacterModel characterModel){

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
    
    private void updatePacmanModel (PacManModel pacManModel) {
        
        updateChracterModel(pacManModel);
        
        // verifica se está pegando um item
        if (!checkTunnel(pacManModel)) {
            double row = pacManModel.getRealRow();
            double col = pacManModel.getRealCol();
            if (row % 1 == 0 && col % 1 == 0) {
                // caso seja uma pacdot normal
                if (mapModel.getCell((int) row, (int) col) instanceof PacDotCellModel) {
                    mapModel.addCell(new EmptyCellModel(), (int) row, (int) col);
                    view.removeCellView((int) row, (int) col);
                } // caso seja uma power pellet
                else if (mapModel.getCell((int) row, (int) col) instanceof PowerPelletCellModel) {

                    // marca o pacman como poderoso
                    pacManModel.setPowerful(true);
                    pacManModel.setRealSpeed(2 * 0.0625);

                    // muda o estado dos fantasmas para fugindo
                    redGhostModel.startRunning();
                    pinkGhostModel.startRunning();
                    cyanGhostModel.startRunning();
                    orangeGhostModel.startRunning();

                    mapModel.addCell(new EmptyCellModel(), (int) row, (int) col);
                    view.removeCellView((int) row, (int) col);
                }
            }
        }
        
        // verifica se está colidindo com um fantasma
        if (checkCollisionCharacters(pacManModel, redGhostModel)) {
            if (redGhostModel.isEatable()) {
                redGhostModel.setState(GhostState.DEAD);
            }
        }
        if (checkCollisionCharacters(pacManModel, pinkGhostModel)) {
            if (pinkGhostModel.isEatable()) {
                pinkGhostModel.setState(GhostState.DEAD);
            }
        }
        if (checkCollisionCharacters(pacManModel, cyanGhostModel)) {
            if (cyanGhostModel.isEatable()) {
                cyanGhostModel.setState(GhostState.DEAD);
            }
        }
        if (checkCollisionCharacters(pacManModel, orangeGhostModel)) {
            if (orangeGhostModel.isEatable()) {
                orangeGhostModel.setState(GhostState.DEAD);
            }
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

    public double distanceBetweenCharacters (CharacterModel characterModel1, CharacterModel characterModel2){
        return (Math.sqrt(Math.pow(characterModel1.getRealCol()-characterModel2.getRealCol(),2) +
                              Math.pow(characterModel1.getRealRow()-characterModel2.getRealRow(),2)));
    }
    
    public boolean checkCollisionCharacters (CharacterModel characterModel1, CharacterModel characterModel2){
        return distanceBetweenCharacters(characterModel1, characterModel2) < 1;
    }
    
    private void updateRedGhostModel(){
        // atualiza a posicao de acordo com o estado atual
        switch (redGhostModel.getState()) {
            case NORMAL:
                redGhostModel.setRealSpeed(0.0625);
                chasePoint(redGhostModel, pacManModel.getRealCol(), pacManModel.getRealRow());
                break;
            case RUNNING:
            case RUNNING_END:
                runAwayPoint(redGhostModel, pacManModel.getRealCol(), pacManModel.getRealRow());
                break;
            case DEAD:
                redGhostModel.setRealSpeed(0.0625);
                chasePoint(redGhostModel, 11, 13);
                if (redGhostModel.getRealRow() == 11 && redGhostModel.getRealCol() == 13)
                    redGhostModel.setState(GhostState.NORMAL);
                break;
        }
        updateChracterModel(redGhostModel);
    }
    
    private void updatePinkGhostModel(){
        // atualiza a posicao de acordo com o estado atual
        switch (pinkGhostModel.getState()) {
            case NORMAL:
                pinkGhostModel.setRealSpeed(0.0625);
                chasePoint(pinkGhostModel, pacManModel.getRealCol(), pacManModel.getRealRow());
                break;
            case RUNNING:
            case RUNNING_END:
                runAwayPoint(pinkGhostModel, pacManModel.getRealCol(), pacManModel.getRealRow());
                break;
            case DEAD:
                pinkGhostModel.setRealSpeed(0.0625);
                chasePoint(pinkGhostModel, 11, 13);
                if (pinkGhostModel.getRealRow() == 11 && pinkGhostModel.getRealCol() == 13)
                    pinkGhostModel.setState(GhostState.NORMAL);
                break;
        }
        updateChracterModel(pinkGhostModel);
    }
    
    private void updateCyanGhostModel(){
        // atualiza a posicao de acordo com o estado atual
        switch (cyanGhostModel.getState()) {
            case NORMAL:
                cyanGhostModel.setRealSpeed(0.0625);
                chasePoint(cyanGhostModel, pacManModel.getRealCol(), pacManModel.getRealRow());
                break;
            case RUNNING:
            case RUNNING_END:
                runAwayPoint(cyanGhostModel, pacManModel.getRealCol(), pacManModel.getRealRow());
                break;
            case DEAD:
                cyanGhostModel.setRealSpeed(0.0625);
                chasePoint(cyanGhostModel, 11, 13);
                if (cyanGhostModel.getRealRow() == 11 && cyanGhostModel.getRealCol() == 13)
                    cyanGhostModel.setState(GhostState.NORMAL);
                break;
        }
        updateChracterModel(cyanGhostModel);
    }
    
    private void updateOrangeGhostModel(){
        // atualiza a posicao de acordo com o estado atual
        switch (orangeGhostModel.getState()) {
            case NORMAL:
                orangeGhostModel.setRealSpeed(0.0625);
                chasePoint(orangeGhostModel, pacManModel.getRealCol(), pacManModel.getRealRow());
                break;
            case RUNNING:
            case RUNNING_END:
                runAwayPoint(orangeGhostModel, pacManModel.getRealCol(), pacManModel.getRealRow());
                break;
            case DEAD:
                orangeGhostModel.setRealSpeed(0.0625);
                chasePoint(orangeGhostModel, 11, 13);
                if (orangeGhostModel.getRealRow() == 11 && orangeGhostModel.getRealCol() == 13)
                    orangeGhostModel.setState(GhostState.NORMAL);
                break;
        }
        updateChracterModel(orangeGhostModel);
    }
    
    //update the position, width, height and orientation of the pacManView according to the pacManModel and the grid's dimensions
    public void updatePacManView(PacManModel pacManModel){
        view.getPacManView().setPosition(view.getGrid().getCellPosition(pacManModel.getRealRow(), pacManModel.getRealCol()));
        view.getPacManView().setOrientation(pacManModel.getOrientation());
        
        if (pacManModel.isMoving())
            view.getPacManView().updateArc();
    }
            
    public void updateGhostView(GhostModel ghostModel, GhostView ghostView){
        
        ghostView.setPosition(view.getGrid().getCellPosition(ghostModel.getRealRow(), ghostModel.getRealCol()));
        
        ghostView.setState(ghostModel.getState());
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
        
    public void runAwayPoint(CharacterModel characterModel, double xPoint, double yPoint){
        
        double x = - characterModel.getCol() + xPoint * CharacterModel.FACTOR;
        double y = - characterModel.getRow() + yPoint * CharacterModel.FACTOR;
        
        switch (characterModel.getOrientation()){
            case UP:
                if (!characterModel.isMoving()){
                    if (!checkCollisionOrientation(characterModel, Orientation.LEFT))
                        characterModel.setNextOrientation(Orientation.LEFT);
                    else
                        characterModel.setNextOrientation(Orientation.RIGHT);
                }else{
                    if (x > 0)
                        characterModel.setNextOrientation(Orientation.LEFT);
                    else if (x < 0)
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
                    if (x > 0)
                        characterModel.setNextOrientation(Orientation.LEFT);
                    else if (x < 0)
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
                    if (y > 0)
                        characterModel.setNextOrientation(Orientation.UP);
                    else if (y < 0)
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
                    if (y > 0)
                        characterModel.setNextOrientation(Orientation.UP);
                    else if (y < 0)
                        characterModel.setNextOrientation(Orientation.DOWN);
                    else
                        characterModel.setNextOrientation(Orientation.RIGHT);
                }
                break;
        }
    }
    
    public void chasePoint(CharacterModel characterModel, double xPoint, double yPoint){
        
        double x = characterModel.getCol() - xPoint * CharacterModel.FACTOR;
        double y = characterModel.getRow() - yPoint * CharacterModel.FACTOR;
        
        switch (characterModel.getOrientation()){
            case UP:
                if (!characterModel.isMoving()){
                    if (!checkCollisionOrientation(characterModel, Orientation.LEFT))
                        characterModel.setNextOrientation(Orientation.LEFT);
                    else
                        characterModel.setNextOrientation(Orientation.RIGHT);
                }else{
                    if (x > 0)
                        characterModel.setNextOrientation(Orientation.LEFT);
                    else if (x < 0)
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
                    if (x > 0)
                        characterModel.setNextOrientation(Orientation.LEFT);
                    else if (x < 0)
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
                    if (y > 0)
                        characterModel.setNextOrientation(Orientation.UP);
                    else if (y < 0)
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
                    if (y > 0)
                        characterModel.setNextOrientation(Orientation.UP);
                    else if (y < 0)
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
