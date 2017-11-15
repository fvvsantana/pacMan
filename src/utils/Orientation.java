package utils;

public enum Orientation{
    UP, RIGHT, DOWN, LEFT, NONE;
    
    private Orientation opposite;
    
    static {
        UP.opposite = DOWN;
        DOWN.opposite = UP;
        LEFT.opposite = RIGHT;
        RIGHT.opposite = LEFT;
    }
    
    public Orientation getOpposite () {
        return opposite;
    }
}