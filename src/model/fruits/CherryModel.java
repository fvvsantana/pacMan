package model.fruits;

public class CherryModel extends FruitModel{
    
    // tempo para a cereja aparecer é 60 * 50 = 3000 frames
    private static final int SPAWN_TIME = 3000;
    
    // tempo que a cereja fica na tela é 60 * 15 = 900 frames
    private static final int LIFE_TIME = 900;

    public CherryModel() {
        super(SPAWN_TIME, LIFE_TIME);
    }
}