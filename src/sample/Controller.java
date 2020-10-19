package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//@Yoad:
// כל הקטע שהגיבור מתחיל מהאמצע? בעייתי. הגיבור צריך להתחיל מלטה כי האויבים יורדים אליו. הגדרה מחדש של זה
// בfxml דופקת הרבה דברים גם הקוד שלך, אז עזבתי את זה, אבל כדאי לטפל
// בעיקרון הגדרתי שהגיבור יתחיל מתחתית המסך בקוד אבל בגלל הfxml מיד שמתחילים בתזוזה הוא עולה לאמצע
// כששיניתי הגדרה זו בfxml התחילו בעיות, אז עזבתי... אשמח אם תבדוק את העניין
public class Controller {
    @FXML
    GridPane Border;
    //flag to call Fire() func only once
    boolean fired = false;
    //delay shooting flag
    boolean allowShooting = true;
    //moving flag
    boolean UP, DOWN, LEFT, RIGHT, SPACE = false;

    @FXML
    ImageView spaceShip;
    Hero hero;

    @FXML
    AnchorPane attackFormation;
    ArrayList<Invader> invaders;
    ObservableList<Node> myEnemies;

    double imgX, imgY = 0;
    Timeline act;

    private ObservableList<Node> borderChildren;

    private ObservableList<KeyCode> keys = FXCollections.observableArrayList();

    private ArrayList<Shot> shootings;

    public void initialize() {
        shootings = new ArrayList<>();
        borderChildren = Border.getChildren();
        hero = Hero.makeHero(spaceShip.getX(), spaceShip.getY(), spaceShip);
        myEnemies = attackFormation.getChildren();
        invaders = new ArrayList<>();
        int invaderCounter = 0;
        int counter = 0;
        while (counter < myEnemies.size()) {
            invaders.add(invaderCounter, Invader.makeInvader((ImageView) myEnemies.get(counter)));
            invaders.get(invaderCounter).position();//randomizes placement of enemy
            while (checkCollision(invaders.get(invaderCounter), invaders))
                invaders.get(invaderCounter).position();
            counter++;
            invaderCounter++;
        }
        hero.updatePosition(0, 250);
    }

    public boolean checkCollision(Invader i, ArrayList<Invader> invasion) {
        for (Invader invader : invasion)
            if (i != null && invader != null && i != invader && i.intersects(invader))
                return true;
        return false;
    }

    public void Fire() {
        fired = true;
        Timeline tm = new Timeline(new KeyFrame(Duration.millis(30), this::shotStep));
        tm.setCycleCount(Timeline.INDEFINITE);
        tm.play();
        if (borderChildren.size() < 1) {
            tm.stop();
            fired = false;
        }
    }

    public void shotStep(ActionEvent e) {
        for (int i = 1; i < borderChildren.size(); i++) {
            borderChildren.get(i).setTranslateY(borderChildren.get(i).getTranslateY() - 5);
            if (borderChildren.get(i).getTranslateY() <= -300) {
                borderChildren.remove(borderChildren.get(i));
            }
        }
    }

    public void turnOn(KeyCode action) {
        switch (action) {
            case LEFT:
                LEFT = true;
                break;
            case RIGHT:
                RIGHT = true;
                break;
            case UP:
                UP = true;
                break;
            case DOWN:
                DOWN = true;
                break;
            case SPACE:
                SPACE = true;
        }
    }

    public void turnOff(KeyCode action) {
        switch (action) {
            case LEFT:
                LEFT = false;
                break;
            case RIGHT:
                RIGHT = false;
                break;
            case UP:
                UP = false;
                break;
            case DOWN:
                DOWN = false;
                break;
            case SPACE:
                SPACE = false;
        }
    }

    @FXML
    public void removeKey(KeyEvent e) {
        turnOff(e.getCode());
        manageMovment();

    }

    @FXML
    public void addKey(KeyEvent e) {
        turnOn(e.getCode());
        manageMovment();
    }


    @FXML
    public void manageMovment() {
        for (Invader invader : invaders) { // the invaders change positions if the hero moves
            invader.movement();
        }
        System.out.println(hero.getPosY());

        System.out.println(LEFT + " " + RIGHT);
        //moving smoothie with timeline (20 per 150 milis)
        //nasty conditions for set borders of the screen
        act = new Timeline(new KeyFrame(Duration.millis(100), (somth) -> {
            double width = (Border.getWidth() - 100) / 2;
            double height = (Border.getHeight() - 100) / 2;
            if (LEFT && UP) {
                if (imgX > width * -1)
                    imgX -= 1;
                if (imgY > height * -1)
                    imgY -= 1;
            } else if (RIGHT && UP) {
                if (imgX < width)
                    imgX += 1;
                if (imgY > height * -1)
                    imgY -= 1;
            } else if (RIGHT && DOWN) {
                if (imgY < height)
                    imgY += 1;
                if (imgX < width)
                    imgX += 1;
            } else if (LEFT && DOWN) {
                if (imgY < height)
                    imgY += 1;
                if (imgX > width * -1)
                    imgX -= 1;
            } else if (RIGHT && imgX < width)
                imgX += 1;
            else if (LEFT && imgX > width * -1)
                imgX -= 1;
            else if (UP && imgY > height * -1)
                imgY -= 1;
            else if (DOWN && imgY < height)
                imgY += 1;
            hero.updatePosition(imgX, imgY);
            for (int j = 0; j < invaders.size(); j++) {
                if (invaders.get(j).intersects(hero)) {
                    //@Yoad - make something graphic happen when a hero gets a point of life taken
                    // default is that hero has 3 lives, invader has 1 life
                    System.out.println("size: " + myEnemies.size());
                    invaders.remove(invaders.get(j));
                    myEnemies.remove(j);
                }
            }
        }));
        act.setCycleCount(25);
        act.play();


        //shoot
        if (SPACE) {
            Timer timer = new java.util.Timer();
            //delay the shooting by 300 milli sec
            if (allowShooting) {
                timer.schedule(new TimerTask() {
                    public void run() {
                        //nasty anonymous class cause of javaFX issue with Timer
                        Platform.runLater(new Runnable() {
                            public void run() {
                                Shot shot = new Shot(3, Color.YELLOW);
                                if (!fired)
                                    Fire();
                                borderChildren.add(shot.getC());
                                shootings.add(shot);
                                shot.updatePosition(imgX + 47, imgY - 50);
                                allowShooting = true;
                                for (int i = 0; i < invaders.size(); i++) {
                                    for (int j = 0; j < shootings.size(); j++) {
                                        if (invaders.size()>0 && myEnemies.size()>0 && invaders.get(i)
                                                .intersects(shootings.get(j))){
                                            // @Yoad - add explosive graphics, for now the enemies just disappear
                                            // there is also a big delay till it happens.
                                            // I assume because this entire method has a delay
                                            // or because it's the wrong place to put this check.
                                            // can you fix it?
                                            // everything to do with animation I'm leaving to you :)
                                            invaders.remove(invaders.get(i));
                                            myEnemies.remove(i);
                                        }

                                    }
                                }
                            }
                        });
                    }
                }, 80);
            }
            allowShooting = false;
        }

    }

}
