package view.characters;

import utils.Orientation;

public class PacManView extends CharacterView{

    public PacManView() {
        super("/images/pacman.png");
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
}
