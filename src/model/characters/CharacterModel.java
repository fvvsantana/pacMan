package model.characters;

import utils.Orientation;

public abstract class CharacterModel {
    float row;
    float col;
    float speed;
    Orientation orientation;

    // multiple constructors
    public CharacterModel(float row, float col) {
        this.row = row;
        this.col = col;
        orientation = Orientation.RIGHT;
        speed = 0.05f;
    }

    public CharacterModel(float row, float col, Orientation orientation) {
        this.row = row;
        this.col = col;
        this.orientation = orientation;
        speed = 0.05f;
    }

    public CharacterModel(float row, float col, float speed, Orientation orientation) {
        this.row = row;
        this.col = col;
        this.speed = speed;
        this.orientation = orientation;
    }
    
    public void moveUp() {
        row -= speed;
    }

    public void moveDown() {
        row += speed;
    }

    public void moveLeft() {
        col -= speed;
    }

    public void moveRight() {
        col += speed;
    }

    public float getRow() {
        return row;
    }

    public float getCol() {
        return col;
    }

    public float getSpeed() {
        return speed;
    }

    public void setRow(float row) {
        this.row = row;
    }

    public void setCol(float col) {
        this.col = col;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
}
