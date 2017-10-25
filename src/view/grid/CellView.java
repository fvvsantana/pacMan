package view.grid;

import javafx.scene.Node;
import utils.Position;

public abstract class CellView {
    //javafx entity
	private Node node;

    abstract public Position getPosition();

    abstract public void setPosition(Position position);

    abstract public void setSize(double cellWidth, double cellHeight);

    public final void setNode(Node node) {
        this.node = node;
    }

    public final Node getNode() {
        return node;
    }
}
