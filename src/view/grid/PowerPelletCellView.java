package view.grid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import utils.Position;

public class PowerPelletCellView extends CellView{
    
    private final Ellipse ellipse;
    
    // proporcao do tamanho da celula que o raio devera ocupar
    private static final int PROPORCAO_MIN = 6;
    private static final int PROPORCAO_MAX = 4;
    
    // pixels variados por atualizacao do raio
    private static final double VAR = 0.01;
    
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
}
