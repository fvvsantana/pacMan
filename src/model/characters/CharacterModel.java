package model.characters;

import utils.Orientation;

public abstract class CharacterModel {
    
    // valores serao multiplicados pelo fator para manter inteiros
    public static final int FATOR = 100;
    Orientation nextOrientation;
    boolean moving;
    int row;
    int col;
    int speed;
    Orientation orientation;

    // multiple constructors
    public CharacterModel(double realRow, double realCol) {
        this(realRow, realCol, 0.05, Orientation.RIGHT);
    }

    public CharacterModel(double realRow, double realCol, double realSpeed, Orientation orientation) {
        this.row = (int) (FATOR * realRow);
        this.col = (int) (FATOR * realCol);
        this.speed = (int) (FATOR * realSpeed);
        this.orientation = orientation;
        System.out.printf("Criou.\nrow:%d\ncol:%d\nspeed:%d\n", row, col, speed);
    }
    
    public void move() {
        switch(orientation) {
            case UP:
                row -= speed;
                break;
            case DOWN:
                row += speed;
                break;
            case LEFT:
                col -= speed;
                break;
            case RIGHT:
                col += speed;
                break;
        }
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

    public int getRow() {
        return row;
    }
    
    public double getRealRow() {
        return (double) row/FATOR;
    }

    public int getCol() {
        return col;
    }

    public double getRealCol() {
        return (double) col/FATOR;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public double getRealSpeed() {
        return (double) speed/FATOR;
    }
    
    public void setRow(int row) {
        this.row = row;
    }

    public void setRealRow(double row) {
        this.row = (int) (FATOR * row);
    }
    
    public void setCol(int col) {
        this.col = col;
    }

    public void setRealCol(double col) {
        this.col = (int) (FATOR * col);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setRealSpeed(double speed) {
        this.speed = (int) (FATOR * speed);
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
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
