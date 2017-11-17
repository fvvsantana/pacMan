package model.characters;

import model.fruits.FruitModel;
import java.util.ArrayList;
import utils.Updatable;

public class PacManModel extends CharacterModel implements Updatable{
    
    // tempo de duração do poder = 7 segundos * 60 frames por segundo
    public static final int POWER_TIME = 420;
    
    private int score;
    private int lives;
    private int counter = 0;
    private boolean powerful;
    private ArrayList<FruitModel> fruits;
    
    public PacManModel(double row, double col) {
        super(row, col);
        score = 0;
        lives = 3;
        powerful = false;
        fruits = new ArrayList<>();
    }
    
    public void setPowerful(boolean powerful){
        this.powerful = powerful;
    }
    public boolean getPowerful(){
        return powerful;
    }
    
    public void updateLives(int lives){
        this.lives += lives;
    }
    public int getLives (){
        return lives;
    }
    
    public void updateScore (int score){
        this.score += score;
    }
    public int getScore (){
        return score;
    }

    @Override
    public void update() {
        
        if(powerful && counter == POWER_TIME){
            setPowerful(false);
            counter = 0;
        } else if(powerful && counter != POWER_TIME){
            counter++;
        }
    }
    
}
    
    
    
    
    
