package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import map.Map;
import robot.Robot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main{
    public static void main(String[] args) {
        combat.robot.Robot Discovery = new combat.robot.Robot("Discovery",10,5);
        
        System.out.printf("Nom : %s Position : %d %d \n Log :",Discovery.getName(),Discovery.getPosX(),Discovery.getPosY());
        for(int i = 0;i < 25;i++) {
        System.out.printf("%c",Discovery.getCommandData(i));
        }
    }
    
}

