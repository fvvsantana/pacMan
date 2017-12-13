package view.fruits;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import utils.Position;

public class FruitView {
    private final ImageView img;

    public FruitView(String url) {
        img = new ImageView(url);
        img.setVisible(false);
    }
    
    public FruitView(String url, double width, double height) {
        img = new ImageView(url);
        img.setFitWidth(width);
        img.setFitHeight(height);
    }
    
    public boolean isVisible() {
        return img.isVisible();
    }
    
    public void show() {
        img.setVisible(true);
    }
    
    public void hide() {
        img.setVisible(false);
    }
    
    public Position getPosition() {
        return new Position(img.getX(), img.getY());
    }

    public void setCellPosition(Position position) {
        img.setX(position.getX());
        img.setY(position.getY());
    }

    public void setSize(double cellWidth, double cellHeight) {
        img.setFitWidth(cellWidth);
        img.setFitHeight(cellHeight);
    }
    
    public Node getNode() {
        return img;
    }
}
