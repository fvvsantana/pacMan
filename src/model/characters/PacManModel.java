package model.characters;

import model.fruits.FruitModel;
import java.util.ArrayList;
import utils.Orientation;
import utils.Updatable;

public class PacManModel extends CharacterModel implements Updatable{
    
    // tempo de duração do poder = 7 segundos * 60 frames por segundo
    public static final int POWER_TIME = 420;
    
    private int score;
    private int lives;
    private int counter = 0;
    private int ghostCounterEaten = 0;
    private boolean powerful;
    private ArrayList<FruitModel> fruits;
    
    public PacManModel() {
        super(1, 1);
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
    
    public void resetLives(){
        lives = 3;
    }
    public void resetScore(){
        score = 0;
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
    
    public void reset() {
        powerful = false;
        setOrientation(Orientation.RIGHT);
        setNextOrientation(Orientation.RIGHT);
        setMoving(false);
    }
    
    public int sumPacDotScore(){
        score += 10;
        return score;
    }
    
    public int sumPowerPalletScore(){
        score += 50;
        return score;
    }
    
    public int sumGhostEaten(){
        score += 200*Math.pow(2, ghostCounterEaten);
        ghostCounterEaten++;
        return score;
    }
    
    @Override
    public void update() {
        
        if(powerful && counter == POWER_TIME){
            setPowerful(false);
            counter = 0;
            ghostCounterEaten = 0;
        } else if(powerful && counter != POWER_TIME){
            counter++;
        }
    }
    
}
    
    
    
    
    
