package model.characters;

import model.fruits.FruitModel;
import model.grid.CellModel;
import java.util.ArrayList;
import utils.Orientation;

public class PacManModel extends CellModel{
    private int row;
    private int col;
    Orientation orientation;
    private int score;
    private int lives;
    ArrayList<FruitModel> fruits;

    public PacManModel(int row, int col) {
        this.row = row;
        this.col = col;
        orientation = Orientation.RIGHT;
        score = 0;
        lives = 3;
        fruits = new ArrayList<>();
    }
    public void addScore (int score){
        this.score +=score;
    }
    public int getScore (){
        return score;
    }
    
    public void addLive (int lives){
        this.lives +=lives;
    }
    public int getLives (){
        return lives;
    }
    
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