package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import map.Map;
import robot.Robot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Objectif Mars");
        GridPane pane = new GridPane();

        /* Create a Map with the default map representation file */
        Map map = new Map();
        int sizeX = map.getSizeX();
        int sizeY = map.getSizeY();

        /* For each char in the map char representation : */
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                try {
                    /* Create an image with the given texture for each position of each representations */
                    Image image = new Image(new FileInputStream("textures/" + map.getObject(x, y).getName().toLowerCase() + ".png"));
                    ImageView iv = new ImageView(image);
                    /* Set the size of each parcel to it feet a good looking view. */
                    iv.setFitHeight(25);
                    iv.setFitWidth(25);
                    /* Add each images on the given x:y position in the pane */
                    pane.add(iv, x, y);
                } catch (FileNotFoundException e) {
                    System.err.println("WARNING: You've given a bad texture file/name. A default texture as" +
                            "been displayed but please change it as soon as possible to avoid further display issues.");
                }
            }
        }

        /* Add the robot to it's given position on the map */
        Robot robot = new Robot();

        Image robotImage = new Image(new FileInputStream(Robot.pathToRobotImage));
        ImageView robotView = new ImageView(robotImage);
        robotView.setFitHeight(25);
        robotView.setFitWidth(25);
        pane.add(robotView,robot.getPosX(), robot.getPosY());

        /* Add the GridPane into the scene panel */
        Scene scene = new Scene(pane);
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
