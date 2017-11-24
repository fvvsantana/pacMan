package model.characters;

import utils.GhostState;
import utils.Orientation;
import utils.Updatable;

public class GhostModel extends CharacterModel implements Updatable {
        
    // estado atual do fantasma
    private GhostState state;
    
    // tempo (em numero de frames) que deve permanecer comivel
    private int eatableTime = 0;
    
    public GhostModel() {
        super(1, 1);
    }

    public GhostState getState() {
        return state;
    }

    // altera o estado do fantasma, com algumas verificacoes
    public void setState(GhostState state) {
        
        // se jah tiver morrido, deve parar de contar o tempo
        if (state == GhostState.DEAD1)
            eatableTime = 0;
        
        // se esta como eatable, deve mudar o comportamento para running
        if (state == GhostState.NORMAL && eatableTime > 0)
            state = GhostState.RUNNING;
        
        // se acabou o poder, deve voltar para a velocidade normal
        if (state == GhostState.NORMAL || state == GhostState.DEAD1)
            setSpeed(STANDARD_SPEED);
        
        this.state = state;
    }
    
    public void startRunning() {
        if (state != GhostState.DEAD1 && state != GhostState.DEAD2) {
            setSpeed(STANDARD_SPEED/2);
            eatableTime = PacManModel.POWER_TIME;
            if (state != GhostState.START && state != GhostState.DEAD3)
                state = GhostState.RUNNING;
        }
    }
    
    public boolean isEatable() {
        return eatableTime > 0;
    }
    
    public boolean isAlive() {
        return state != GhostState.DEAD1 && state != GhostState.DEAD2;
    }
    
    public void reset() {
        state = GhostState.START;
        eatableTime = 0;
        setSpeed(STANDARD_SPEED);
        setOrientation(Orientation.UP);
    }
    
    public GhostState getViewState() {
        if (state == GhostState.DEAD1 || state == GhostState.DEAD2)
            return GhostState.DEAD1;
        else if (eatableTime > 0.2*PacManModel.POWER_TIME)
            return GhostState.RUNNING;
        else if (eatableTime > 0)
            return GhostState.RUNNING_END;
        else
            return GhostState.NORMAL;
    }

    @Override
    public void update() {
        
        if (isEatable())
            eatableTime--;
        
        if (eatableTime == 0 && (state == GhostState.RUNNING))
            setState(GhostState.NORMAL);
    }
    
}
