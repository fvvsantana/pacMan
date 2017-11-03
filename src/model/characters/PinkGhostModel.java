package model.characters;



public class PinkGhostModel extends CharacterModel{
    boolean eatable;
    
    public PinkGhostModel(double row, double col) {
        super(row, col);
        moving = true;
        eatable = false;
    }

    public void setEatable (boolean eatable){
        this.eatable = eatable;
    }
    
}