package tests;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import robot.Battery;
import robot.Direction;
import robot.Laser;
import robot.Robot;
import map.Map;
import map.MapObject; 

public class TestRobot {
    private Robot robot;
    // private static final String CHEMINCONFIG = "/Users/sylvain/Documents/projet-java-objectif-mars/files/robot/config_1.txt";
    private static final String CHEMINCONFIG = "files/robot/config_1.txt";

    @BeforeEach
    public void beforeEach(){
        this.robot = new Robot(CHEMINCONFIG);
    }

    @Test
    @DisplayName("a new robot should have the default value")
    public void createRobotTest(){
    
        assertEquals(new Battery().toString(), robot.getBattery().toString(), "The battery should be the default battery");
        assertEquals(new Laser().toString(), robot.getLaser().toString(), "The laser should be the default laser");
        assertEquals(Direction.NORTH, robot.getDirection(), "The direction should be the NOTH direction");
        assertEquals(new Map().getBase().getPosX(), robot.getPosX(), "The posX should be the posX of the base");
        assertEquals(new Map().getBase().getPosY(), robot.getPosY(), "The posY should be the posY od the base");
        assertEquals(0, robot.getValue(), 0.001, "The value should be 0"); //the last arguments is the precision
        assertEquals(0, robot.getWeightCarried(), 0.001, "The getWeightCarried should be 0");
    }

    @Test
    @DisplayName("change direction of robot")
    public void changeDirectionRobotTest(){
        assertEquals(Direction.NORTH, robot.getDirection());
        robot.rotate(Direction.EAST);
        assertEquals(Direction.EAST, robot.getDirection(), "The direction should be the east");
        robot.rotate(Direction.SOUTH);
        assertEquals(Direction.SOUTH, robot.getDirection(), "The direction should be the north");
        robot.rotate(Direction.WEST);
        assertEquals(Direction.WEST, robot.getDirection(), "The direction should be the west");
    }


    @Test
    @DisplayName("Move the robot")
    public void moveRobotTest(){
        int x = robot.getPosX();
        int y = robot.getPosY();

        robot.move();
        assertEquals(--y, robot.getPosY(), "The posY should be decremented by 1");
        assertEquals(x, robot.getPosX(), "The posX shouln'd be incremented");

        double hardness = new Map().getObject(robot.getPosX(), robot.getPosY()).getAttribute("hardness");
        Battery battery = new Battery();
        Laser laser = new Laser();
        double usedPoint = hardness * 100 / robot.getLaser().getPower();
        battery.useBattery(usedPoint);
        laser.loosePower((long)usedPoint, robot.getConfig().get("emoussage_laser"));
        assertEquals(battery.getLevel(), robot.getBattery().getLevel(), 0.01, "The battery should be decremented by " + hardness * 100 / robot.getLaser().getPower());
        assertEquals(laser.getPower(), robot.getLaser().getPower(), "The laser should loose a little power (" + usedPoint + ")");
    }
}