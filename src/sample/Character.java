package sample;

import javafx.scene.image.Image;

public abstract class Character extends Interactive{
    private String imgURL;
    private int hitPoints;
    private Image img;



    public Character(int posX, int posY, String imgURL, int hitPoints) {
        super(posX, posY);
        this.hitPoints = hitPoints;
        this.imgURL = imgURL;
        this.img = new Image(this.getImgURL());
    }

    public int getHitPoints() { return hitPoints; }

    public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public Image getFullImage(){
        return img;
    }
}
