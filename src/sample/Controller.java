package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
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
    @FXML
    Label GameOver;
    @FXML
    HBox Hearts;
    //flag to call Fire() func only once
    boolean fired = false;
    //delay shooting flag
    boolean allowShooting = true;
    //moving flag
    boolean UP, DOWN, LEFT, RIGHT, SPACE = false;
    //Invaders moved flag
    boolean InvadersMoved = false;
    //List of enemys that killed
    ArrayList enemyKilled;

    @FXML
    ImageView spaceShip;
    Hero hero;

    @FXML
    AnchorPane attackFormation;
    ArrayList<Invader> invaders;
    ObservableList<Node> myEnemies;

    double imgX, imgY = 250;
    Timeline act;

    private ObservableList<Node> borderChildren;

    private ArrayList<Shot> shootings;

    public void initialize() {
        enemyKilled = new ArrayList<Invader>();
        Hearts.setTranslateY(-250);
        Hearts.setTranslateX(400);
        Border.getChildren().remove(GameOver);
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
        for (int i = 0; i < shootings.size(); i++) {

            if(shootings.get(i).getC().getFill().equals(Color.BLACK))
                shootings.get(i).updatePosition(shootings.get(i).getPosX(),shootings.get(i).getPosY()+3);
            else
            shootings.get(i).updatePosition(shootings.get(i).getPosX(),shootings.get(i).getPosY()-5);
            if (shootings.get(i).getPosY() <= -300) {
                Border.getChildren().remove(shootings.get(i).getC());
                shootings.remove(shootings.get(i));

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


    //check if enemy got shot and animate
    public void ObjectsInteract(){
        for (int i = 0; i < invaders.size(); i++) {
            for (int j = 0; j < shootings.size(); j++) {
                if (invaders.size() > 0 && myEnemies.size() > 0) {
                    try {
                        if (invaders.get(i).intersects(shootings.get(j))&&!shootings.get(j).getC().getFill().equals(Color.BLACK)) {
                            try {
                                invaders.get(i).setImage(new Image(getClass().getResourceAsStream("Images/Explosion.gif")));
                            } catch (Exception ex) {
                                System.out.println("not found");
                            }
                            enemyKilled.add(invaders.get(i));
                            invaders.remove(i);
                            Border.getChildren().remove(shootings.get(j).getC());
                            shootings.remove(shootings.get(j));
                            EnemyDeath();
                        }
                    }catch(IndexOutOfBoundsException ex){
                        System.out.println("out of index ?");
                    }


                }
                //if hero got hurt from shot
                if(shootings.get(j).intersects(hero)&&
                        shootings.get(j).getC().getFill().equals(Color.BLACK)){
                    if(Hearts.getChildren().size()>0)
                        Hearts.getChildren().remove(Hearts.getChildren().size()-1);
                    hero.setHitPoints(hero.getHitPoints()-1);
                    if(hero.getHitPoints()<=0){
                        hero.setImage(new Image(getClass().getResourceAsStream("Images/Explosion.gif")));
                        Border.setOnKeyPressed(e->{
                            System.out.println("game over");
                        });
                        if (!Border.getChildren().contains(GameOver))
                            Border.getChildren().add(GameOver);
                    }
                        Border.getChildren().remove(shootings.get(j).getC());
                        shootings.remove(shootings.get(j));
                }
            }
        }

    }
    //delay the explosion animate before delete Node
    public void EnemyDeath(){
        Timer timer = new java.util.Timer();
        //delay the shooting by 300 milli sec

        timer.schedule(new TimerTask() {
            public void run() {
                //nasty anonymous class cause of javaFX issue with Timer
                Platform.runLater(new Runnable() {
                    public void run() {
                        try{

                            for(int i=0 ; i<enemyKilled.size();i++){
                                myEnemies.remove(((Invader)enemyKilled.get(i)).getImageView());
                            }


                        }catch (IndexOutOfBoundsException ex){
                            System.out.println("end of the bad guys");
                        }
                    }
                });
            }
        },1500);
    }
    public void EnemyShooting(){
        Timer timer = new java.util.Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    //nasty anonymous class cause of javaFX issue with Timer
                    Platform.runLater(new Runnable() {
                        public void run() {
                            //randomize 3 enemies shooting each 3 seconds
                            int random1 = (int)(Math.random()*invaders.size());
                            int random2 = (int)(Math.random()*invaders.size());
                            int random3 = (int)(Math.random()*invaders.size());
                            for (int i=0;i<invaders.size();i++) {
                                if(i == random1||i==random2||i==random3) {
                                    Shot shot = new Shot(3, Color.BLACK);
                                    shootings.add(shot);
                                    shot.updatePosition(invaders.get(i).getPosX() + 40, invaders.get(i).getPosY() + 50);
                                    borderChildren.add(shot.getC());
                                }
                            }
                        }
                    });
                }
            }, 0,3000);


    }
    @FXML
    public void manageMovment() {
        if(InvadersMoved==false) {
            Timeline InvadersMove = new Timeline(new KeyFrame(Duration.millis(100), e -> {
                for (Invader invader : invaders) { // the invaders change positions if the hero moves
                    invader.movement();
                }
                ObjectsInteract();
                InvadersMoved=true;
            }));
            InvadersMove.setCycleCount(Timeline.INDEFINITE);
            InvadersMove.play();


        }

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
                    invaders.remove(invaders.get(j));
                    myEnemies.remove(j);
                }
            }
        }));
        act.setCycleCount(25);
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
                                if (!fired){
                                    Fire();
                                    EnemyShooting();
                                }

                                borderChildren.add(shot.getC());
                                shootings.add(shot);
                                shot.updatePosition(imgX+40, imgY - 50);
                                allowShooting = true;

                            }
                        });
                    }
                }, 80);
            }
            allowShooting = false;
        }

    }


}
