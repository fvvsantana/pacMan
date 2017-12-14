package menu.itens;

public abstract class MenuItemModel {
    private int stageNumber;
    
    public MenuItemModel(int stageNumber){
        this.stageNumber = stageNumber;
    }
    
    public int getStageNumber(){
        return stageNumber;
    }
}
