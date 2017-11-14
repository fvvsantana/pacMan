package view.grid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utils.Position;

public class PacDotCellView extends CellView {
    
    private final Rectangle rec;
    
    // proporcao do tamanho da celula que devera ocupar
    private static final int PROPORCAO = 5;
    
    // valores absolutos da celula:
    private Position cellPosition;
    private double cellWidth;
    private double cellHeight;
    
    public PacDotCellView() {
        rec = new Rectangle();
        rec.setFill(Color.WHITE);
        setNode(rec);
    }
    
    // atualiza o tamanho e a posicao reais do retangulo
    private void updateRectangle() {
        // deve ocupar uma proporcao da tela
        rec.setWidth(cellWidth / PROPORCAO);
        rec.setHeight(cellHeight / PROPORCAO);
        
        // deve estar centralizado na celula
        rec.setX(cellPosition.getX() + cellWidth/2 - cellWidth/(PROPORCAO*2));
        rec.setY(cellPosition.getY() + cellHeight/2 - cellHeight/(PROPORCAO*2));
    }

    @Override
    public Position getPosition() {
        return cellPosition;
    }

    @Override
    public void setPosition(Position position) {
        cellPosition = position;
        updateRectangle();
    }

    @Override
    public void setSize(double cellWidth, double cellHeight) {
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        updateRectangle();
    }
}
