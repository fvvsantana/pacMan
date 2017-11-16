package model.characters;

public class OrangeGhostModel extends CharacterModel{
   
    
    public OrangeGhostModel(double row, double col) {
        super(row, col);
        setEatable(false);
        setRunning(false);
    }
    
}