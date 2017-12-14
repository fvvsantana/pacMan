package menu.view.itens;

import javafx.scene.image.ImageView;

public abstract class MenuItemView {
    private ImageView img;
    private int stage;

    public MenuItemView(int stageNumber){
        stage = stageNumber;
        img = new ImageView("images/" +Integer.toString(stageNumber) + ".png");
        img.setFitHeight(275);
        img.setFitWidth(250);
    }

    public ImageView getImg() {
        return img;
    }
    
    public int getStage() {
        return stage;
    }
    
}
