package view.lives;

import javafx.scene.image.ImageView;

public class liveView {
    private final ImageView img;

    public liveView(double width, double height) {
        this.img = new ImageView("/images/pacmanLife.png");
        img.setFitHeight(height);
        img.setFitWidth(width);
    }
    
    public ImageView getImg(){
        return img;
    }
    
}
