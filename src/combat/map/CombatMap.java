package combat.map;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import map.MapObject;
import robot.Direction;


public class CombatMap {
	private final String path = "files/maps/zone_combat_1.txt";
    // The X size of the Map (can be called the length of the map) */
    private final int sizeX = 30;
    //The Y size of the Map (can be called the height of the map) */
    private final int sizeY = 19;
    // the energy given by the batteries (represented by '%') on the map
    private final int energy_battery_value = 4;
    /**
     * The representation of the map as characters
     */
    private char [][] map;
    


	/**
     * Load the given file and stores the characters in a matrix
     * the matrix is of the given size X Y
     * sizeX and sizeY are the number of character inside the file
     */
    private static volatile CombatMap instance = null;
    
    /**
     * Since the CombatMap is unique for all the robot and the entire duration of the fight, the singleton pattern is applied
     * @return a new Map if none exist or the only instance otherwise
     */
    public static CombatMap getInstance() {
    	if(instance == null) {
    		//The keyword synchronized is only useful for when several threads try to get a new singleton at the same time,
    		//it should not happen but it is imperative to prevent this case from happening
    		synchronized(CombatMap.class) {
    			if(instance == null) {
    	    		instance =  new CombatMap();
    			}
    		}
    	}
    	return instance;
    	
    }
    
    private CombatMap() {
        File file = new File(path);

        try (FileReader fr = new FileReader(file)) {
            int charRead;
            int numLigne = 0, numColonne = 0;
            this.map = new char[sizeY][sizeX+1]; //The size is a number of characters and in a text file the cursor can be placed between every character horizontally, hence the +1 
            /* While we read a char that is considered as the end of the file by FileReader : */
            while ((charRead = fr.read()) != -1) {
                /* If the line is over : */
                if (charRead == '\n') {
                    numLigne++; // We change the line number
                    numColonne = 0; // We start over from the first element of the new line
                /* If the char we get is valid (ie != \n & != -1) : */
                } else {
                    // We can save it onto our array of representation
                    this.map[numLigne][numColonne] = (char) charRead;
                    numColonne++; // After that, we increase our position within the array
                }
            }
        }catch(ArrayIndexOutOfBoundsException e) {
            System.err.println("ERROR: An error occured during the creation of the map, please check that" +
        " the given file match the given size of the matrix. "+ e.getMessage());
        }catch (IOException e) {
            System.err.println("ERROR: An error occurred while opening the file containing the representation" +
                    "of the map you've given. Please consider verify the path file and the validity and correct access" +
                    "right of the file." + e.getMessage());
        }
    }
    /**
     * 
     * @return the length of the matrix
     */
    public int getSizeX() {
		return sizeX;
	}
	/**
	 * 
	 * @return the heigth of the matrix
	 */
	public int getSizeY() {
		return sizeY;
	}
    
    /**
     * Display a 2D representation of the current map in the console, using characters
     */
    public void DiplayMap() {
        /* For each char in the map char representation : */
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                System.out.print(this.map[i][j]);
            }
            System.out.println();
        }
    }
    /*
     * return the char at the given coordinates in the matrix
     */
    public char getChar(int x, int y) {
    	return map[x][y];
    }
    /*
     * Set the char c at the given coordinates in the matrix
     */
    private void setChar(int x, int y, char c) {
    	map[x][y] = c;
    }
    

    
    /**
     * Move the content of the pos1 to the pos2 (pos1 should be a robot)
     * @param x1 pos x of the robot
     * @param y1 pos y of the robot
     * @param x2 pos x of the destination
     * @param y2 pos y of the destination
     * @throws if the pos1 is not a robot
     */
    public boolean moveContent(int x1,int y1, int x2, int y2, combat.robot.Robot rb) throws IsNotARobotException {
    	boolean success = true;
    	//check if all the given coordinates fit inside the matrix, if not leave the function
    	if(!isPosValid(x1,y1) || !isPosValid(x2,y2))
    		success = false;
    	if(success) {
	    	char pos1 = getChar(x1,y1);
	    	char pos2 = getChar(x2,y2);
	    	if(pos1 != '@')
	    		throw new IsNotARobotException("No robot found at the coordinates " + x1 + ";" + x2);
	    	if(pos2 == '%') {
	    		rb.addEnergy(energy_battery_value);
	    	}else if(pos2 == '#' || pos2 == '@') { // If the second pos is a wall or another robot the movement is cancelled
	    		success = false;
	    	}
	    	if(success) {
	    		setChar(x1,y1,' ');
	    		setChar(x2,y2,'@');
	    	}
	    		
    	}
    	return success;
    }
    /**
     * Check if the given set of coordinate fit inside the map matrix
     * @param x
     * @param y
     * @return true if the set is valid and false otherwise
     */
    private boolean isPosValid(int x, int y) {
    	boolean success = true;
    	if(x < 0 || x >= sizeX)
    		success = false;
    	if(y < 0 || y >= sizeY)
    		success = false;
    	return success;
    }
}
