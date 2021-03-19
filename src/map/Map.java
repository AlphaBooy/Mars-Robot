package map;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class Map {
    /**
     * The char representation of the map is an array of to dimensions which can be textured
     * by JavaFX or just be displayed in the console, in order to render the Map on a 2D basis.
     */
    private char[][] representation;
    /** The X size of the Map (can be called the length of the map) */
    private int sizeX;
    /** The Y size of the Map (can be called the height of the map) */
    private int sizeY;
    /**
     * An array of two dimensions composed of MapObjects, it's the more complex form of the
     * representation array because it's composed of complex objects instead of a char abstraction.
     */
    private MapObject [][] map;

    /**
     * Create a map based on a character representation of every items present on the field.
     * Here, the char representation is given through a 2D array. Size of the map (SizeX and SizeY) is
     * dynamically set thanks to the length of each arrays.
     * Don't use this constructor if you want to use directly a map representation stored in a text file !
     * @param representation
     */
    public Map(char[][] representation) {
        sizeX = representation[0].length;
        sizeY = representation.length;
        generateObjects();
    }

    /**
     * Creates a empty map with a given size (in X and Y)
     * Objects can be added later on by using AddObject method
     * @param sizeX Width of the map that will be created
     * @param sizeY Height of the map that will be created
     */
    public Map(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    /**
     * Create a map with the default file representation "zone_1.txt".
     * This map is set up with a given Size (SizeX = 40; SizeY = 20)
     * All objects are generated and ready to use. It can be a great method to test or to backup if needed.
     */
    public Map() {
        this.sizeX = 40;
        this.sizeY = 20;
        getRepresentation("files/maps/zone_1.txt", 41, 21);
        generateObjects();
    }

    /**
     * Load a representation from a file to be used in the process of creating a map.
     * The representation is loaded from a text file and is transformed into a
     * @param filePath The path of the file where the representation is stored
     */
    public void getRepresentation(String filePath, int sizeX, int sizeY) {
        File file = new File(filePath);

        try (FileReader fr = new FileReader(file)) {
            int charRead;
            int numLigne = 0, numColonne = 0;
            this.representation = new char[sizeY][sizeX];
            /* While we read a char that is considered as the end of the file by FileReader : */
            while ((charRead = fr.read()) != -1) {
                /* If the line is over : */
                if (charRead == '\n') {
                    numLigne++; // We change the line number
                    numColonne = 0; // We start over to the first element of the new line
                /* If the char we get is valid (ie != \n & != -1) : */
                } else {
                    // We can save it onto our array of representation
                    this.representation[numLigne][numColonne] = (char) charRead;
                    numColonne++; // After that, we increase our position within the array
                }
            }
        } catch (IOException e) {
            System.err.println("ERROR: An error occurred while opening the file containing the representation" +
                    "of the map you've given. Please consider verify the path file and the validity and correct access" +
                    "right of the file.");
        }
    }

    /**
     * Generate all objects with a given position and a char representation with the representation array
     * of the map.
     */
    public void generateObjects() {
        this.map = new MapObject[sizeY][sizeX];
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                this.map[i][j] = new MapObject(j, i, representation[i][j]);
            }
        }
    }

    /**
     * @return the representation of the map (this is not a toString)
     */
    public char[][] getRepresentation() {
        return representation;
    }

    /**
     * @return get the length of the map (SizeX)
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     * @return get the height of the map (sizeY)
     */
    public int getSizeY() {
        return sizeY;
    }

    /**
     * Get the MapObject from it's coordinates
     * @param posX It's position on the X axis
     * @param posY It's position on the Y axis
     * @return the MapObject found on the given position, null otherwise
     */
    public MapObject getObject(int posX, int posY) {
        return this.map[posY][posX];
    }

    /**
     * @return the base within the map (a map must have a base to be valid), it can be used to spawn the robot for ex
     */
    public MapObject getBase() {
        for (int i = 0; i < this.sizeY; i++) {
            for (int j = 0; j < this.sizeX; j++) {
                if (this.map[i][j].getName() == "Base")
                    return this.map[i][j];
            }
        }
        System.err.println("ERROR: It appear that there is no base in the map you've entered." +
                "A base is mandatory to perform certain actions, the process will end now !");
        return null;
    }

    /**
     * @return a string representation of the Map
     */
    @Override
    public String toString() {
        String result = "";
        this.map = new MapObject[this.sizeY][this.sizeX];
        for (int i = 0; i < this.sizeY; i++) {
            for (int j = 0; j < this.sizeX; j++) {
                result += this.map[i][j];
            }
        }
        return result;
    }

    public static void main(String[] args) {

    }
}
