package view.characters;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import utils.Orientation;
import utils.Position;

public class PacManView {
    
    // arco que representa o pacman
    private final Arc arc;
    private final double radiusX;
    private final double radiusY;
    private boolean opening;

    public PacManView(double radiusX, double radiusY) {
        // inicia o arco
        arc = new Arc();
        arc.setType(ArcType.ROUND);   
        arc.setFill(Color.YELLOW);
        arc.setStartAngle(30);
        arc.setLength(300);
        
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }
    
    // atualiza a abertura da boca do pacman
    public void updateArc() {
        // verifica se deve estar abrindo ou fechando
        if (arc.getStartAngle() <= 0)
            opening = true;
        else if (arc.getStartAngle() >= 40)
            opening = false;
        
        // realiza o movimento
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
        arc.setCenterX(position.getX() + radiusX);
        arc.setCenterY(position.getY() + radiusY);
    }

    //set image orientation
    public void setOrientation(Orientation orientation){
        switch(orientation){
            case UP:
                arc.setRotate(-90);
                arc.setRadiusX(radiusY);
                arc.setRadiusY(radiusX);
                break;

            case RIGHT:
                arc.setRotate(0);
                arc.setRadiusX(radiusX);
                arc.setRadiusY(radiusY);
                break;

            case DOWN:
                arc.setRotate(90);
                arc.setRadiusX(radiusY);
                arc.setRadiusY(radiusX);
                break;

            case LEFT:
                arc.setRotate(180);
                arc.setRadiusX(radiusX);
                arc.setRadiusY(radiusY);
                break;

        }
    }

    public Arc getArc() {
        return arc;
    }
}
