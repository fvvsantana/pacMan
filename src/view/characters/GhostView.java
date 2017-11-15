package view.characters;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import utils.Position;
import utils.Updatable;

public abstract class GhostView implements Updatable {
    private final ImageView img;
    private final Image image1;
    private final Image image2;
    
    // contador para atualizar a imagem
    private int counter = 0;
    // numero de atualizações que deve esperar para trocar a imagem
    private static final int INTERVAL = 5;
    
    public GhostView (String imagePath1, String imagePath2){
        image1 = new Image (imagePath1);
        image2 = new Image (imagePath2);
        img = new ImageView(imagePath1);
        
    }

    @Override
    public void update() {
        if (counter == INTERVAL) {
            img.setImage((image2));
        } else if (counter == INTERVAL*2) {
            img.setImage(image1);
            counter = 0;
        }
        counter++;
    }
     
    public void setImage (Image image){
        img.setImage(image);
    }

    public ImageView getImg() {
        return img;
    }
    
    public void setPosition(Position position){
        img.setX(position.getX());
        img.setY(position.getY());
    }
    
}
