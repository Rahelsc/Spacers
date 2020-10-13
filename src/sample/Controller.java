package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class Controller {
    @FXML
    GridPane Border;

    boolean fired = false;

    @FXML
    ImageView SpaceShip;
    double imgX,imgY = 0;

    public void Fire(){
        fired=true;
        Timeline tm = new Timeline(new KeyFrame(Duration.millis(30),this::shotStep));
        tm.setCycleCount(Timeline.INDEFINITE);
        tm.play();

    }

    public void shotStep(ActionEvent e){
        for (int i =1 ; i<Border.getChildren().size();i++) {
            Border.getChildren().get(i).setTranslateY(Border.getChildren().get(i).getTranslateY() - 5);
            if(Border.getChildren().get(i).getTranslateY()<=-300){
                Border.getChildren().remove(Border.getChildren().get(i));
            }
        }
    }

    private ObservableList<KeyCode> keys = FXCollections.observableArrayList();

    @FXML
    public void ManageMovment(KeyEvent e){

        if (!keys.contains(e.getCode())) {
            keys.add(e.getCode());
        }
        Border.setOnKeyReleased((event) -> {
            keys.remove(event.getCode());
        });

        System.out.println(keys);
        Timeline act = new Timeline(new KeyFrame(Duration.millis(10),(somth)->{
              if (keys.contains(KeyCode.LEFT)&&keys.contains(KeyCode.UP)) {
                imgX-=1;imgY-=1;
                SpaceShip.setTranslateY(imgY);
                SpaceShip.setTranslateX(imgX);
            } else if (keys.contains(KeyCode.RIGHT)&&keys.contains(KeyCode.UP)) {
                imgY-=1;imgX+=1;
                SpaceShip.setTranslateX(imgX);
                SpaceShip.setTranslateY(imgY);
            } else if (keys.contains(KeyCode.RIGHT)&&keys.contains(KeyCode.DOWN)) {
                imgY+=1;imgX+=1;
                SpaceShip.setTranslateX(imgX);
                SpaceShip.setTranslateY(imgY);
            } else if (keys.contains(KeyCode.LEFT)&&keys.contains(KeyCode.DOWN)) {
                imgY+=1;imgX-=1;
                SpaceShip.setTranslateX(imgX);
                SpaceShip.setTranslateY(imgY);
            }
              else if (keys.contains(KeyCode.RIGHT)) {
                imgX+=1;
                SpaceShip.setTranslateX(imgX);
            } else if (keys.contains(KeyCode.LEFT)) {
                imgX-=1;
                SpaceShip.setTranslateX(imgX);
            } else if (keys.contains(KeyCode.UP)) {
                imgY-=1;
                SpaceShip.setTranslateY(imgY);
            } else if (keys.contains(KeyCode.DOWN)) {
                imgY+=1;
                SpaceShip.setTranslateY(imgY);
            }


        }));
        act.setCycleCount(20);
        act.play();

        if (e.getCode() == KeyCode.SPACE) {
            Circle shot = new Circle(3, Color.YELLOW);
            if (!fired)
                Fire();
            shot.setStroke(Color.RED);
            shot.setStrokeWidth(2.0);
            Border.getChildren().add(shot);
            shot.setTranslateX(imgX + 47);
            shot.setTranslateY(imgY - 50);
        }
    }

}