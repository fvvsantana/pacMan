package model.characters;

import utils.GhostState;
import utils.Updatable;

public class GhostModel extends CharacterModel implements Updatable {
    
    private static final int RUNNING_TIME = 600;
    
    private GhostState state;
    private int counter = 0;
    
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
    
    public void startRunning() {
        if (state != GhostState.DEAD) {
            state = GhostState.RUNNING;
            counter = 0;
        }
    }
    
    public boolean isEatable() {
        return state == GhostState.RUNNING || state == GhostState.RUNNING_END;
    }

    @Override
    public void update() {
        
        if (isEatable())
            counter++;
        
        if (state == GhostState.RUNNING && counter == RUNNING_TIME*0.8)
            state = GhostState.RUNNING_END;
        else if (state == GhostState.RUNNING_END && counter == RUNNING_TIME)
            state = GhostState.NORMAL;
    }
    
}
