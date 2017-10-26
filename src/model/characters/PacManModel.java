package model.characters;

import model.fruits.FruitModel;
import java.util.ArrayList;

public class PacManModel extends CharacterModel{
    int score;
    int lives;
    ArrayList<FruitModel> fruits;

    public PacManModel(int row, int col) {
        super(row, col);
        score = 0;
        lives = 3;
        fruits = new ArrayList<>();
    }

}