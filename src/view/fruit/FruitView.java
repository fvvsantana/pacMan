package view.fruit;

import javafx.scene.image.ImageView;

public abstract class FruitView{
    
    public static int BOTTOM_SIZE = 30;
    private final ImageView img;
    
    public FruitView(String url){
        img = new ImageView(url);
        img.setFitHeight(BOTTOM_SIZE);
        img.setFitWidth(BOTTOM_SIZE);
    }
    
    public ImageView getImg(){
        return img;
    }
    
}
