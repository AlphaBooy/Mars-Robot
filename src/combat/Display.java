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
import java.util.ArrayList;


public class Display extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static GridPane pane = new GridPane();
    public static CombatMap map = CombatMap.getInstance();
    public static int block_size = 25;

    public static int STAGE_HEIGHT = 1080; //The height of the window
    public static int STAGE_WIDTH = 1920; //The width of the window
    public static int WAIT_BETWEEN_ACTIONS = 1000; //The amount of time in milliseconds waited between every actions

    /**
     * Display a texture depending of the name of the map object found in the map matrix.
     * All textures are stored within the textures/ folder and are named after the configuration file "measure_1.txt"
     * The texture generated will be displayed at a position given when calling the method on the gridpane
     * @param x the position of the object on the X axis
     * @param y the position of the object on the Y axis
     */
    public static void generateTexture(int x, int y) {
        String path = getPathFromPos(x,y);
        try {
            /* Create an image with the given texture for each position of each representations */
            Image image = new Image(new FileInputStream(path));
            ImageView iv = new ImageView(image);
            /* Set the size of each parcel to it feet a good looking view. */
            iv.setFitHeight(block_size);
            iv.setFitWidth(block_size);
            pane.add(iv, x, y);
        } catch (FileNotFoundException e) {
            System.err.println("WARNING: You've given a bad texture file/name : " + path + ". A default texture as" +
                    "been displayed but please change it as soon as possible to avoid further display issues.");
        }
    }

    /**
     * Give the path to the resource file corresponding to the given coordinate depending on the character and
     * the battery in the case where the char is a robot
     * @param x posX
     * @param y poY
     * @return a path to the resource file
     */
    private static String getPathFromPos(int x, int y){
        String fileName = "";
        switch (map.getChar(x,y)){
            case '@':
                fileName = "robot";
                break;
            case ' ':
                fileName = "empty";
                break;
            case '#':
                fileName = "wall";
                break;
            case '%':
                fileName = "battery";
        }
        if(fileName == "robot"){
            try {
                fileName += map.getRobot(x,y).getEnergy();
            }catch(IsNotARobotException e){
                System.err.println("Error : A robot was expected " + e);
            }
        }
        return "textures/combat/" + fileName + ".png";
    }

    /**
     * Run through all the available commands and execute the commands, while more than one robot is alive
     */
    public static void makeActions() {
        Runnable task = () -> {
            ArrayList<Robot> rbs = map.getRobots();
            int i = 0;
            while (rbs.size() > 1 && i < (map).getLongestCommand()) {
                performActions(rbs);
                waitAMoment();
                rbs = map.getRobots();
                i++;
            }
            System.out.println("The game is over, there are "+ rbs.size() + " robots still alive :");
            if(rbs.size() > 0){
                for(Robot rb : rbs){
                    System.out.println("    -" + rb.getName());
                }
            }else{
                System.out.println("There is no winner, everyone DIED");
            }
        };
        /* Start the new thread */
        new Thread(task).start();
    }

    /**
     * Perform the actions for all the robots, need to be synchronized because each action happen after the previous one
     * @param rbs list containing the robots
     */
    private static synchronized void performActions(ArrayList<Robot> rbs){
        //Actually execute the commands of all the robots
        for(int i = 0; i < rbs.size();i++){
            rbs.get(i).executeCommand();
        }
    }

    /**
     * Wait for a certain amount of time before the next action to allow the user to see changes in the ui
     */
    private static void waitAMoment(){
        //We wait for a certain amount of time before performing another action
        try {
            Thread.sleep(WAIT_BETWEEN_ACTIONS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update in a dedicated thread a single element of the UI grid
     * @param x
     * @param y
     */
    public static void updateElement(int x, int y){
        //The following code is run after the action is performed and update the Graphical Interface
        Platform.runLater(() -> {
            generateTexture(x,y);
        });
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Combat de Robots");
        char[][] charMap = map.getMap();
        /* Create a Map with the default map representation file */
        int sizeX = map.getSizeX();
        int sizeY = map.getSizeY();

        if(STAGE_HEIGHT / sizeY < STAGE_WIDTH / sizeX)
            this.block_size = STAGE_HEIGHT / sizeY;
        else
            this.block_size = STAGE_WIDTH / sizeX;

        /* For each char in the map char representation : */
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                /* Add each images on the given x:y position in the pane */
                generateTexture(x,y);
            }
        }

        //Run the thread that will perform actions and update the GUI
        makeActions();

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
