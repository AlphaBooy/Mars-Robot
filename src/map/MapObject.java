package map;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MapObject {
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
        String [] fileContent = readMapObjectsFile();
        /* Then, we exploit the array we made to get the name of the object thanks to it's representation */
        for (int i = 0; i < fileContent.length; i++) {
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

    public int getValue() {
        String [] fileContent = readMapObjectsFile();
        for (int i = 0; i < fileContent.length; i++) {
            String[] fileContentSplit = fileContent[i].split(" ");
            /* Reading each lines of the file */
            if (fileContentSplit[0].charAt(0) == this.mapRepresentation)
                return fileContentSplit[fileContentSplit.length - 3].charAt(0);
        }
        return -1;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    /**
     * Return a printable String that can be displayed to represent a MapObject
     * Ex: Base [x:2, y:5, @]
     * @return a String that represent the given MapObject
     */
    public String toString() {
        return name + " [x:" + posX + ", y:" + posY + ", " + mapRepresentation + "]";
    }

    private String[] readMapObjectsFile() {
        String[] fileContent = new String[7]; // Up to 50 possible ores on the map
        /* First, we get, line by line, the content of the file describing all MapObjects */
        File file = new File("files/measures/measure_1.txt");
        try (FileReader fr = new FileReader(file)) {
            int charRead, i = 0;
            while ((charRead = fr.read()) != -1) {
                fileContent[i] = fileContent[i] == null ? "" : fileContent[i];
                if (charRead == '\n') {
                    i++;
                    continue;
                }
                fileContent[i] += (char) charRead;
            }
        } catch (IOException e) {
            System.err.println("ERROR: The file describing the map objects is missing or can't be reached." +
                    "Please, make sure the program can access to \"files/measures/measure_1.txt\"");
        }
        return fileContent;
    }

    /**
     * Get the MapObject from it's coordinates
     * @param posX It's position on the X axis
     * @param posY It's position on the Y axis
     * @return the MapObject found on the given position, null otherwise
     */
    public static MapObject getObject(int posX, int posY) {
        return null;
    }
}
