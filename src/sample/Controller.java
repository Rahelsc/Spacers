package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    double imgX,imgY = 0;

    private ObservableList<KeyCode> keys = FXCollections.observableArrayList();

    public void Fire(){
        fired=true;
        Timeline tm = new Timeline(new KeyFrame(Duration.millis(30),this::shotStep));
        tm.setCycleCount(Timeline.INDEFINITE);
        tm.play();

    }

    public void shotStep(ActionEvent e){
        System.out.println(keys);
        for (int i =1 ; i<Border.getChildren().size();i++) {
            Border.getChildren().get(i).setTranslateY(Border.getChildren().get(i).getTranslateY() - 5);
            if(Border.getChildren().get(i).getTranslateY()<=-300){
                Border.getChildren().remove(Border.getChildren().get(i));
            }
        }
    }



    @FXML
    public void ManageMovment(KeyEvent e) throws InterruptedException {
        if (!keys.contains(e.getCode())) {
            keys.add(e.getCode());
        }
        Border.setOnKeyReleased((event) -> {
            keys.remove(event.getCode());
        });
        System.out.println(keys);
        //moving smoothie with timeline (20 per 150 milis)
        //nasty conditions for set borders of the screen
        Timeline act = new Timeline(new KeyFrame(Duration.millis(150),(somth)->{
            if (keys.contains(KeyCode.LEFT)&&keys.contains(KeyCode.UP)) {
                  if(imgX>-450)
                      imgX-=1;
                  if(imgY>-250)
                      imgY-=1;
            }
            else if (keys.contains(KeyCode.RIGHT)&&keys.contains(KeyCode.UP)) {
                  if(imgX<450)
                      imgX+=1;
                  if(imgY>-250)
                      imgY-=1;
            } else if (keys.contains(KeyCode.RIGHT)&&keys.contains(KeyCode.DOWN)) {
                  if(imgY<250)
                      imgY+=1;
                  if(imgX<450)
                      imgX+=1;
            } else if (keys.contains(KeyCode.LEFT)&&keys.contains(KeyCode.DOWN)) {
                  if(imgY<250)
                      imgY+=1;
                  if(imgX>-450)
                      imgX-=1;
            }
            else if (keys.contains(KeyCode.RIGHT) && imgX<450)
                      imgX+=1;
            else if (keys.contains(KeyCode.LEFT) && imgX>-450)
                      imgX-=1;
            else if (keys.contains(KeyCode.UP) && imgY>-250)
                      imgY-=1;
            else if (keys.contains(KeyCode.DOWN) && imgY<250)
                      imgY+=1;
            SpaceShip.setTranslateX(imgX);
            SpaceShip.setTranslateY(imgY);

        }));
        act.setCycleCount(20);
        act.play();

        //shoot
        if (keys.contains(KeyCode.SPACE)) {
            Timer timer = new java.util.Timer();
            //delay the shooting by 300 milli sec
            if(allowShooting) {
                timer.schedule(new TimerTask() {
                    public void run() {
                        //nasty anonymous class cause of javaFX issue with Timer
                        Platform.runLater(new Runnable() {
                            public void run() {
                                Circle shot = new Circle(3, Color.YELLOW);
                                if (!fired)
                                    Fire();
                                shot.setStroke(Color.RED);
                                shot.setStrokeWidth(2.0);
                                Border.getChildren().add(shot);
                                shot.setTranslateX(imgX + 47);
                                shot.setTranslateY(imgY - 50);
                                allowShooting=true;
                            }
                        });
                    }
                }, 300);
            }
            allowShooting=false;
        }

    }

}