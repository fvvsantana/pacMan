package view.fruits;

public class CherryView extends FruitView {

    public CherryView() {
        super("/images/cherry.png");
    }

    public CherryView(double width, double height) {
        super("/images/cherry.png", width, height);
    }
}
