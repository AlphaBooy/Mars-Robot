package combat.map;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import map.MapObject;


public class CombatMap {
    /** The X size of the Map (can be called the length of the map) */
    private int sizeX;
    /** The Y size of the Map (can be called the height of the map) */
    private int sizeY;
    /**
     * The representation of the map as characters
     */
    private char [][] map;
    /**
     * Load the given file and stores the characters in a matrix
     * the matrix is of the given size X Y
     */
    public CombatMap(String path, int sizeX, int sizeY) {
        File file = new File(path);
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        try (FileReader fr = new FileReader(file)) {
            int charRead;
            int numLigne = 0, numColonne = 0;
            this.map = new char[sizeY][sizeX+1];
            /* While we read a char that is considered as the end of the file by FileReader : */
            while ((charRead = fr.read()) != -1 && numLigne < sizeY) {
                /* If the line is over : */
                if (charRead == '\n' || numColonne > sizeX) {
                    numLigne++; // We change the line number
                    numColonne = 0; // We start over from the first element of the new line
                /* If the char we get is valid (ie != \n & != -1) : */
                } else {
                    // We can save it onto our array of representation
                    this.map[numLigne][numColonne] = (char) charRead;
                    numColonne++; // After that, we increase our position within the array
                }
            }
        } catch (IOException e) {
            System.err.println("ERROR: An error occurred while opening the file containing the representation" +
                    "of the map you've given. Please consider verify the path file and the validity and correct access" +
                    "right of the file." + e.getMessage());
        }
    }
    
    public void DiplayMap() {
        /* For each char in the map char representation : */
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                System.out.print(this.map[i][j]);
            }
            System.out.println();
        }
    }
}
