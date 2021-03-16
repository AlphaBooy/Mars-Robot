package map;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class Map {
    private char[][] representation;
    private int sizeX;
    private int sizeY;
    private MapObject [][] map;

    /**
     * Create a map based on a character representation of every items
     * present on the field.
     * @param representation
     */
    public Map(char[][] representation) {
        sizeX = representation[0].length;
        sizeY = representation.length;
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                this.map[i][j] = new MapObject(i, j, "test", representation[i][j]);
            }
        }
    }

    /**
     * Creates a empty map with a given size (in X and Y)
     * Objects can be added later on by using AddObject method
     * @param sizeX Width of the map that will be created
     * @param sizeY Height of the map that will be created
     */
    public Map(int sizeX, int sizeY) {
        //TODO
    }

    public Map() {
        this.sizeX = 40;
        this.sizeY = 20;
        getRepresentation("C:\\Users\\clem2\\Desktop\\Cours\\JAVA\\projet\\Objectif Mars\\files\\maps\\zone_1.txt");
    }

    /**
     * Load a representation from a file to be used in the process of creating a map
     * @param filePath The path of the file where the representation is stored
     */
    public void getRepresentation(String filePath) {
        File file = new File(filePath);

        try (FileReader fr = new FileReader(file)) {
            int charRead;
            int numLigne = 0, numColonne = 0;
            this.representation = new char[21][41];
            while ((charRead = fr.read()) != -1) {
                this.representation[numLigne][numColonne] = (char) charRead;
                numColonne++;
                if (numColonne > this.sizeX) {
                    numLigne++;
                    numColonne = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public char[][] getRepresentation() {
        return representation;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

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
}
