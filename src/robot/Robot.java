package robot;

import map.Map;
import map.MapObject;

import java.io.*;
import java.util.HashMap;

public class Robot {
    private HashMap<String, Double> config;
    /** A direction that can be one of {NORTH , SOUTH, EAST, WEST} */
    private Direction direction;
    private Laser laser;
    private Battery battery;
    /** Map on which the robot will appear and evolve */
    private Map map;
    /** The X position of the robot (supposed to evolve) */
    private int posX;
    /** The Y position of the robot (supposed to evolve) */
    private int posY;
    /** The total value the robot is carrying, the value also allow the robot to purchase material */
    private double value;
    /** The weight carried by the robot. If it's heavier than the max weight the robot can carried, it stop */
    private double weightCarried;

    public static final String PATH_TO_IMAGE = "textures/robot.png";

    /**
     * Generate the default robot object.
     * It'll be creating facing north, with a default laser and a default battery.
     * The default position of the robot is on the base position of the default map
     */
    public Robot(String configPath) {
        this.battery = (Battery) Material.getDefault()[0].getObject();
        this.laser = (Laser) Material.getDefault()[1].getObject();
        this.direction = Direction.NORTH;
        this.map = new Map();
        this.posX = map.getBase().getPosX();
        this.posY = map.getBase().getPosY();
        this.value = 0;
        this.weightCarried = 0;
        this.config = getConfig(configPath);
    }

    /**
     * Generate the default robot object.
     * It'll be creating facing north, with a default laser and a default battery.
     * The default position of the robot is on the base position of the map given
     * @param map The map where the robot will spawn
     */
    public Robot(Map map, String configPath) {
        this.battery = (Battery) Material.getDefault()[0].getObject();
        this.laser = (Laser) Material.getDefault()[1].getObject();
        this.direction = Direction.NORTH;
        this.map = map;
        this.posX = map.getBase().getPosX();
        this.posY = map.getBase().getPosY();
        this.value = 0;
        this.weightCarried = 0;
        this.config = getConfig(configPath);
    }

    public Direction getDirection() {
        return this.direction;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public double getValue() {
        return value;
    }

    public Laser getLaser() {
        return laser;
    }

    public Battery getBattery() {
        return battery;
    }

    public double getWeightCarried() {
        return weightCarried;
    }

    public HashMap<String, Double> getConfig() {
        return config;
    }

    public static HashMap<String, Double> getConfig(String path) {
        String lineContent = "";
        HashMap<String, Double> config = new HashMap<String, Double>();
        File file = new File(path);
        try (FileReader fr = new FileReader(file)) {
            int charRead;
            while ((charRead = fr.read()) != -1) {
                if (charRead == '\n') {
                    config.put(lineContent.split("=")[0],Double.parseDouble(lineContent.split("=")[1]));
                    lineContent = "";
                    continue;
                }
                lineContent += (char) charRead;
            }
        } catch (IOException e) {
            System.err.println("ERROR: The robot config file is missing or can't be reached." +
                    "Please, make sure the program can access to \"" + path + "\"");
        }
        return  config;
    }

    /**
     * Rotate the robot (change it's direction). This will change it's battery level and dure a given time.
     * All data for this will be taken from the config file given to the robot constructor.
     * @param direction The new direction the robot will be facing (NORTH, SOUTH, EAST, WEST).
     */
    public void rotate(Direction direction){
        /* Update the battery by the amount of battery taken by a robot rotation. If battery < 0 : the game is over */
        this.battery.useBattery(this.config.get("cout_rotation"));
        try {
            /* The program waits the necessary time for the robot to rotate */
            Thread.sleep((long)(this.config.get("temps_rotation") * 1000));
        } catch (InterruptedException e) {
            System.err.println("ERROR: You tried to stop (or change) the program while the robot was rotating." +
                    "The program will stop immediately to avoid any further issues.");
            System.exit(1);
        }
        /* FINALLY, change the direction of the robot */
        this.direction = direction;
    }

    /**
     * Move the robot by one "terrain unit" (one square on the map)
     * The robot will use POWER_FOR_MOVING energy of the battery to perform this movement
     * If the robot reach the end of the map and still try to move forward, it reach the end of the scan
     * you use to perform tasks on him so it'll disconnect and the game will be over.
     */
    public void move() {
        /* Handle the progress of the robot on the map with it's position (the movement is function of the direction) */
        if (this.getDirection() == Direction.SOUTH) {
            posY++;
        } else if (this.getDirection() == Direction.EAST) {
            posX++;
        } else if (this.getDirection() == Direction.WEST) {
            posX--;
        } else if (this.getDirection() == Direction.NORTH) {
            posY--;
        }
        /* CASE --> the robot evolve on a mined MapObject or on the base : the movement time is the minimum one */
        if (map.getObject(this.posX, this.posY).getName() == "void" || map.getObject(this.posX, this.posY).getName() == "base") {
            /* Use the necessary energy from the battery to move the robot forward. The robot'll stop if battery < 0 */
            this.battery.useBattery(this.config.get("cout_deplacement"));
            try {
                /* Wait enought time for the robot to move on the map. Durations are converted in milliseconds (*1000) */
                Thread.sleep((long) (this.config.get("temps_deplacement_vide") * 1000));
            } catch (InterruptedException e) {
                System.err.println("ERROR: You tried to stop (or change) the program while the robot was moving." +
                        "The program will stop immediately to avoid any further issues.");
                System.exit(1);
            }
        } else {
            /* CASE --> The Map Object hasn't been mined yet so the delay is handled by the mining process */
            this.mine();
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
            switch (instruction.toLowerCase().split(" ")[0]) {
                case "move": case "avancer":
                    this.move();
                    writeInResult("AVANCER,");
                    break;
                case "rotate": case "tourner":
                    switch (instruction.toLowerCase().split(" ")[1]) {
                        case "south", "sud" -> {
                            this.rotate(Direction.SOUTH);
                            writeInResult("TOURNER SUD,");
                        }
                        case "north", "nord" -> {
                            this.rotate(Direction.NORTH);
                            writeInResult("TOURNER NORD,");
                        }
                        case "east", "est" -> {
                            this.rotate(Direction.EAST);
                            writeInResult("TOURNER EST,");
                        }
                        case "west", "ouest" -> {
                            this.rotate(Direction.WEST);
                            writeInResult("TOURNER OUEST,");
                        }
                    }
                    break;
                case "acheter": case "buy":
                    buyMaterial(new Material(instruction.toLowerCase().split(" ")[1],"files/material/material_list_1.txt"));
                    writeInResult("ACHETER " + instruction.toUpperCase().split(" ")[1] + ",");
                    break;
            }
        }
        //gameOver(); // When all instructions over :
    }

    /**
     * Used to perform actions stored in a file.
     * The method will just get actions separated by a ',' in an "actions" folders file and return them as an array of
     * Strings. On a higher level, it can be used to inject actions to the "PerformActions" method.
     * @param path The path where actions are stored.
     * @return an array representing the list of actions the robot can perform on the map
     */
    public static String[] getActionsFromFile(String path) {
        String fileContent = "";
        File file = new File(path);
        try (FileReader fr = new FileReader(file)) {
            int charRead;
            while ((charRead = fr.read()) != -1) {
                fileContent += (char) charRead;
            }
        } catch (IOException e) {
            System.err.println("ERROR: The file describing the actions is missing or can't be reached." +
                    "Please, make sure the program can access to \"" + path + "\"");
        }
        return fileContent.split(",");
    }

    /**
     * Destroy a map object to get loot an ore that can be sold at a given value.
     */
    private void mine() {
        /* First, we get the object what is mined by the robot when the method is called */
        MapObject mo = map.getObject(this.posX, this.posY);
        /* Then we return the time needed to mine the MapObject (hardness * 100) / laser*/
        long time = (long)((mo.getAttribute("hardness") * 100) / this.laser.getPower());
        try {
            /* Wait the time needed by the robot to mine the MapObject */
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            System.err.println("ERROR: You tried to stop (or change) the program while the robot was mining." +
                    "The program will stop immediately to avoid any further issues.");
        }
        /* Use the battery needed for the robot to mine the map element */
        this.battery.useBattery((double) time);
        /* The laser looses power when used to mine an object */
        this.laser.loosePower((int) time, this.config.get("emoussage_laser"));
        /* Loot the ore contained within the map object (gain value for the result but also the weight to carry) */
        this.value += mo.getAttribute("value");
        this.weightCarried += mo.getAttribute("weight");
        /* Destroy the MapObject once the time to drill it is over, then, updates the map with the destoyed M-O */
        mo.destroy();
        map.setObject(mo.getPosX(), mo.getPosY(), mo);
        /* Check if the robot can carry that much weight */
        if (weightCarried > this.config.get("charge_maximale"))
            gameOver(); // If the robot is overload, end the game
    }

    public void gameOver() {
        writeInResult("SCORE " + Double.toString(this.value));
        System.out.println("the game is over !");
        setCurrentNumber(); // Upgrade the run version number
    }

    private void writeInResult(String resultToWrite) {
        String path = "files/results/run_" + getCurrentRunNumber();
        File resultFile = new File(path);
        try {
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(resultFile, true));
            myWriter.write(resultToWrite);
            myWriter.close();
        } catch (IOException e) {
            System.err.println("ERROR: Can't create the result file ! Make sure the program has access to the result repository.");
        }
    }

    @Override
    public String toString() {
        return "Robot{" +
                "config=" + config +
                ", direction=" + direction +
                ", laser=" + laser +
                ", battery=" + battery +
                ", map=" + map +
                ", posX=" + posX +
                ", posY=" + posY +
                ", value=" + value +
                ", weightCarried=" + weightCarried +
                '}';
    }

    public double getActionDuration(String action) {
        switch (action.toLowerCase()) {
            case "move": case "avancer":
                if (map.getObject(this.posX, this.posY).getName() == "void" || map.getObject(this.posX, this.posY).getName() == "base") {
                    return this.config.get("temps_deplacement_vide");
                }
                MapObject mo = map.getObject(this.posX, this.posY);
                return (mo.getAttribute("hardness") * 100) / this.laser.getPower();
            case "rotate south": case "tourner sud":
            case "rotate east": case "tourner est":
            case "rotate west": case "tourner ouest":
            case "rotate north": case "tourner nord":
                return this.config.get("temps_rotation");
        }
        return 0;
    }

    public void buyMaterial(Material material) {
        if (this.value > material.getCost()) {
            this.value -= material.getCost();
            if (Material.isLaser(material.getName()))
                this.laser = new Laser(material.getName(), material.getValue("files/material/material_list_1.txt", material.getName()));
        }
    }

    public static void startGame() {
        String path = "files/results/run_" + getCurrentRunNumber();
        File resultFile = new File(path);
        try {
            FileWriter myWriter = new FileWriter(resultFile);
            myWriter.write(" ");
            myWriter.close();
        } catch (IOException e) {
            System.err.println("ERROR: Can't create the result file ! Make sure the program has access to the result repository.");
        }
    }

    private static int getCurrentRunNumber() {
        File file = new File("files/results/current_number.txt");
        try (FileReader fr = new FileReader(file)) {
            int charRead;
            String fileContent = "";
            while ((charRead = fr.read()) != -1) {
                fileContent += (char) charRead;
            }
            return Integer.parseInt(fileContent);
        } catch (IOException e) {
            System.err.println("ERROR: Missing file, critical error !");
            System.exit(1);
        }
        return 1; // DEFAULT
    }

    private static void setCurrentNumber() {

        String path = "files/results/current_number.txt";
        File resultFile = new File(path);
        try {
            FileWriter myWriter = new FileWriter(resultFile);
            myWriter.write(getCurrentRunNumber() + 1);
            myWriter.close();
        } catch (IOException e) {
            System.err.println("ERROR: Can't create the result file ! Make sure the program has access to the result repository.");
        }
    }
}
