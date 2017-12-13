package view.lives;

import javafx.scene.image.ImageView;
import view.fruit.FruitView;

public class liveView {
    private final ImageView img;

    public liveView() {
        this.img = new ImageView("/images/pacmanLife.png");
        img.setFitHeight(FruitView.BOTTOM_SIZE - 2);
        img.setFitWidth(FruitView.BOTTOM_SIZE - 2);
    }
    
    public ImageView getImg(){
        return img;
    }
    
}
