package model.characters;

public class GhostModel extends CharacterModel {
    
    public enum State {
        NORMAL, RUNNING, DEAD;
    }
    
    private State state;
    
    public GhostModel(double realRow, double realCol) {
        super(realRow, realCol);
        
        state = State.NORMAL;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    
}
