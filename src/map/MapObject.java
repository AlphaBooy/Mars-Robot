package map;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MapObject {
    public static final String FILES_MEASURES = "files/measures/measure_1.txt";
    /** Position of the object within a line */
    private int posX;
    /** Position of the object within a collumn */
    private int posY;
    /** Name that describe the object (it's used later on to display each element */
    private String name;
    /** Char representation of the object that can be stored in a text file */
    public char mapRepresentation;

    /**
     * Create an object that can be placed latter on a Map
     * @param posX the position of the object on the X axis (within a line)
     * @param posY the position of the object on the Y axis (within a column)
     * @param name Name of the object that can be displayed in order to help the user
     * @param mapRepresentation The character representation of the object on the map
     */
    public MapObject(int posX, int posY, String name, char mapRepresentation) {
        this.posX = posX;
        this.posY = posY;
        this.name = name;
        this.mapRepresentation = mapRepresentation;
    }

    /**
     * Create an object without specifying the name. It'll be given automatically based on it's char representation
     * @param posX the position of the object on the X axis (within a line)
     * @param posY the position of the object on the Y axis (within a column)
     * @param mapRepresentation The character representation of the object on the map
     */
    public MapObject(int posX, int posY, char mapRepresentation) {
        this.posX = posX;
        this.posY = posY;
        this.mapRepresentation = mapRepresentation;
        this.name = getName(mapRepresentation);
    }

    /**
     * @return the name of the object (ex: "sable")
     */
    public String getName() {
        return name;
    }

    /**
     * Return the name of the element described as it's char representation
     * @param representation the char representation of an element on the map
     * @return the name of the given element
     */
    public String getName(char representation) {
        if (representation == '@')
            return "Base"; // Case: The char given is the base (not in the file of materials)
        if (representation == ' ')
            return "void"; // The object has been destroyed and must be replaced by nothing ==> "void"
        String [] fileContent = readMapObjectsFile(FILES_MEASURES);
        /* Then, we exploit the array we made to get the name of the object thanks to it's representation */
        for (int i = 0; i < fileContent.length; i++) {
            if (fileContent[i] == null) {
                continue;
            }
            String[] fileContentSplit = fileContent[i].split(" ");
            /* Reading each lines of the file */
            if (fileContentSplit[0].charAt(0) != representation)
                continue;
            // We have the correct character at the start of the line
            if (fileContentSplit.length == 5) { //The name of the MapObject is 1 word long :
                return fileContentSplit[1];
            } else { // The name if the MapObject is 2 or more words long :
                return fileContentSplit[1] + " " + fileContentSplit[2];
            }
        }
        return "NC";
    }

    /**
     * Get the attribute of the map object, one of those :
     * - Value (the "price" of the object for the end of the game)
     * - Hardness (the "durability" of the object once the robot started mining)
     * - weight (the weight of the object one mined and carried by the robot)
     * @param element The attribute you want to get from the configuration file
     * @return one of the 3 attributes that describes the map object
     */
    public int getAttribute(String element) {
        int index;
        /* Determining which of the attributes the program needs (add genericity to the method) */
        switch (element) {
            case "value": case "valeur":
                index = 3;
                break;
            case "hardness": case "solidite":
                index = 2;
                break;
            case "weight": case "poids":
                index = 1;
                break;
            default:
                /* Rise an exception if the method is called with an illegal argument */
                throw new IllegalStateException("Unexpected value: " + element);
        }
        /* Get the measure file and read it to parse it */
        String[] fileContent = readMapObjectsFile(FILES_MEASURES);
        /* Reading each lines of the file */
        for (int i = 0; i < fileContent.length; i++) {
            try {
                if (fileContent[i] == null) continue;
                /* If the representation is at the start of the line : this is the item that we want */
                if (fileContent[i].charAt(0) == this.mapRepresentation)
                    /* Then we can return the attribute needed by the program */
                    return Integer.parseInt(fileContent[i].split(" ")[fileContent[i].split(" ").length - index]);
            } catch (NumberFormatException e) {
                System.err.println("ERROR: When parsing the measures of the map, can't find : " + element + " of " + this.name + ". Stopping !");
                System.exit(1);
            }
        }
        return 0; // Because we have to return a value !
    }

    /**
     * @return the X position of the map object
     */
    public int getPosX() {
        return posX;
    }

    /**
     * @return the Y position of the map object
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Destroy a map object. Basically, change the representation with a ' ' and the name to void
     */
    public void destroy() {
        this.mapRepresentation = ' ';
        this.name = "void";
    }

    /**
     * Return a printable String that can be displayed to represent a MapObject
     * Ex: Base [x:2, y:5, @]
     * @return a String that represent the given MapObject
     */
    public String toString() {
        return name + " [x:" + posX + ", y:" + posY + ", " + mapRepresentation + "]";
    }

    /**
     * Read map objects from a text file.
     * The file will contain all possible items that can be placed in the map except m
     * - The base that is unique on the map and don't have attributes like "normal objects"
     * - The "void" item which is set when the robot break an item on the map and don't have any attributes either
     * @param path The path of the text file that will be read by the method
     * @return a String array representing all items that can be displayed on the map
     */
    private String[] readMapObjectsFile(String path) {
        String[] fileContent = new String[50]; // Up to 50 possible ores on the map
        /* First, we get, line by line, the content of the file describing all MapObjects */
        File file = new File(path);
        try (FileReader fr = new FileReader(file)) {
            int charRead, i = 0;
            /* Read every char of the text file until the end of the file (char = -1) */
            while ((charRead = fr.read()) != -1) {
                /* We can't process a "null" within this method so we deal with void strings instead */
                fileContent[i] = fileContent[i] == null ? "" : fileContent[i];
                /* We hit the end of a line, this is another object */
                if (charRead == '\n') {
                    i++;
                    continue;
                }
                /* Append the char read to a String representation that can be returned */
                fileContent[i] += (char) charRead;
            }
        } catch (IOException e) {
            System.err.println("ERROR: The file describing the map objects is missing or can't be reached." +
                    "Please, make sure the program can access to \"" + path + "\"");
        }
        return fileContent;
    }
}
