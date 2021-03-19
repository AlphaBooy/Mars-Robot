package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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


    public static GridPane pane = new GridPane();
    public static Map map = new Map();
    public static Robot robot = new Robot();

    public static void generateTexture(int x, int y) {
        try {
            /* Create an image with the given texture for each position of each representations */
            Image image = new Image(new FileInputStream("textures/" + map.getObject(x, y).getName().toLowerCase() + ".png"));
            ImageView iv = new ImageView(image);
            /* Set the size of each parcel to it feet a good looking view. */
            iv.setFitHeight(35);
            iv.setFitWidth(35);
            pane.add(iv, x, y);
        } catch (FileNotFoundException e) {
            System.err.println("WARNING: You've given a bad texture file/name. A default texture as" +
                    "been displayed but please change it as soon as possible to avoid further display issues.");
        }
    }

    public static void displayRobot(Robot robot) {
        try {
            Image robotImage = new Image(new FileInputStream(Robot.PATH_TO_IMAGE));
            ImageView robotView = new ImageView(robotImage);
            robotView.setFitHeight(35);
            robotView.setFitWidth(35);
            pane.add(robotView, robot.getPosX(), robot.getPosY());
        } catch (FileNotFoundException e) {
            System.err.println("WARNING: You've given a bad texture file/name for the robot. The program can't" +
                    "run without a visual representation of the robot so the program will stop.");
            System.exit(1);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Objectif Mars");

        /* Create a Map with the default map representation file */
        int sizeX = map.getSizeX();
        int sizeY = map.getSizeY();

        /* For each char in the map char representation : */
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                    /* Add each images on the given x:y position in the pane */
                    generateTexture(x,y);
            }
        }
        displayRobot(robot);

        Runnable task = () -> {
            int i = 5;
            while (i-- > 0) {
                Platform.runLater(() -> {
                    displayRobot(robot);
                });
                robot.move();
            }
        };
        new Thread(task).start();

        /* Add the GridPane into the scene panel */
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        /* Display the scene on the primary stage  */
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
