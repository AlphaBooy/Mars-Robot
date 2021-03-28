package robot;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Material {

    private Object object;
    private int cost;

    /**
     * A Material object is basically an object of the game (Battery / Laser / ...) with a cost and an installation time
     * Each material can be "plugged" into the robot. This cost "cost" and last for "time" seconds
     * @param object The object that can be sold to the robot
     * @param cost The cost of the material within the shop
     */
    public Material(Object object, int cost) {
        this.object = object;
        this.cost = cost;
    }

    public Material(String name, String path) {
        String lineContent = "";
        File file = new File(path);
        try (FileReader fr = new FileReader(file)) {
            int charRead;
            while ((charRead = fr.read()) != -1) {
                if (charRead == '\n') {
                    if (isLaser(lineContent.split(" ")[0]) && lineContent.split(" ")[0] == name) {
                        this.object = new Laser(lineContent.split(" ")[0],
                                Double.parseDouble(lineContent.split(" ")[2]));
                    } else if (isBattery(lineContent.split(" ")[0])) {
                        this.object = new Battery(lineContent.split(" ")[0],
                                Double.parseDouble(lineContent.split(" ")[2]));
                    }
                    break;
                }
                lineContent += (char) charRead;
            }
        } catch (IOException e) {
            System.err.println("ERROR: The material list file is missing or can't be reached." +
                    "Please, make sure the program can access to \"" + path + "\"");
        }
    }

    /**
     * @return the default material list (composed of the default battery and the default laser)
     */
    public static Material[] getDefault() {
        return new Material[] {
                new Material(new Battery(), 200),
                new Material(new Laser(), 200)
        };
    }

    public static ArrayList<Material> getAllMaterial(String path) {
        String lineContent = "";
        ArrayList<Material> materials = new ArrayList<Material>();
        File file = new File(path);
        try (FileReader fr = new FileReader(file)) {
            int charRead;
            while ((charRead = fr.read()) != -1) {
                if (charRead == '\n') {
                    if (isLaser(lineContent.split(" ")[0])) {
                        Material material = new Material(new Laser(lineContent.split(" ")[0],
                                Double.parseDouble(lineContent.split(" ")[2])), Integer.parseInt(lineContent.split(" ")[1]));
                        materials.add(material);
                    } else if (isBattery(lineContent.split(" ")[0])) {
                        Material material = new Material(new Battery(lineContent.split(" ")[0],
                                Double.parseDouble(lineContent.split(" ")[2])), Integer.parseInt(lineContent.split(" ")[1]));
                        materials.add(material);
                    }
                    lineContent = "";
                    continue;
                }
                lineContent += (char) charRead;
            }
        } catch (IOException e) {
            System.err.println("ERROR: The material list file is missing or can't be reached." +
                    "Please, make sure the program can access to \"" + path + "\"");
        }
        return materials;
    }

    public static boolean isLaser(String name) {
        return name.split("_")[0].equals("laser");
    }

    public static boolean isBattery(String name) {
        return name.split("_")[0].equals("batterie");
    }

    public String getName() {
        return object.toString().split("'")[1];
    }

    public int getCost() {
        return cost;
    }

    public Object getObject() {
        return object;
    }

    public String toString() {
        return "[Object: " + object.toString() + "; Cost: " + cost + "]";
    }

    public static int getMAterialAtribute(String path, String name, String attribute) {
        int indexAttribute = 0;
        if (attribute == "cost" || attribute == "cout")
            indexAttribute = 2;
        if (attribute == "value" || attribute == "valeur")
            indexAttribute = 1;
        String lineContent = "";
        File file = new File(path);
        try (FileReader fr = new FileReader(file)) {
            int charRead;
            while ((charRead = fr.read()) != -1) {
                if (charRead == '\n') {
                    if (lineContent.split(" ")[0].equals(name))
                        return Integer.parseInt(lineContent.split(" ")[lineContent.split(" ").length - indexAttribute]);
                    lineContent = "";
                    continue;
                }
                lineContent += (char) charRead;
            }
        } catch (IOException e) {
            System.err.println("ERROR: The material list file is missing or can't be reached." +
                    "Please, make sure the program can access to \"" + path + "\"");
        }
        return 0;
    }
}
