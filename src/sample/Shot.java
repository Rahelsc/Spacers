package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Shot extends Interactive {
    Circle c;

    public Shot(int radius, Color fillColor) {
        c = new Circle(radius, fillColor);
        c.setStroke(Color.RED);
        c.setStrokeWidth(2.0);
    }

    public void updatePosition(double x, double y){
        this.c.setTranslateX(x);
        this.c.setTranslateY(y);
    }

    public Circle getC() {
        return c;
    }
}
