package view.characters;

import java.util.Random;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import utils.Orientation;
import utils.Position;

public abstract class CharacterView {
    ImageView img;
    Image image1;
    Image image2;
    Random rand = new Random();
    int num;
    
    public CharacterView (String imagePath1, String imagePath2){
        image1 = new Image (imagePath1);
        image2 = new Image (imagePath2);
        img = new ImageView(imagePath1);
        
    }
     public void UpgradeImg(){
        
        num = rand.nextInt(1000)+1;
        if (num <= 40) setImage(image1);
        else setImage(image2);
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
