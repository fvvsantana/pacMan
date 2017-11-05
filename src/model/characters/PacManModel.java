package model.characters;

import model.fruits.FruitModel;
import java.util.ArrayList;
import utils.Orientation;

public class PacManModel extends CharacterModel{
    private Orientation nextOrientation;
    boolean moving;
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

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Orientation getNextOrientation() {
        return nextOrientation;
    }

    public void setNextOrientation(Orientation nextOrientation) {
        this.nextOrientation = nextOrientation;
    }
}