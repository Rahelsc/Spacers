package sample;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;

public abstract class Interactive {
    private int posX;
    private int posY;


    public Interactive(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public Interactive(){

    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
