
package combat.map;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import combat.Display;
import combat.robot.Robot;


public class CombatMap {
	// The path leading to the file containing the map in characters
	private final String path = "files/combat/arena/zone_combat_2.txt";
    // The X size of the Map (can be called the length of the map) */
    private final int sizeX = 10;
    //The Y size of the Map (can be called the height of the map) */
    private final int sizeY = 10;
    // the energy given by the batteries (represented by '%') on the map
    private final int energy_battery_value = 4;
    // A collection containing the names (and numbers) of robots to instantiate
   
	private ArrayList<String> robotsNames =	 new ArrayList<String>(Arrays.asList("Curiosity","Millenium","Discovery","Normandy"));
    /**	
     * The representation of the map as characters
     */
    private char [][] map;
    
    // The array of robots currently presents on the map
    private ArrayList<Robot> robots;
    private static int turnCount =0;
    
    public static final int MAX_TURN = 20;

	/**
     * Load the given file and stores the characters in a matrix
     * the matrix is of the given size X Y
     * sizeX and sizeY are the number of columns and lines of characters inside the file
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
            this.map = new char[sizeX+1][sizeY]; //The size is a number of characters and in a text file the cursor can be placed between every character horizontally, hence the +1
            /* While we read a char that is considered as the end of the file by FileReader : */
            this.robots = new ArrayList<Robot>();
            while ((charRead = fr.read()) != -1) {
                /* If the line is over : */
                if (charRead == '\n') {
                    numLigne++; // We change the line number
                    numColonne = 0; // We start over from the first element of the new line
                /* If the char we get is valid (ie != \n & != -1) : */
                } else {
                    if(charRead == '@') { //If the char is a robot we add it to the robots list
                    	if(robotsNames.size() > 0) { // It is also necessary to have robots names left
	                    	Robot rb = new Robot(robotsNames.get(0),numColonne,numLigne);
	                    	robots.add(rb);
	                    	robotsNames.remove(0);
                    	}else { // Else there are no more robots to add so we replace it by an empty space
                    		charRead = ' ';
                    	}
                    }
                    // We can save it onto our array of representation
                    this.map[numColonne][numLigne] = (char) charRead;
                    numColonne++; // After that, we increase our position within the array
                }
            }
        }catch(ArrayIndexOutOfBoundsException e) {
            System.err.println("ERROR: An error occured during the creation of the map, please check that" +
        " the given file match the given size of the matrix. "+ e.getMessage());
        }catch (IOException e) {
            System.err.println("ERROR: An error occurred while opening the file containing the representation " +
                    "of the map you've given. Please consider verify the path file and the validity and correct access" +
                    " of the file." + e.getMessage());
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
	 * @return the height of the matrix
	 */
	public int getSizeY() {
		return sizeY;
	}

	/**
	 *
	 * @return the matrix containing the chars of the map
	 */
	public char[][] getMap() {return map;}

	public ArrayList<Robot> getRobots() {
		return robots;
	}

	/**
     * Display a 2D representation of the current map in the console, using characters
     */
    public void DisplayMap() {
        /* For each char in the map char representation : */
		System.out.println();
        for (int j = 0; j < sizeY; j++) {
            for (int i = 0; i < sizeX; i++) {
                System.out.print(getChar(i,j));
            }
            System.out.println();
        }
    }

    public void showMap(){
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				Display.generateTexture(i,j);
			}
		}
	}

    /*
     * return the char at the given coordinates in the matrix
     */
    public char getChar(int x, int y) {
    	if(x >= sizeX) x = sizeX-1;
    	if(x < 0) x = 0;
    	if(y >= sizeY) y = sizeY-1;
    	if(y < 0) y = 0;
    	return map[x][y];
    }
    /*
     * Set the char c at the given coordinates in the matrix
     */
    public void setChar(int x, int y, char c) {
    	map[x][y] = c;
    	Display.updateElement(x,y);
    }
    

    
    /**
     * Move the content of the pos1 to the pos2 (pos1 should be a robot)
     * @param x1 pos x of the robot
     * @param y1 pos y of the robot
     * @param x2 pos x of the destination
     * @param y2 pos y of the destination
     * @throws if the pos1 is not a robot
     */
    public boolean moveContent(int x1,int y1, int x2, int y2) throws IsNotARobotException {
    	boolean success = true;
    	//check if all the given coordinates fit inside the matrix, if not leave the function
    	if(!isPosValid(x1,y1) || !isPosValid(x2,y2))
    		success = false;
    	if(success) {
	    	char pos2 = getChar(x2,y2);
	    	Robot rb = getRobot(x1,y1);
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
     * Damage all robots on the 9 positions near and including the first one
     * @param x
     * @param y
     */
    public void damageRobots(int x, int y) {
    	for(int i = x - 1; i <= x + 1; i++) {
    		for(int j = y - 1; j <= y + 1; j++) {
    			if(isPosValid(i,j) && getChar(i,j) == '@') {
    				try {
    					Robot rb = getRobot(i,j);
    					rb.addEnergy(-1);
    				}catch(IsNotARobotException e) {
    					System.err.println("ERROR: No robot found here : " + e.getMessage());
    				}
    			}
    		}
    	}
    }
    /**
     * Give one energy to the robot on this position for each robot on all the 9 near position,
     * but also give 2 energy to every other robot on the 8 near positions
     * @param x posX of the robot to recharge
     * @param y posY of the robot to recharge
     */
    public void rechargeRobots(int x, int y) {
    	int energyToGive = 0;
    	Robot robotToRecharge = null;
		try {
			robotToRecharge = getRobot(x,y);
		}catch(IsNotARobotException e) {
			System.err.println("ERROR: The position for energy restituion isn't a robot : " + e.getMessage());
		}
    	for(int i = x - 1; i <= x + 1; i++) {
    		for(int j = y - 1; j <= y + 1; j++) {
    			if(isPosValid(i,j) && getChar(i,j) == '@') {//For all robots on the 9 positions
    				energyToGive += 1;
    				if(!(x == i && y == j)) {//If the position isn't the robot calling this method
        				try {
        					Robot rb = getRobot(i,j);
        					rb.addEnergy(2);
        				}catch(IsNotARobotException e) {
        					System.err.println("ERROR: No robot found for energy restitution : " + e.getMessage());
        				}
    				}
    			}
    		}
    	}
    	robotToRecharge.addEnergy(energyToGive);
    }
    
    /*
	 * Save the remainings robots after the end of the simulation
	 */
    public void remainingBots()
    {
    	 File file = new File("files\\combat\\remaining");
    	 file.delete();
    	  try {

    	    Path path = Paths.get("files\\combat\\remaining");

    	    //java.nio.file.Files;
    	    Files.createDirectories(path);


    	  } catch (IOException e) {

    	    System.err.println("Failed to create directory!" + e.getMessage());

    	  }

    	for (int i=0;i<robots.size();i++) {
		Robot rb = robots.get(i);
    	try {
		      FileWriter myWriter = new FileWriter("files\\combat\\remaining\\" + rb.getName() + ".txt");
		      myWriter.write(rb.getName() + ' '+ rb.getCLogAsString());
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
    }
    	
    }
 
    /**
     * Destroy all walls on the adjacent positions and withdraw one energy
     * from the robot on the center for each wall found
     * @param x posX of the robot
     * @param y posY of the robot
     */
    public void destroyWalls(int x, int y) {
    	int nbWalls = 0;
    	Robot robotToRecharge = null;
		try {
			robotToRecharge = getRobot(x,y);
		}catch(IsNotARobotException e) {
			System.err.println("ERROR: The position for wall breaking isn't a robot : " + e.getMessage());
		}
    	for(int i = x - 1; i <= x + 1; i++) {
    		for(int j = y - 1; j <= y + 1; j++) {
    			if(isPosValid(i,j) && getChar(i,j) == '#') {//For all robots on the 9 positions
    				setChar(i,j,' ');
    				nbWalls += 1;
    			}
    		}
    	}
    	robotToRecharge.addEnergy(-nbWalls);
    }
    /**
     * Destroy the given robot and remove it from the map, it is replaced by a battery
     */
	public void destroyRobot(int x, int y) {
		try {
			Robot robotToDestroy = getRobot(x,y);
			robots.remove(robotToDestroy);
			setChar(x, y, '%');
		}catch(IsNotARobotException e) {
			System.err.println("ERROR: The given robot to destroy isn't a robot : " + e.getMessage());
		}
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
    /**
     * Get the robot for the given coordinates
     * @param x
     * @param y
     * @return The corresponding robot
     * @throws IsNotARobotException if there are no robots with the given coordinates
     */
    public Robot getRobot(int x, int y) throws IsNotARobotException {
    	Robot rb = null;
    	for(int i = 0; i < robots.size(); i++) {
    		if(robots.get(i).getPosX() == x && robots.get(i).getPosY() == y)
    			rb = robots.get(i);
    	}
    	if(rb == null)
    		throw new IsNotARobotException("No robot found at the coordinates " + x + ";" + y);
    	return rb;
    }
    public char turn() {
		char endFlag = 0;
    	/*Scanner sc = new Scanner(System.in);
    	char c = sc.next().charAt(0);*/
		int c = 0;
   
    	if(c < 10)
    	{
    		if(turnCount == MAX_TURN)
    		{
    			endFlag = 's';
    		}
    		else
    		{
    		for (Robot rb : robots) {

    			 rb.executeCommand();
    			 
            }
    		//DisplayMap();
    		this.turnCount++;
    		}
    		c++;
    	}
    	if(c > 10)
    	{
    	 endFlag = 's';
    	}
    	return endFlag;
    	
    }

	/***
	 *
	 * @return the longest set of command from all the robots
	 */
	public int getLongestCommand(){
		int res = 0;
		for(Robot rb : robots){
			if(rb.getCommandTotal() > res)
				res = rb.getCommandTotal();
		}
		return res;
	}
    

}
