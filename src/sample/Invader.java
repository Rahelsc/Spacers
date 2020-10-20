package sample;

import javafx.beans.NamedArg;
import javafx.scene.image.ImageView;

public class Invader extends Character {
    private Invader(double posX, double posY, ImageView imageView) {
        super(posX, posY, imageView);
        this.setHitPoints(1);
    }

    public static Invader makeInvader(ImageView imageView){
        return new Invader(imageView.getX(), imageView.getY(), imageView);
    }

    public void position(){ // randomizes start position of enemy
        double px;
        int random = (int)Math.round(Math.random() + 1);
        if (random == 1)
            px = 200 * Math.random() - 200 * Math.random();
        else
            px = 200 * Math.random() + 200 * Math.random();
        double py = -260 * Math.random();
        this.updatePosition(px, py);
    }

    public void movement(){
        int random = (int)Math.round(Math.random() + 1); // randomizes movement of enemies on the x axis
        if (random == 1)
            this.updatePosition(this.getPosX() - Math.random()*10, this.getPosY()+0.3);
        else if (random == 2)
            this.updatePosition(this.getPosX() + Math.random()*10, this.getPosY()+0.3);
        else
            this.updatePosition(this.getPosX(), this.getPosY()+0.3);
    }

}
