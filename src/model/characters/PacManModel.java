package model.characters;

import model.fruits.FruitModel;
import java.util.ArrayList;

public class PacManModel extends CharacterModel{
    private int score;
    private int lives;
    private ArrayList<FruitModel> fruits;
    
    public PacManModel(double row, double col) {
        super(row, col);
        score = 0;
        lives = 3;
        fruits = new ArrayList<>();
    }
}