package robot;


import map.Map;

import java.util.Arrays;

public class Robot {
    /** A direction that can be one of {NORTH , SOUTH, EAST, WEST} */
    private Direction direction;
    /** A complete list of the material carried by the robot */
    private Material[] material;
    /** The X position of the robot (supposed to evolve) */
    private int posX;
    /** The Y position of the robot (supposed to evolve) */
    private int posY;
    /** The battery of the robot, if it hit 0, the robot stops */
    private Battery battery;

    public static final String PATH_TO_IMAGE = "textures/robot.png";

    private static final int POWER_FOR_MOVING = 10;
    private static final int POWER_FOR_ROTATING = 15;

    /* Time are in seconds */
    private static final int TIME_FOR_MOVING = 1;
    private static final int TIME_FOR_ROTATING = 3;

    /**
     * Generate the default robot object.
     * It'll be creating facing south, with a default laser and a default battery.
     * The default position of the robot is on the base position of the default map
     */
    public Robot() {
        this.material = Material.getDefault();
        this.battery = new Battery();
        this.direction = Direction.SOUTH;
        Map map = new Map();
        this.posX = map.getBase().getPosX();
        this.posY = map.getBase().getPosY();
    }

    public Robot(Material[] material, int posX, int posY) {
        this.material = material;
        this.posX = posX;
        this.posY = posY;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public Material[] getEquipement() {
        return this.material;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    /**
     * Rotate the robot (change it's direction) and use POWER_FOR_ROTATING energy of the battery
     * @param direction The new direction the robot will be facing (NORTH, SOUTH, EAST, WEST)
     */
    public void rotate(Direction direction){
        this.direction = direction;
        this.battery.useBattery(POWER_FOR_ROTATING);
        try {
            Thread.sleep(TIME_FOR_ROTATING * 1000);
        } catch (InterruptedException e) {
            System.err.println("ERROR: You tried to stop (or change) the program while the robot was rotating." +
                    "The program will stop immediately to avoid any further issues.");
            System.exit(1);
        }
    }

    /**
     * Move the robot by one "terrain unit" (one square on the map)
     * The robot will use POWER_FOR_MOVING energy of the battery to perform this movement
     * If the robot reach the end of the map and still try to move forward, it reach the end of the scan
     * you use to perform tasks on him so it'll disconnect and the game will be over.
     */
    public void move() {
        /* Handle the progress of the robot on the map with it's position */
        if (this.getDirection() == Direction.SOUTH) {
            posY++;
        } else if (this.getDirection() == Direction.EAST) {
            posX++;
        } else if (this.getDirection() == Direction.WEST) {
            posX--;
        } else if (this.getDirection() == Direction.NORTH) {
            posY--;
        }
        this.battery.useBattery(POWER_FOR_MOVING);
        try {
            Thread.sleep(TIME_FOR_MOVING * 1000);
        } catch (InterruptedException e) {
            System.err.println("ERROR: You tried to stop (or change) the program while the robot was moving." +
                    "The program will stop immediately to avoid any further issues.");
            System.exit(1);
        }
    }

    /**
     * Read an array that contains instructions that will be used by the robot to perform a series of given actions
     * The robot can : move, rotate, mine, buy or wait.
     * Handle english or french instructions.
     * You can give a single instruction (a String) or a multitude (a String[])
     */
    public void performActions(String... instructions) {
        for (String instruction : instructions) {
            switch (instruction.toLowerCase()) {
                case "move": case "avancer":
                    this.move();
                    break;
                case "rotate south": case "tourner sud":
                    this.rotate(Direction.SOUTH);
                    break;
                case "rotate east": case "tourner est":
                    this.rotate(Direction.EAST);
                    break;
                case "rotate west": case "tourner ouest":
                    this.rotate(Direction.WEST);
                    break;
                case "rotate north": case "tourner nord":
                    this.rotate(Direction.NORTH);
                    break;
            }
            System.out.println(this.toString());
        }
    }

    @Override
    public String toString() {
        return "Robot{" +
                "direction=" + direction +
                ", material=" + Arrays.toString(material) +
                ", posX=" + posX +
                ", posY=" + posY +
                ", battery=" + battery +
                '}';
    }

    public static void main(String[] args) {
        Robot robot = new Robot();
        String[] actions = {
                "avancer",
                "tourner nord",
                "avancer",
                "avancer",
                "rotate east",
                "move",
                "move",
                "move"
        };
        robot.performActions(actions);
    }
}
