package tests;

import static org.junit.Assert.assertEquals;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import robot.Robot;
import map.Map; 

public class TestRobot {
    @Test
    @DisplayName("create a new robot test")
    public void createRobotTest(){
        // Map map = new Map();
        Robot robot = new Robot();
        // robot.toString();
    }

}