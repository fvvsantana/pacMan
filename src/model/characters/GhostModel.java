package model.characters;

import utils.GhostState;

public class GhostModel extends CharacterModel {
    
    private GhostState state;
    
    public GhostModel(double realRow, double realCol) {
        super(realRow, realCol);
        
        state = GhostState.NORMAL;
    }

    public GhostState getState() {
        return state;
    }

    public void setState(GhostState state) {
        this.state = state;
    }
    
}
