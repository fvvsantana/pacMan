package menu.view.itens;

import javafx.scene.image.ImageView;

public abstract class MenuItemView {
    private ImageView img;

    public MenuItemView(int stageNumber){
        img = new ImageView("images/" +Integer.toString(stageNumber) + ".png");
        img.setFitHeight(220);
        img.setFitWidth(200);
    }

    public ImageView getImg() {
        return img;
    }
    
}
