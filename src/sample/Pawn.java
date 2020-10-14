package sample;

import javafx.scene.Node;

public class Pawn extends Node {
    private int posX;
    private int posY;
    private String imgURL;
    private int hitPoints;

    public Pawn(int posX, int posY, String imgURL,int hitPoints) {
        this.posX = posX;
        this.posY = posY;
        this.imgURL = imgURL;
        this.hitPoints = hitPoints;
    }

    public int getHitPoints() { return hitPoints; }

    public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }

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
