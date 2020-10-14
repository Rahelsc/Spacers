package sample;

public class Character extends Interactive{
    private int hitPoints;


    public Character(int posX, int posY, String imgURL, int hitPoints) {
        super(posX, posY, imgURL);
        this.hitPoints = hitPoints;
    }

    public int getHitPoints() { return hitPoints; }

    public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }
}
