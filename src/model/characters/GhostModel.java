package model.characters;

import model.grid.CellModel;
import java.util.ArrayList;
import utils.Orientation;

public abstract class GhostModel {
    private int row;
    private int col;
    private boolean eatable;
    Orientation orientation;

    public GhostModel(int row, int col) {
        this.row = row;
        this.col = col;
        eatable = false;
        }
    
    public GhostModel (){
    	this (0,0);
    }
    public void setEatable (boolean eatable){
    	this.eatable = eatable;
    }
    
    abstract public void RunAway();
    abstract public void WalkInTheMap();
    abstract public void WaitInSafeZone();
    
    
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void moveUp(){
        row--;
    }

    public void moveDown(){
        row++;
    }

    public void moveLeft(){
        col--;
    }

    public void moveRight(){
        col++;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

}