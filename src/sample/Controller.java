package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;


public class Controller {
    @FXML
    GridPane Border;
    //flag to call Fire() func only once
    boolean fired = false;
    //delay shooting flag
    boolean allowShooting = true;

    @FXML
    ImageView SpaceShip;
    double imgX, imgY = 0;

    Timeline act;

//    @FXML
//    Canvas enemy;
//
//    Invader alien = Invader.createInvader();

    private ObservableList<KeyCode> keys = FXCollections.observableArrayList();

    public void Fire() {
        fired = true;
        Timeline tm = new Timeline(new KeyFrame(Duration.millis(30), this::shotStep));
        tm.setCycleCount(Timeline.INDEFINITE);
        tm.play();
        if (Border.getChildren().size() < 2) {
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

    public void removeKey(KeyEvent event) {
        System.out.println("YYYYYYYYY");
        keys.remove(event.getCode());
    }

    @FXML
    public void ManageMovment(KeyEvent e) throws InterruptedException {

//        enemy.getGraphicsContext2D().drawImage(new Image("file:Images/attacker.jpeg"), 50, 50);
        if (!keys.contains(e.getCode())) {
            keys.add(e.getCode());
        }
        Border.setOnKeyReleased((event) -> {
            removeKey(event);
        });
        System.out.println(keys);
        //moving smoothie with timeline (20 per 150 milis)
        //nasty conditions for set borders of the screen
        act = new Timeline(new KeyFrame(Duration.millis(50), (somth) -> {
            if (keys.contains(KeyCode.LEFT) && keys.contains(KeyCode.UP)) {
                if (imgX > -450)
                    imgX -= 1;
                if (imgY > -250)
                    imgY -= 1;
            } else if (keys.contains(KeyCode.RIGHT) && keys.contains(KeyCode.UP)) {
                if (imgX < 450)
                    imgX += 1;
                if (imgY > -250)
                    imgY -= 1;
            } else if (keys.contains(KeyCode.RIGHT) && keys.contains(KeyCode.DOWN)) {
                if (imgY < 250)
                    imgY += 1;
                if (imgX < 450)
                    imgX += 1;
            } else if (keys.contains(KeyCode.LEFT) && keys.contains(KeyCode.DOWN)) {
                if (imgY < 250)
                    imgY += 1;
                if (imgX > -450)
                    imgX -= 1;
            } else if (keys.contains(KeyCode.RIGHT) && imgX < 450)
                imgX += 1;
            else if (keys.contains(KeyCode.LEFT) && imgX > -450)
                imgX -= 1;
            else if (keys.contains(KeyCode.UP) && imgY > -250)
                imgY -= 1;
            else if (keys.contains(KeyCode.DOWN) && imgY < 250)
                imgY += 1;
            SpaceShip.setTranslateX(imgX);
            SpaceShip.setTranslateY(imgY);
            act.playFromStart();
        }));
        act.setCycleCount(25);
        act.play();

        //shoot
        if (keys.contains(KeyCode.SPACE)) {
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
                }, 300);
            }
            allowShooting = false;
        }

    }

}