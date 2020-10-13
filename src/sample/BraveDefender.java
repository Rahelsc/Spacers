package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BraveDefender extends Sprite{
    public BraveDefender(String img) {
        Image m = new Image(img, 100, 100, true, true);
        this.setImage(m);
        this.setImageView();
    }

    @Override
    public void setPosition(double x, double y) {
        this.getImageView().setTranslateX(x);
        this.getImageView().setTranslateY(y);
    }
}
