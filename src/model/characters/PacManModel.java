package model.characters;

import model.fruits.FruitModel;
import java.util.ArrayList;

public class PacManModel extends CharacterModel{
    
    int score;
    int lives;
    ArrayList<FruitModel> fruits;

    public PacManModel(double row, double col) {
        super(row, col);
        nextOrientation = orientation;
        moving = true;
        score = 0;
        lives = 3;
        fruits = new ArrayList<>();
    }
    
    public void addScore (int addscore){
        score +=score;
    }
    public int getScore (){
        return score;
    }
    public void addLive (int live){
        lives +=live;
    }
    public int getLive (){
        return lives;
    }

    
}