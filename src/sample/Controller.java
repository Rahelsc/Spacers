package sample;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.security.Key;
import java.util.Timer;
import java.util.TimerTask;


public class Controller {
    @FXML
    GridPane Border;
    //flag to call Fire() func only once
    boolean fired = false;
    //delay shooting flag
    boolean allowShooting = true;
    //moving flag
    boolean UP,DOWN,LEFT,RIGHT,SPACE = false;

    @FXML
    ImageView SpaceShip;
    double imgX, imgY = 0;

    Timeline act;

    private ObservableList<KeyCode> keys = FXCollections.observableArrayList();

    public void Fire() {
        fired = true;
        Timeline tm = new Timeline(new KeyFrame(Duration.millis(30), this::shotStep));
        tm.setCycleCount(Timeline.INDEFINITE);
        tm.play();
        if (Border.getChildren().size() < 1) {
            tm.stop();
            fired = false;
        }
    }

    public void shotStep(ActionEvent e) {
        for (int i = 1; i < Border.getChildren().size(); i++) {
            Border.getChildren().get(i).setTranslateY(Border.getChildren().get(i).getTranslateY() - 5);
            if (Border.getChildren().get(i).getTranslateY() <= -300) {
                Border.getChildren().remove(Border.getChildren().get(i));
            }
        }
    }
    public void turnOn(KeyCode action){
        switch (action){
            case LEFT:LEFT=true;
            break;
            case RIGHT:RIGHT=true;
            break;
            case UP: UP = true;
            break;
            case DOWN: DOWN = true;
            break;
            case SPACE:SPACE = true;
        }
    }
    public void turnOff(KeyCode action){
        switch (action){
            case LEFT:LEFT=false;
                break;
            case RIGHT:RIGHT=false;
                break;
            case UP: UP = false;
                break;
            case DOWN: DOWN = false;
                break;
            case SPACE:SPACE = false;
        }
    }

    @FXML
    public void removeKey(KeyEvent e) {
        turnOff(e.getCode());
        ManageMovment();

    }
    @FXML
    public void addKey(KeyEvent e) {
        turnOn(e.getCode());
        ManageMovment();
    }



    @FXML
    public void ManageMovment() {
        System.out.println(LEFT+" "+RIGHT);
        //moving smoothie with timeline (20 per 150 milis)
        //nasty conditions for set borders of the screen
        act = new Timeline(new KeyFrame(Duration.millis(100), (somth) -> {
            double width = (Border.getWidth()-100)/2;
            double height = (Border.getHeight()-100)/2;
            if (LEFT && UP) {
                if (imgX>width * -1)
                    imgX -= 1;
                if (imgY > height * -1)
                    imgY -= 1;
            } else if (RIGHT && UP) {
                if (imgX < width)
                    imgX += 1;
                if (imgY > height * -1)
                    imgY -= 1;
            } else if (RIGHT && DOWN ) {
                if (imgY < height)
                    imgY += 1;
                if (imgX < width)
                    imgX += 1;
            } else if (LEFT && DOWN ) {
                if (imgY < height)
                    imgY += 1;
                if (imgX>width * -1)
                    imgX -= 1;
            } else if (RIGHT && imgX < width)
                imgX += 1;
            else if (LEFT && imgX>width * -1)
                imgX -= 1;
            else if (UP && imgY > height * -1)
                imgY -= 1;
            else if (DOWN && imgY < height)
                imgY += 1;
            SpaceShip.setTranslateX(imgX);
            SpaceShip.setTranslateY(imgY);
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
                                Border.getChildren().add(shot.getC());
                                shot.updatePosition(imgX + 47, imgY - 50);
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