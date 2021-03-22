package robot.tests;

import map.Map;
import robot.Direction;
import robot.Robot;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

    @org.junit.jupiter.api.Test
    void testGetConfig() {
    }

    @org.junit.jupiter.api.Test
    void rotate() {
        Robot robot = new Robot("files/robot/config_1.txt");
        /* Verify that the robot is facing south when spawn */
        assertTrue(robot.getDirection() == Direction.NORTH);
        /* Also verify if the robot can get all 4 directions correctly */
        robot.rotate(Direction.SOUTH);
        assertTrue(robot.getDirection() == Direction.SOUTH);
        robot.rotate(Direction.EAST);
        assertTrue(robot.getDirection() == Direction.EAST);
        robot.rotate(Direction.WEST);
        assertTrue(robot.getDirection() == Direction.WEST);
        robot.rotate(Direction.NORTH);
        assertTrue(robot.getDirection() == Direction.NORTH);
    }

    @org.junit.jupiter.api.Test
    void move() {
        /*  Verify that the robot spawn on the base at the start of the game */
        Robot robot = new Robot("files/robot/config_1.txt");
        Map map = new Map();
        assertTrue(robot.getPosX() == map.getBase().getPosX());
        /* Check if the horizontal movement change the X of the robot */
        robot.move();
        assertTrue(robot.getPosX() == map.getBase().getPosX());
        robot.rotate(Direction.SOUTH);
        robot.move();
        assertTrue(robot.getPosX() == map.getBase().getPosX());
        /* Check if the vertical movement change the X of the robot */;
        robot.rotate(Direction.EAST);
        robot.move();
        assertTrue(robot.getPosX() == map.getBase().getPosX() + 1);;
        robot.rotate(Direction.WEST);
        robot.move();
        assertTrue(robot.getPosX() == map.getBase().getPosX());

    }

    @org.junit.jupiter.api.Test
    void performActions() {
    }

    @org.junit.jupiter.api.Test
    void getActionsFromFile() {
    }

    @org.junit.jupiter.api.Test
    void gameOver() {
    }

    @org.junit.jupiter.api.Test
    void getActionDuration() {
    }
}