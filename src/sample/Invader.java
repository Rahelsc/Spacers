package sample;

import javafx.beans.NamedArg;

public class Invader extends Character {
    private Invader(int posX, int posY, String imgURL, int hitPoints) {
        super(posX, posY, imgURL, hitPoints);
    }

    public static Invader createInvader(){
        return new Invader(50, 50, "file:/Images/attacker.jpeg", 0);
    }
}
