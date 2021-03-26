package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import map.Map;
import robot.Direction;
import robot.Material;
import robot.Robot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends Application {


    public static GridPane pane = new GridPane();
    public static GridPane infos = new GridPane();
    public static GridPane material = new GridPane();
    public static Map map = new Map();
    public static Robot robot = new Robot(map, "files/robot/config_1.txt");
    public static ProgressBar pi = new ProgressBar(0);

    public static final int BLOCK_SIZE = 25;

    /**
     * Display a texture depending of the name of the map object found in the map matrix.
     * All textures are stored within the textures/ folder and are named after the configuration file "measure_1.txt"
     * The texture generated will be displayed at a position given when calling the method on the gridpane
     * @param x the position of the object on the X axis
     * @param y the position of the object on the Y axis
     */
    public static void generateTexture(int x, int y) {
        try {
            /* Create an image with the given texture for each position of each representations */
            Image image = new Image(new FileInputStream("textures/" + map.getObject(x, y).getName().toLowerCase() + ".png"));
            ImageView iv = new ImageView(image);
            /* Set the size of each parcel to it feet a good looking view. */
            iv.setFitHeight(BLOCK_SIZE);
            iv.setFitWidth(BLOCK_SIZE);
            pane.add(iv, x, y);
        } catch (FileNotFoundException e) {
            System.err.println("WARNING: You've given a bad texture file/name. A default texture as" +
                    "been displayed but please change it as soon as possible to avoid further display issues.");
        }
    }

    /**
     * Display a robot on the map at it's positions x:y one the grid pane
     * @param robot
     */
    public static void displayRobot(Robot robot) {
        try {
            /* Get the image of the robot in the texture folder (position saved in the Robot Object */
            Image robotImage = new Image(new FileInputStream(Robot.PATH_TO_IMAGE));
            ImageView robotView = new ImageView(robotImage);
            /* Set the size of the robot to match the size of each map objects represented */
            robotView.setFitHeight(BLOCK_SIZE);
            robotView.setFitWidth(BLOCK_SIZE);
            /* Rotate the robot image depending of the position (by default the image is rotated to NORTH) */
            if (robot.getDirection() == Direction.EAST)
                robotView.setRotate(90);
            if (robot.getDirection() == Direction.SOUTH)
                robotView.setRotate(180);
            if (robot.getDirection() == Direction.WEST)
                robotView.setRotate(270);
            /* Add the robot in the pane at the correct position on the grid */
            pane.add(robotView, robot.getPosX(), robot.getPosY());
        } catch (FileNotFoundException e) {
            System.err.println("WARNING: You've given a bad texture file/name for the robot. The program can't" +
                    "run without a visual representation of the robot so the program will stop.");
            System.exit(1);
        }
    }

    /**
     * Let the robot make an action and display it (with or without an animation) so the user can see whats going on
     * at a given time.
     * In order to display correctly the robot on a JFX grid pane, we had to take care of some points so :
     * - The robot actions will be played in a dedicated Thread that will be played after the generation of the map
     * - After a robot action, the program will regenerate the texture to "erase" the old robot image
     * - The old positions of the robot are stored in a dedicated object called "Atomic integer" compatible with Threads
     * @param actions Series of actions performed by the robot
     */
    public static void makeActions(String... actions) {
        /* Get old position of the robot to replace the ancien image by the map component once it has moved */
        AtomicInteger robotOldX = new AtomicInteger(robot.getPosX());
        AtomicInteger robotOldY = new AtomicInteger(robot.getPosY());
        /* Separate the movement of the robot and the generation of the map */
        Runnable task = () -> {
            int i = 0;
            while (i < actions.length) {
                loadingTime(actions[i]);
                /* After the generation of the map, run : */
                Platform.runLater(() -> {
                    displayRobot(robot); // Display the robot to it's new position
                    /* After the robot has performed it's given command : */
                    Platform.runLater(() -> {
                        /* The robot has moved, delete the remnant image by updating the map texture on top of it */
                        generateTexture(robotOldX.get(), robotOldY.get());
                        /* If the action performed didn't moved the robot, update the texture of the robot */
                        if (robot.getPosX() == robotOldX.get() || robot.getPosY() == robotOldY.get())
                            displayRobot(robot);
                    });
                });
                /* /!\  Those commands are executed BEFORE those on top of this comment  /!\ */
                /* Save the position of the robot before moving it (X and Y) */
                robotOldX.set(robot.getPosX());
                robotOldY.set(robot.getPosY());
                /* Run the action asked for the robot */
                robot.performActions(actions[i]);
                getInfos();
                i++;
            }
        };
        /* Start the new thread */
        new Thread(task).start();
    }

    private static void loadingTime(String action) {
        Runnable task = () -> {
            double i = 0.0;
            pi.setProgress(0);
            try {
                while (i++ < robot.getActionDuration(action)) {
                    pi.setProgress(i / robot.getActionDuration(action));
                        Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.err.println("ERROR: You can't stop the program while the robot is performing tasks. Execution failed");
                System.exit(1);
            }
        };
        new Thread(task).start();
    }

    private static void getInfos() {
        Runnable getInfosTask = () -> {
            Platform.runLater(() -> {
                Label titleListState = new Label("State of the robot :");
                infos.add(titleListState,0,0);
                /* All items are set in an observable list that can self refresh to display robot statistics */
                ObservableList<Stats> items = FXCollections.observableArrayList(Stats.extractor());
                Stats item_1 = new Stats();
                items.add(item_1);
                Stats item_2 = new Stats();
                items.add(item_2);
                Stats item_3 = new Stats();
                items.add(item_3);
                Stats item_4 = new Stats();
                items.add(item_4);
                Stats item_5 = new Stats();
                items.add(item_5);
                Stats item_6 = new Stats();
                items.add(item_6);
                /* A list view that display the current state of the robot (position, battery state, laser...) */
                ListView<Stats> listViewState = new ListView<Stats>(items);
                listViewState.setItems(items);
                infos.add(listViewState,0,1);
                Label titleListConfig = new Label("Configuration of the robot :");
                infos.add(titleListConfig,0,2);
                /* A list view that displays all configuration properties of the robot */
                ListView<String> listViewConfig = new ListView<>();
                listViewConfig.setDisable(true);
                robot.getConfig().forEach((key, value) -> {
                    listViewConfig.getItems().add(key + " = " + value);
                });
                infos.add(listViewConfig,0,3);
                /*try {
                    Image batteryLogo = new Image(new FileInputStream("textures/menu/" + robot.getBattery().getImageName() + ".png"));
                    ImageView batteryLogoView = new ImageView(batteryLogo);
                    batteryLogoView.setFitHeight(BLOCK_SIZE);
                    batteryLogoView.setFitWidth(BLOCK_SIZE - 10);
                    infos.add(batteryLogoView,1,0);
                } catch (FileNotFoundException e) {
                    System.err.println("ERROR: Unable to find some textures necessary for the good program behavior.");
                    System.exit(1);
                }*/
                Platform.runLater(() -> {
                    item_1.name.set("X = " + robot.getPosX());
                    item_2.name.set("Y = " + robot.getPosY());
                    item_3.name.set("Battery = " + String.format("%.2f", (robot.getBattery().getLevel() / robot.getBattery().getCapacity()) * 100) + " %");
                    item_4.name.set("Weight Carried = " + String.format("%.2f", (robot.getWeightCarried() / robot.getConfig().get("charge_maximale")) * 100) + " %");
                    item_5.name.set("Laser power = " + String.format("%.2f", robot.getLaser().getPower()));
                    item_6.name.set("Score = " + robot.getValue());
                    listViewState.refresh();
                });
            });
        };
        new Thread(getInfosTask).start();
    }

    public static void displayMaterial() {
        /* A list view that displays all configuration properties of the robot */
        ListView<String> listViewMaterial = new ListView<>();
        Material.getAllMaterial("files/material/material_list_1.txt").forEach((materialElement) -> {
            listViewMaterial.getItems().add(materialElement.getName());
        });
        material.add(listViewMaterial,0,0);
    }

    @Override
    public void start(Stage primaryStage) {
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
        /* Display the robot at it's position to initiate the program. Later, the robot will evolve on the map */
        displayRobot(robot);
        Robot.startGame();

        getInfos();
        displayMaterial();

        /* Run given commands to the robot in a separate thread (different from the map generation thread) */
        String[] actions = Robot.getActionsFromFile("files/actions/actions_1.txt");
        makeActions(actions);

        BorderPane borderPane = new BorderPane(pane);
        borderPane.setLeft(infos);
        borderPane.setRight(material);
        pi.setMaxWidth(800);
        borderPane.setBottom(pi);
        BorderPane.setAlignment(pi, Pos.CENTER);
        /* Add the GridPane into the scene panel */
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(1520);
        primaryStage.setHeight(560);
        /* Display the scene on the primary stage  */
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
