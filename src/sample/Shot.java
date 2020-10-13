package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Shot extends Sprite {
    private Circle circle;


    public Shot() {
        this.circle = new Circle(3, Color.YELLOW);
        this.circle.setStroke(Color.RED);
        this.circle.setStrokeWidth(2.0);
    }

    public Circle getCircle() {
        return circle;
    }

    @Override
    public void setPosition(double x, double y) {
        this.circle.setTranslateX(x);
        this.circle.setTranslateY(y);
    }
}
