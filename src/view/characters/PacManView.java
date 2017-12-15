package view.characters;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import utils.Orientation;
import utils.Position;
import utils.Updatable;

public class PacManView implements Updatable {
    
    // tempo da animacao de morte
    private static final long DEATH_DURATION = 1_500_000_000L;
    
    // arco que representa o pacman
    private final Arc arc;
    
    private final double radiusX;
    private final double radiusY;
    private boolean opening;
    private boolean moving;
    private long deathTime;
    private boolean dead;
    private Position position;
    
    // multiplicador de tamanho para o pacman ser maior que uma celula
    private static final double SIZE_MULTIPLIER = 1.2;

    public PacManView(double cellWidth, double cellHeight) {
        // inicia o arco
        arc = new Arc();
        arc.setType(ArcType.ROUND);   
        arc.setFill(Color.YELLOW);
        arc.setStartAngle(40);
        arc.setLength(280);
        
        this.radiusX = (cellWidth * SIZE_MULTIPLIER) / 2;
        this.radiusY = (cellHeight * SIZE_MULTIPLIER) / 2;
        moving = false;
        dead = false;
        position = new Position(0, 0);
    }
    
    public void reset() {
        arc.setStartAngle(40);
        arc.setLength(280);
        dead = false;
    }
    
    // consider the position inside a map cell
    public void setCellPosition (Position position) {
        arc.setCenterX(position.getX() + radiusX * (1-(SIZE_MULTIPLIER-1)/2));
        arc.setCenterY(position.getY() + radiusY * (1-(SIZE_MULTIPLIER-1)/2));
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

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    // atualiza a abertura da boca do pacman
    @Override
    public void update() {
        if (!dead) {
            if (moving) {
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
        } else {
            if (deathTime < 100_000_000) {
                position.setX(arc.getCenterX());
                position.setY((arc.getCenterY()));
            } else {
                arc.setCenterX(position.getX()+deathTime*(radiusX/2)/DEATH_DURATION);
                arc.setCenterY(position.getY()+deathTime*(radiusY/2)/DEATH_DURATION);
            }
            double start = deathTime*180/DEATH_DURATION;
            if (start > 180)
                start = 180;
            double length = 360 - deathTime*360/DEATH_DURATION;
            if (length < 0)
                length = 0;
            arc.setStartAngle(start);
            arc.setLength(length);
        }
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setDeathTime(long deathTime) {
        this.deathTime = deathTime;
    }
}
