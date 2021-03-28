package robot;

import map.Map;
import map.MapObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Robot {
    public static final String BEST_RUN = "files/results/best_run";
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

    public static final String FILES_MATERIAL_MATERIAL_LIST_1_TXT = "files/material/material_list_1.txt";
    public static final String FILES_RESULTS_CURRENT_NUMBER = "files/results/current_number.txt";
    public static final String PATH_TO_IMAGE = "textures/robot.png";
    public static final int ACCELERATION_FACTOR = 1;
    private static final String CHEMINCONFIG = "files/robot/config_1.txt";

    /**
     * Generate the default robot object.
     * It'll be creating facing north, with a default laser and a default battery.
     * The default position of the robot is on the base position of the default map
     */
    public Robot() {
        this.battery = (Battery) Material.getDefault()[0].getObject();
        this.laser = (Laser) Material.getDefault()[1].getObject();
        this.direction = Direction.NORTH;
        this.map = new Map();
        this.posX = map.getBase().getPosX();
        this.posY = map.getBase().getPosY();
        this.value = 0;
        this.weightCarried = 0;
        this.config = getConfig(CHEMINCONFIG);
    }

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
            Thread.sleep((long)(this.config.get("temps_rotation") * 1000) / ACCELERATION_FACTOR);
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
        /* If the robot leaves the map, you can't track it anymore, the game is over ! */
        if (posX > map.getSizeX() || posX < 0 || posY > map.getSizeY() || posY < 0)
            gameOver();
        /* We unload the robot when we hit the base */
        if (map.getObject(this.posX, this.posY).getName() == "base")
            this.weightCarried = 0;
        /* CASE --> the robot evolve on a mined MapObject or on the base : the movement time is the minimum one */
        if (map.getObject(this.posX, this.posY).getName() == "void" || map.getObject(this.posX, this.posY).getName() == "base") {
            /* Use the necessary energy from the battery to move the robot forward. The robot'll stop if battery < 0 */
            this.battery.useBattery(this.config.get("cout_deplacement"));
            try {
                /* Wait enought time for the robot to move on the map. Durations are converted in milliseconds (*1000) */
                Thread.sleep((long) (this.config.get("temps_deplacement_vide") * 1000) / ACCELERATION_FACTOR);
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
                    buyMaterial(instruction.toLowerCase().split(" ")[1]);
                    writeInResult("ACHETER " + instruction.toUpperCase().split(" ")[1] + ",");
                    break;
                default:
                    gameOver();
            }
        }
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
            Thread.sleep((time * 1000) / ACCELERATION_FACTOR);
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

    /**
     * The game is over when :
     *      - The robot run out of battery
     *      - The robot carry to much weight and can't move anymore
     *      - The robot leaves the map area and can't be tracked anymore
     *      - If the robot want to perform an action that isn't understood (the robot explode)
     *      - ...
     * When the game is over, write the final score in the result file, update the current run number and exit the app.
     */
    public void gameOver() {
        writeInResult("SCORE " + Double.toString(this.value));
        System.out.println("the game is over !");
        setCurrentNumber(); // Upgrade the run version number
        System.exit(0);
    }

    /**
     * Write a result in a dedicated file.
     * The results are composed of :
     *      - All actions made by the robot separated by ','
     *      - The total score made by the robot at the end of it's journey
     * Results files are generated with a unique id and all results are append at the end of the file.
     * @param resultToWrite The String that'll be written at the end of the result file.
     */
    private void writeInResult(String resultToWrite) {
        String path = "files/results/run_" + getCurrentRunNumber();
        File resultFile = new File(path);
        try {
            /* Create a BufferedWritter that'll write the given String at the end of the right result file */
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(resultFile, true));
            myWriter.write(resultToWrite);
            myWriter.close();
        } catch (IOException e) {
            System.err.println("ERROR: Can't create the result file ! Make sure the program has access to the result repository.");
            System.exit(1); // You can't proceed if the results can't be saved !
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

    /**
     * @param action The action you want the total duration time
     * @return The duration of the action given in argument
     */
    public double getActionDuration(String action) {
        switch (action.toLowerCase().split(" ")[0]) {
            case "move": case "avancer":
                /* If the robot evolve on a broken map object or on the base */
                if (map.getObject(this.posX, this.posY).getName() == "void" || map.getObject(this.posX, this.posY).getName() == "base") {
                    return (this.config.get("temps_deplacement_vide")) / ACCELERATION_FACTOR;
                }
                /* If the robot need to mine the map element to progress on the map */
                MapObject mo = map.getObject(this.posX, this.posY);
                return ((mo.getAttribute("hardness") * 100) / this.laser.getPower()) / ACCELERATION_FACTOR;
            case "rotate": case "tourner":
                return this.config.get("temps_rotation") / ACCELERATION_FACTOR;
            case "acheter": case "buy":
                return this.getConfig().get("temps_installation") * 1000 / ACCELERATION_FACTOR;
        }
        return 0;
    }

    /**
     * The robot need to buy material to mine more objects and get an higher score. To do so, the robot need :
     *      - to be on the base (where it's material is stored and can be bought + equipped)
     *      - to have enough value to buy the desired material
     * Then, the robot will buy instantly the material from the base deducing it's price to the value pool and set up
     * the new equipment with a constant installation time taken from the configuration file.
     * @param materialName The name of the material that will be bought and equipped
     */
    public void buyMaterial(String materialName) {
        /* If the robot can buy the material, buy it, equip it and the cost of the material is deduced from the robot value */
        if (this.value > Material.getMAterialAtribute(FILES_MATERIAL_MATERIAL_LIST_1_TXT, materialName, "cost")
                && this.posX == map.getBase().getPosX() && this.posY == map.getBase().getPosY()) {
            this.value -= Material.getMAterialAtribute(FILES_MATERIAL_MATERIAL_LIST_1_TXT, materialName, "cost");
            /* Generate the material if it's a laser */
            if (Material.isLaser(materialName))
                this.laser = new Laser(materialName, Material.getMAterialAtribute(FILES_MATERIAL_MATERIAL_LIST_1_TXT, materialName, "value"));
            /* Generate the material if it's a battery */
            if (Material.isBattery(materialName))
                this.battery = new Battery(materialName, Material.getMAterialAtribute(FILES_MATERIAL_MATERIAL_LIST_1_TXT, materialName, "value"));
            try {
                /* Wait for the robot to equip the material */
                Thread.sleep((long) (this.getConfig().get("temps_installation") / ACCELERATION_FACTOR));
            } catch (InterruptedException e) {
                System.err.println("ERROR: You tried to stop (or change) the program while the robot was equipping an item." +
                        "The program will stop immediately to avoid any further issues.");
                System.exit(1);
            }
        }
    }

    /**
     * Start the game by creating a new result file where all results will be stored during the game.
     * If the run number has already been saved by another run, overwrite it to avoid further issues.
     */
    public static void startGame() {
        String path = "files/results/run_" + getCurrentRunNumber();
        File resultFile = new File(path);
        try {
            /* OverWrite the result file (or just initiate it if it didn't exists before */
            FileWriter myWriter = new FileWriter(resultFile);
            myWriter.write("");
            myWriter.close();
        } catch (IOException e) {
            System.err.println("ERROR: Can't create the result file ! Make sure the program has access to the result repository.");
            System.exit(1);  // We can't proceed if no result are saved in the result file !
        }
    }

    /**
     * @return the current run number to name the result file and don't overwrite previous runs, number are stored in a dedicated file.
     */
    private static int getCurrentRunNumber() {
        File file = new File(FILES_RESULTS_CURRENT_NUMBER);
        try (FileReader fr = new FileReader(file)) {
            int charRead;
            String fileContent = "";
            while ((charRead = fr.read()) != -1) {
                fileContent += (char) charRead;
            }

            return Integer.parseInt(fileContent);
        } catch (NumberFormatException formatException) {
            System.err.println("ERROR: Wrong number of run detected, replaced with default 'run_1'. Waiting for you to" +
                    " patch this issue manually");
            return 1;
        } catch (IOException e) {
            System.err.println("ERROR: Missing file, critical error !");
            System.exit(1);
        }
        return 1; // DEFAULT
    }

    /**
     * Set the run number to *current run number + 1*
     */
    private static void setCurrentNumber() {
        String path = FILES_RESULTS_CURRENT_NUMBER;
        File savedCurrentRunNumberFile = new File(path);
        try {
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(savedCurrentRunNumberFile));
            myWriter.write((char) (getCurrentRunNumber() + 1));
            myWriter.close();
        } catch (IOException e) {
            System.err.println("ERROR: Can't create the result file ! Make sure the program has access to the result repository.");
        }
    }

    public String[] getBestPath() {
        boolean continuer= true;
        ArrayList<String> path = new ArrayList<>();
        int bestScore = 0;
        File bestRun = new File(BEST_RUN);
        /* Parse the score of the best run registered */
        try (FileReader fr = new FileReader(bestRun)) {
            int charRead;
            String fileContent = "";
            while ((charRead = fr.read()) != -1) {
                fileContent += (char) charRead;
            }
            bestScore = Integer.parseInt(fileContent.split(",")[fileContent.split(",").length].split(" ")[1]);
        } catch (IOException e) {
            bestScore = 0;
        }
        while (continuer) { // Exit on game over

        }
        /* If the result is good enough, save the results in the "best result file" */
        if (this.value > bestScore) {
            try {
                /* Create a BufferedWritter that'll write the given String at the end of the right result file */
                BufferedWriter myWriter = new BufferedWriter(new FileWriter(bestRun, true));
                myWriter.write((int) this.value);
                myWriter.close();
            } catch (IOException e) {
                System.err.println("ERROR: Can't create the result file ! Make sure the program has access to the result repository.");
                System.exit(1); // You can't proceed if the results can't be saved !
            }
        }
        return null;
    }
}
