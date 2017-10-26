package view.characters;

import javafx.scene.image.ImageView;
import utils.Position;

public abstract class CharacterView {
    ImageView img;
    
    public CharacterView (String imagePath) {
        img = new ImageView(imagePath);
    }

    public ImageView getImg() {
        return img;
    }
    
    public void setPosition(Position position){
        img.setX(position.getX());
        img.setY(position.getY());
    }
}
