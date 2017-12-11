package model.characters;

import java.io.Serializable;
import utils.Orientation;

public abstract class CharacterModel implements Serializable {
    
    // the values will be multiplied by the FACTOR to keep them integers
    public static final int FACTOR = 100;
    
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
        setSpeed(STANDARD_SPEED);
        this.orientation = Orientation.RIGHT;
        nextOrientation = orientation;
        moving = false;
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
        adjustPosition();
    }

    public void setRealRow(double row) {
        setRow((int) (FACTOR * row));
    }
    
    public void setCol(int col) {
        this.col = col;
        adjustPosition();
    }

    public void setRealCol(double col) {
        setCol((int)(FACTOR*col));
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        adjustPosition();
    }

    public void setRealSpeed(double speed) {
        setSpeed((int)(FACTOR * speed));
    }
    
    // ajusta a posicao para a velocidade atual
    private void adjustPosition() {
        row = row - (row%speed);
        col = col - (col%speed);
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
