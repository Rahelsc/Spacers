package sample;

public class Character extends Interactive{
    private String imgURL;
    private int hitPoints;



    public Character(int posX, int posY, String imgURL, int hitPoints) {
        super(posX, posY);
        this.hitPoints = hitPoints;
        this.imgURL = imgURL;
    }

    public int getHitPoints() { return hitPoints; }

    public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
