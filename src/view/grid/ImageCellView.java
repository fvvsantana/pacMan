package view.grid;

import javafx.scene.image.ImageView;
import utils.Position;

class ImageCellView extends CellView{
    private final ImageView img;

    public ImageCellView(String url) {
        img = new ImageView(url);
        setNode(img);
    }

    @Override
    public Position getPosition() {
        return new Position(img.getX(), img.getY());
    }

    @Override
    public void setPosition(Position position) {
        img.setX(position.getX());
        img.setY(position.getY());
    }

    @Override
    public void setSize(double cellWidth, double cellHeight) {
        img.setFitWidth(cellWidth);
        img.setFitHeight(cellHeight);
    }
}
