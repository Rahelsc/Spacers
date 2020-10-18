package sample;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Circle circle = new Circle(50.0f, Color.RED);
    private double newY, newX = 0;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Scene scene = new Scene(root, 1000, 600);
        scene.getRoot().requestFocus();
        primaryStage.setTitle("Spacers"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
