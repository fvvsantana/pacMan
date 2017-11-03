package model.characters;



public class CyanGhostModel extends CharacterModel{
      boolean eatable;
    
    public CyanGhostModel(double row, double col) {
        super(row, col);
        moving = true;
        eatable = false;
    }

    public void setEatable (boolean eatable){
        this.eatable = eatable;
    }
    
}