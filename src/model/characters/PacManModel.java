package model.characters;

import model.fruits.FruitModel;
import java.util.ArrayList;

public class PacManModel extends CharacterModel{
    boolean moving;
    int score;
    int lives;
    ArrayList<FruitModel> fruits;

    public PacManModel(int row, int col) {
        super(row, col);
        moving = true;
        score = 0;
        lives = 3;
        fruits = new ArrayList<>();
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
}