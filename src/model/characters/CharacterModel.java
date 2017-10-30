package model.characters;

import utils.Orientation;

public abstract class CharacterModel {
    double row;
    double col;
    double speed;
    Orientation orientation;

    // multiple constructors
    public CharacterModel(double row, double col) {
        this.row = row;
        this.col = col;
        orientation = Orientation.RIGHT;
        speed = 0.05f;
    }

    public CharacterModel(double row, double col, Orientation orientation) {
        this.row = row;
        this.col = col;
        this.orientation = orientation;
        speed = 0.05f;
    }

    public CharacterModel(double row, double col, double speed, Orientation orientation) {
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

    public double getRow() {
        return row;
    }

    public double getCol() {
        return col;
    }

    public double getSpeed() {
        return speed;
    }

    public void setRow(double row) {
        this.row = row;
    }

    public void setCol(double col) {
        this.col = col;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
}
