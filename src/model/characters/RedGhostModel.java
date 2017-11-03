package model.characters;


public class RedGhostModel extends CharacterModel{
    boolean eatable;
    
    public RedGhostModel(double row, double col) {
        super(row, col);
        moving = true;
        eatable = false;
    }

    public void setEatable (boolean eatable){
        this.eatable = eatable;
    }
   
}
