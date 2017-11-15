package model.characters;

public class OrangeGhostModel extends CharacterModel{
    boolean eatable;
    
    public OrangeGhostModel(double row, double col) {
        super(row, col);
        eatable = false;
    }


    public void setEatable (boolean eatable){
        this.eatable = eatable;
    }
    
}