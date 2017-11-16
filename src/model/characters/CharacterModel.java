package model.characters;

import utils.Orientation;

public abstract class CharacterModel {
    
    // the values will be multiplied by the FACTOR to keep them integers
    public static final int FACTOR = 150;
    
    public static final int STANDARD_SPEED = 10;
    
    private Orientation orientation;
    private Orientation nextOrientation;
    private int row;
    private int col;
    private int speed;
    private boolean moving;
    

    // multiple constructors
    public CharacterModel(double realRow, double realCol) {
        this.row = (int) (FACTOR * realRow);
        this.col = (int) (FACTOR * realCol);
        this.speed = STANDARD_SPEED;
        this.orientation = Orientation.RIGHT;
        nextOrientation = orientation;
        moving = true;
    }

    public CharacterModel(double realRow, double realCol, double realSpeed, Orientation orientation) {
        this.row = (int) (FACTOR * realRow);
        this.col = (int) (FACTOR * realCol);
        this.speed = (int) (FACTOR * realSpeed);
        this.orientation = orientation;
        nextOrientation = orientation;
        moving = true;
    }
    
    // move to the defined orientation
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

    public int getCol() {
        return col;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    // return the values without the factor
    public double getRealRow() {
        return (double) row/FACTOR;
    }

    public double getRealCol() {
        return (double) col/FACTOR;
    }
    
    public double getRealSpeed() {
        return (double) speed/FACTOR;
    }
    
    public void setRow(int row) {
        this.row = row;
    }

    public void setRealRow(double row) {
        this.row = (int) (FACTOR * row);
    }
    
    public void setCol(int col) {
        this.col = col;
    }

    public void setRealCol(double col) {
        this.col = (int) (FACTOR * col);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        // ajusta a posicao para a nova velocidade
        row = row - (row%speed);
        col = col - (col%speed);
    }

    public void setRealSpeed(double speed) {
        setSpeed((int)(FACTOR * speed));
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Orientation getNextOrientation() {
        return nextOrientation;
    }
    

    public void setNextOrientation(Orientation nextOrientation) {
        this.nextOrientation = nextOrientation;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
}
