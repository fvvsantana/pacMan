package view.grid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import utils.Position;
import utils.Updatable;

public class PowerPelletCellView extends CellView implements Updatable {
    
    private final Ellipse ellipse;
    
    // proporcao do tamanho da celula que o raio devera ocupar
    private static final int PROPORCAO_MIN = 6;
    private static final int PROPORCAO_MAX = 3;
    
    // pixels variados para cada atualizacao do raio
    private static final double VAR = 0.2;
    
    // guarda se esta aumentando ou diminuindo
    private boolean aumentando = false;
    
    // valores absolutos da celula:
    private Position cellPosition;
    private double cellWidth;
    private double cellHeight;
    
    public PowerPelletCellView() {
        ellipse = new Ellipse();
        ellipse.setFill(Color.WHITE);
        setNode(ellipse);
    }
    
    private void updateCenter() {
        ellipse.setCenterX(cellPosition.getX() + cellWidth/2);
        ellipse.setCenterY(cellPosition.getY() + cellHeight/2);
        ellipse.setRadiusX(cellWidth/PROPORCAO_MAX);
        ellipse.setRadiusY(cellHeight/PROPORCAO_MAX);
    }

    @Override
    public Position getPosition() {
        return cellPosition;
    }

    @Override
    public void setPosition(Position position) {
        cellPosition = position;
        updateCenter();
    }

    @Override
    public void setSize(double cellWidth, double cellHeight) {
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        updateCenter();
    }

    @Override
    public void update() {
        if (ellipse.getRadiusX() > cellWidth/PROPORCAO_MAX)
            aumentando = false;
        else if (ellipse.getRadiusX() < cellWidth/PROPORCAO_MIN)
            aumentando = true;
        
        if (aumentando) {
            ellipse.setRadiusX(ellipse.getRadiusX()+VAR);
            ellipse.setRadiusY(ellipse.getRadiusY()+VAR);
        } else {
            ellipse.setRadiusX(ellipse.getRadiusX()-VAR);
            ellipse.setRadiusY(ellipse.getRadiusY()-VAR);
        }
    }
}
