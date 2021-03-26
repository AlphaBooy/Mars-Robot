package combat;

import combat.map.CombatMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import combat.map.*;
import combat.robot.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicInteger;

public class Display extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static GridPane pane = new GridPane();
    public static CombatMap map = CombatMap.getInstance();
    public static int block_size = 25;

    public static int STAGE_HEIGHT = 800;
    public static int STAGE_WIDTH = 800;

    /**
     * Display a texture depending of the name of the map object found in the map matrix.
     * All textures are stored within the textures/ folder and are named after the configuration file "measure_1.txt"
     * The texture generated will be displayed at a position given when calling the method on the gridpane
     * @param x the position of the object on the X axis
     * @param y the position of the object on the Y axis
     */
    public static void generateTexture(int x, int y) {
        try {
            String charTexture = "";
            switch (map.getChar(x,y)){
                case '@':
                    charTexture = "robot";
                    break;
                case ' ':
                    charTexture = "empty";
                    break;
                case '#':
                    charTexture = "wall";
                    break;
                case '%':
                    charTexture = "battery";

            }
            /* Create an image with the given texture for each position of each representations */
            Image image = new Image(new FileInputStream("textures/combat/" + charTexture + ".png"));
            ImageView iv = new ImageView(image);
            /* Set the size of each parcel to it feet a good looking view. */
            iv.setFitHeight(block_size);
            iv.setFitWidth(block_size);
            pane.add(iv, x, y);
        } catch (FileNotFoundException e) {
            System.err.println("WARNING: You've given a bad texture file/name. A default texture as" +
                    "been displayed but please change it as soon as possible to avoid further display issues.");
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Objectif Mars");
        char[][] charMap = map.getMap();
        /* Create a Map with the default map representation file */
        int sizeX = map.getSizeX();
        int sizeY = map.getSizeY();

        this.block_size = STAGE_HEIGHT / sizeY;

        /* For each char in the map char representation : */
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                /* Add each images on the given x:y position in the pane */
                generateTexture(x,y);
            }
        }

        BorderPane borderPane = new BorderPane(pane);
        /* Add the GridPane into the scene panel */
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        //primaryStage.setFullScreen(true);
        primaryStage.setWidth(STAGE_WIDTH);
        primaryStage.setHeight(STAGE_HEIGHT);
        /* Display the scene on the primary stage  */
        primaryStage.show();
    }
}
