package sample;

import javafx.geometry.Rectangle2D;

public abstract class Interactive {
    private double posX;
    private double posY;
    private double height;
    private double width;
    private int speed;


    public Interactive(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public Interactive(){

    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(getPosX(),getPosY(),getWidth(),getHeight());
    }

    public boolean intersects(Interactive s)
    {
        return s.getBoundary().intersects(this.getBoundary());
    }
}
