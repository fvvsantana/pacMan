package model.fruits;

public class StrawberryModel extends FruitModel{

    // tempo para o morango aparecer é 60 * 75 = 4500 frames
    private static final int SPAWN_TIME = 300;
    
    // tempo que o morango fica na tela é 60 * 15 = 900 frames
    private static final int LIFE_TIME = 300;
    
    public StrawberryModel() {
        super(SPAWN_TIME, LIFE_TIME);
    }
}