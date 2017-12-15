package view.characters;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import utils.GameState;
import utils.Orientation;
import utils.Position;
import utils.Updatable;

public class PacManView implements Updatable {
    
    // tempo da animacao de morte
    private static final long DEATH_DURATION = 1_500_000_000L;
    private static final long WIN1 = 1_220_000_000L;
    private static final long WIN_DURATION = 5_000_000_000L;
    
    // arco que representa o pacman
    private final Arc arc;
    
    private final double radiusX;
    private final double radiusY;
    private boolean opening;
    private boolean moving;
    private long animationTime;
    private Position position;
    private GameState gameState;
    private double rotation;
    
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
        gameState = GameState.RUNNING;
        position = new Position(0, 0);
    }
    
    public void reset() {
        arc.setStartAngle(40);
        arc.setLength(280);
        gameState = GameState.RUNNING;
    }
    
    // consider the position inside a map cell
    public void setCellPosition (Position position) {
        arc.setCenterX(position.getX() + radiusX * (1-(SIZE_MULTIPLIER-1)/2));
        arc.setCenterY(position.getY() + radiusY * (1-(SIZE_MULTIPLIER-1)/2));
    }

    //set image orientation
    public void setOrientation(Orientation orientation){
        if (gameState == GameState.DEAD)
            return;
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
        switch (gameState) {
            case DEAD:
                arc.setCenterX(position.getX()+animationTime*(radiusX/2)/DEATH_DURATION);
                arc.setCenterY(position.getY()+animationTime*(radiusY/2)/DEATH_DURATION);
                double start = animationTime*180/DEATH_DURATION;
                if (start > 180)
                    start = 180;
                double length = 360 - animationTime*360/DEATH_DURATION;
                if (length < 0)
                    length = 0;
                arc.setStartAngle(start);
                arc.setLength(length);
                break;
            case WIN:
                // verifica se deve estar abrindo ou fechando
                if (arc.getStartAngle() <= 0) {
                    opening = true;
                } else if (arc.getStartAngle() >= 40) {
                    opening = false;
                }   // realiza o movimento
                if (opening) {
                    arc.setStartAngle(arc.getStartAngle() + 2);
                    arc.setLength(arc.getLength() - 4);
                } else {
                    arc.setStartAngle(arc.getStartAngle() - 2);
                    arc.setLength(arc.getLength() + 4);
                }
                if (animationTime < WIN1) {
                    arc.setRadiusX(radiusX+(animationTime*radiusX*3)/WIN1);
                    arc.setRadiusY(radiusY+(animationTime*radiusY*3)/WIN1);
                    arc.setRotate(rotation += 5);
                } else if (animationTime < 2*WIN1) {
                    arc.setRadiusX(4*radiusX-((animationTime-WIN1)*radiusX*3)/WIN1);
                    arc.setRadiusY(4*radiusY-((animationTime-WIN1)*radiusY*3)/WIN1);
                    arc.setRotate(rotation -= 5);
                } else if (animationTime < 3*WIN1) {
                    arc.setRadiusX(radiusX+((animationTime-WIN1*2)*radiusX*3)/WIN1);
                    arc.setRadiusY(radiusY+((animationTime-WIN1*2)*radiusY*3)/WIN1);
                    arc.setRotate(rotation += 5);
                } else {
                    arc.setRadiusX(4*radiusX-((animationTime-WIN1*3)*radiusX*4)/WIN1);
                    arc.setRadiusY(4*radiusY-((animationTime-WIN1*3)*radiusY*4)/WIN1);
                    arc.setRotate(rotation -= 5);
                }
                break;
            default:
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
                break;
        }
    }

    public void setGameState(GameState gameState) {
        if (gameState != this.gameState && gameState == GameState.WIN)
            rotation = arc.getRotate();
        else if (gameState != this.gameState && gameState == GameState.DEAD) {
            position.setX(arc.getCenterX());
            position.setY((arc.getCenterY()));
        }
        this.gameState = gameState;
    }

    public void setAnimationTime(long animationTime) {
        this.animationTime = animationTime;
    }
}
