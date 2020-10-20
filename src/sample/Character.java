package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Character extends Interactive{
    private int hitPoints;
    private ImageView imageView;

    public Character(double posX, double posY, ImageView imageView) {
        super(posX, posY);
        this.hitPoints = 3; // default life. can be defined individually using the setter
        this.imageView = imageView;
        setHeight(this.imageView.getFitHeight());
        setWidth(this.imageView.getFitWidth());
    }

    public int getHitPoints() { return hitPoints; }

    public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImage(Image img) {
        this.imageView.setImage(img);
    }

    public void updatePosition(double x, double y){ // updates the graphics of the class
        if(x>-500&&x<500&&y>-300&&y<300){
            this.getImageView().setTranslateX(x);
            this.getImageView().setTranslateY(y);
            upadteXY(); // call to update class coordinates
        }
    }

    private void upadteXY(){ // updates the class coordinates
        this.setPosX(this.getImageView().getTranslateX());
        this.setPosY(this.getImageView().getTranslateY());
    }
}
