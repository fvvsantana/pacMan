package view.characters;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import utils.Orientation;
import utils.Position;

public class PacManView {
    
    // arco que representa o pacman
    private final Arc arc;
    private boolean opening;

    public PacManView() {
        // inicia o arco
        arc = new Arc();
        arc.setType(ArcType.ROUND);   
        arc.setFill(Color.YELLOW);
        arc.setStartAngle(30);
        arc.setLength(300);
    }
    
    // atualiza a abertura da boca do pacman
    public void updateArc() {
        if (arc.getStartAngle() <= 0)
            opening = true;
        else if (arc.getStartAngle() >= 40)
            opening = false;
        
        if (opening) {
            arc.setStartAngle(arc.getStartAngle()+2);
            arc.setLength(arc.getLength()-4);
        } else {
            arc.setStartAngle(arc.getStartAngle()-2);
            arc.setLength(arc.getLength()+4);
        }
    }
    
    // define a posicao considerando o centro
    public void setPosition (Position position) {
        arc.setCenterX(position.getX() + arc.getRadiusX());
        arc.setCenterY(position.getY() + arc.getRadiusY());
    }

    //set image orientation
    public void setOrientation(Orientation orientation){
        switch(orientation){
            case UP:
                arc.setRotate(-90);
                break;

            case RIGHT:
                arc.setRotate(0);
                break;

            case DOWN:
                arc.setRotate(90);
                break;

            case LEFT:
                arc.setRotate(180);
                break;

        }
    }

    public Arc getArc() {
        return arc;
    }
}
