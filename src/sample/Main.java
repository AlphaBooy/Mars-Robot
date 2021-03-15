package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import map.Map;

import java.io.FileInputStream;
import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Objectif Mars");
        GridPane pane = new GridPane();

        Map map = new Map();
        int sizeX = map.getSizeX();
        int sizeY = map.getSizeY();

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                Image image = new Image(new FileInputStream("textures/" + map.getObject(x,y).getName().toLowerCase() + ".png"));
                ImageView iv = new ImageView(image);
                iv.setFitHeight(25);
                iv.setFitWidth(25);
                pane.add(iv, x, y);
            }
        }

        Scene scene = new Scene(pane);
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
