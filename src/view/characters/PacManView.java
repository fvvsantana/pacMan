package view.characters;

import javafx.scene.image.ImageView;
import utils.Orientation;
import utils.Position;

public class PacManView{
    ImageView img;

    public PacManView() {
        img = new ImageView("/images/pacman.png");

    }

    //set image orientation
    public void setOrientation(Orientation orientation){
        switch(orientation){
            case UP:
                img.setRotate(-90);
                break;

            case RIGHT:
                img.setRotate(0);
                break;

            case DOWN:
                img.setRotate(90);
                break;

            case LEFT:
                img.setRotate(180);
                break;

        }
    }

    public ImageView getImg() {
        return img;
    }

    public void setPosition(Position position){
        img.setX(position.getX());
        img.setY(position.getY());
    }

}
