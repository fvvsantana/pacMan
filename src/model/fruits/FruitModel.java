package model.fruits;

import java.io.Serializable;

public class FruitModel implements Serializable {
    
    private int counter = 0;
    private final int spawnTime;
    private final int lifeTime;
    
    private int x;
    private int y;

    public FruitModel(int spawnTime, int lifeTime) {
        this.spawnTime = spawnTime;
        this.lifeTime = lifeTime;
    }
    
    public void reset() {
        counter = 0;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
    
    public boolean isVisible() {
        return counter < 0;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public int getSpawnTime() {
        return spawnTime;
    }
    
    
}