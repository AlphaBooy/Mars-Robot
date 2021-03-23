// package tests;

// import static org.junit.Assert.assertEquals;

// import java.lang.annotation.ElementType;
// import java.lang.annotation.Retention;
// import java.lang.annotation.RetentionPolicy;
// import java.lang.annotation.Target;

// import javax.swing.plaf.synth.SynthSeparatorUI;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Disabled;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Tag;
// import org.junit.jupiter.api.Test;

// import robot.Battery;
// import robot.Direction;
// import robot.Laser;
// import robot.Robot;
// import map.Map; 

// public class TestRobot {
//     private Robot robot;
//     private static final String CHEMINCONFIG = "/Users/sylvain/Documents/projet-java-objectif-mars/files/robot/config_1.txt";
//     // private static final String CHEMINCONFIG = "files/robot/config_1.txt";

//     @BeforeEach
//     public void beforeEach(){
//         this.robot = new Robot(CHEMINCONFIG);
//     }

//     @Test
//     @DisplayName("a new robot should have the default value")
//     public void createRobotTest(){
    
//         assertEquals("The battery should be the default battery", new Battery().toString(), robot.getBattery().toString());
//         assertEquals("The laser should be the default laser", new Laser().toString(), robot.getLaser().toString());
//         assertEquals("The direction should be the NOTH direction", Direction.NORTH, robot.getDirection());
//         assertEquals("The posX should be the posX of the base", new Map().getBase().getPosX(), robot.getPosX());
//         assertEquals("The posY should be the posY od the base", new Map().getBase().getPosY(), robot.getPosY());
//         assertEquals("The value should be 0", 0, robot.getValue(), 0.001); //the last arguments is the precision
//         assertEquals("The getWeightCarried should be 0", 0, robot.getWeightCarried(), 0.001);
//     }

//     @Test
//     @DisplayName("change direction of robot")
//     public void changeDirectionRobotTest(){
//         assertEquals(Direction.NORTH, robot.getDirection());
//         robot.rotate(Direction.EAST);
//         assertEquals("The direction should be the east", Direction.EAST, robot.getDirection());
//         robot.rotate(Direction.SOUTH);
//         assertEquals("The direction should be the north", Direction.SOUTH, robot.getDirection());
//         robot.rotate(Direction.WEST);
//         assertEquals("The direction should be the west", Direction.WEST, robot.getDirection());
//     }

    
//     @Test
//     @DisplayName("Move the robot")
//     public void moveRobotTest(){
//         int x = robot.getPosX();
//         int y = robot.getPosY();

//         robot.move();
//         assertEquals("The posY should be decremented by 1", --y, robot.getPosY());
//         assertEquals("The posX shouln'd be incremented", x, robot.getPosX());
//         //Pour le tests de l'usure de la batterie, 
//         //Il faut regarder la case sur laquel il va s'avancer
//         //si elle est vide, la battery est usée de cout-deplacement
//         //si elle est pas vide elle est usée de la dureté du cailloux * 100
//         // assertEquals("The battery should be decremented by 0.0178", new Battery().getLevel() - 0.0178, robot.getBattery().getLevel(), 0.00001);
//     }
// }