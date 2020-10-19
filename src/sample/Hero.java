package sample;

import javafx.scene.image.ImageView;

public class Hero extends Character {

    private Hero(double posX, double posY, ImageView imageView) {
        super(posX, posY, imageView);
    }

    public static Hero makeHero(double posX, double posY, ImageView imageView){
        return new Hero(posX, posY, imageView);
    }



}
