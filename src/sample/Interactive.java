package sample;

import javafx.scene.Node;

public class Interactive extends Node {
    private int posX;
    private int posY;
    private String imgURL;

    public Interactive(int posX, int posY, String imgURL) {
        this.posX = posX;
        this.posY = posY;
        this.imgURL = imgURL;
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

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
