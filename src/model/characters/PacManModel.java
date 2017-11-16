package model.characters;

import model.fruits.FruitModel;
import java.util.ArrayList;
import utils.Updatable;

public class PacManModel extends CharacterModel implements Updatable{
    private int score;
    private int lives;
    private int counter = 0;
    private boolean powerful;

    
        
        
    private ArrayList<FruitModel> fruits;
    
    public PacManModel(double row, double col) {
        super(row, col);
        score = 0;
        lives = 3;
        fruits = new ArrayList<>();
        setEatable(false);
        setRunning(false);
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
        
        if(getPowerful() && counter == 720){
            setPowerful(false);
            counter = 0;
            setRealSpeed(0.0625);
            
        }else if(getPowerful() && counter != 720){
            counter++;
        }
    }
        
    }
    
    
    
    
    
