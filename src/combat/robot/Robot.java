package combat.robot;
import java.io.BufferedReader;

import java.io.File;  // Import the File class
import java.io.FileReader;


import combat.Display;
import combat.map.CombatMap;
import combat.map.IsNotARobotException;
import robot.Direction;

public class Robot {

	private int energy;
    /** The X position of the robot */
    private int posX;
    /** The Y position of the robot */
    private int posY;
    /** The robot's C record */
    private int C;
    /** The robot's D record */
    private int D;
    /** The robot's command log */
    private int commandLog[];
    /** The robot's name **/
    private String name;


	/**
	 * The object robot that will be contained in the map, it is represented with a '@'
	 * @param chosenName robot's name
	 * @param posX pos x on the map matrix
	 * @param posY pos y on the map matrix
	 */
	public Robot(String chosenName, int posX, int posY) {
		this.name = chosenName;
		this.energy = 5;
		this.posX = posX;
		this.posY = posY;
		this.D = 0;
		this.C = 0;
		this.commandLog = new int[100];
		initBot(chosenName);
	}
    /**
	 * 
	 * @return The position of the robot on the X axis
	 */
 
    public int getPosX() {
        return posX;
    }

    /**
	 * 
	 * @return The position of the robot on the Y axis
	 */
    public int getPosY() {
        return posY;
    }
    

	/**
	 * Get the next command for the robot
	 * @return if the commandLog isn't ended return it, else return -1
	 */
	public int getCommand() {
    	int res = -1;
    	if(this.C < commandLog.length)
    		res = commandLog[this.C];
    	return res;
 
    } 
    /**
	 * 
	 * @return Return the robot's command log
	 */
    public String getCLogAsString()
    {
    	String res = "";
    	for(int i : commandLog) {
    		res += (char)i;
    	}
    	return res;
    }
    /**
   	 * 
   	 * @return Return the robot's name
   	 */

    public String getName() {
    	return name;
    }
    /**
   	 * 
   	 * @return Return the robot's energy
   	 */
    public int getEnergy() {
    	return energy;
    }

	/**
	 * Get the total number of commands on this robot
	 * @return length of commandLog
	 */
	public int getCommandTotal(){
		return commandLog.length;
	}
	 /**
	 * Initialize the robot's command log
	 * @param name of the robot to initialize
	 */
    public  void initBot(String name) {
    	File robotFile= new File("files\\combat\\bots\\" + name + ".txt");
    		
    		try {
    			BufferedReader r = new BufferedReader(new FileReader(robotFile));
    			int ch;
    			int i =0;
    			while((ch=r.read())!=' ');
    			
    			while((ch=r.read()) != -1){
    				 if (Character.isUpperCase(ch)){
    		                ch =Character.toLowerCase(ch);
    		         }
    			    		  commandLog[i] = ch;
    			    		  i++;
    			    		  
    			}
    			
    			r.close();
    		} catch(Exception e) {
   	         // if any error occurs
   			e.printStackTrace();}
   		 
    }
 
    /**
	 * Execute the command located at the position C then add 1 to C
	 */
    public void executeCommand(){
    	int x,y;

    	CombatMap map = CombatMap.getInstance();
    	PublicStack pStack = PublicStack.getInstance();
    	switch(getCommand()) {
    	  case 'p':
    		  pStack.stack(commandLog[D]);
    		  System.out.printf("Action : p\n");
    	    break;
    	  case 'g':

    	  	y = pStack.unStack();
    	    
    	    x = pStack.unStack();
    	    
    	    x %= 2;
    	    y %= 2;
    	    x &= y;
    	    x = ~x;
    	    pStack.stack(x);
    	    System.out.printf("Action : g\n");
    	    break;
    	    
    	  case 'd':
      	    D = pStack.unStack();
      	  	System.out.printf("Action : d\n");
      	  	break;
    	  case 'm':
      	   commandLog[D] = pStack.unStack();
      	   System.out.printf("Action : m\n");
      	   break;
    	  case 'k':
    		  C = D;
    		  D = 0;
        	  System.out.printf("Action : k\n");
      	    break;
    	  case 'y':
    		  switch(D)
    		  {
    		  case ' ' :
    			  moveRobot(Direction.EAST);
    			  System.out.println("Action : y  ");
    			  break;
    		  case '!' :
    			  moveRobot(Direction.NORTH);
    			  System.out.printf("Action : y !\n");
        		  break;
    		  case '"' :
    			  moveRobot(Direction.WEST);
    			  System.out.printf("Action : y " + '"'+'\n');
        		  break;
    		  case '#' :
    			  moveRobot(Direction.SOUTH);
    			  System.out.printf("Action : y #\n");
        		  break;
    		  case '$' :
    			 map.damageRobots(posX,posY);
    			 System.out.printf("Action : y $\n");
        		 break;
    		  case '%' :
    			  map.rechargeRobots(posX, posY);
    			  System.out.println("Action : y %");
        		  break;
    		  case '&' :
    			  map.destroyWalls(posX, posY);
    			  System.out.printf("Action : y &\n");
        		  break;
        	default :
				map.destroyRobot(posX,posY);
    		  }
    		  
      	    break;
      	 
    	  case 'i':
      	    x = pStack.unStack();
      	    y = pStack.unStack();
      	    
      	  	pStack.stack(map.getChar(x, y));
      	  	System.out.printf("Action : i\n");
      	    break;
    	  default:
    		  map.destroyRobot(posX,posY);
    	    break;
    	}
    	this.C++;

    	
    }
    /**
     * Move the robot in the given direction if possible, nothing happen otherwise
     * @param dir the direction to move the robot if possible
     */
    public void moveRobot(Direction dir) {
    	CombatMap map = CombatMap.getInstance();
    	try {
		switch(dir) {
		case NORTH :
			if(map.moveContent(posX, posY, posX, posY-1))
				posY -=1;
			break;
		case WEST :
			if(map.moveContent(posX, posY, posX-1, posY))
				posX -=1;
			break;
		case SOUTH :
			if(map.moveContent(posX, posY, posX, posY+1))
				posY +=1;
			break;
		case EAST :
			if(map.moveContent(posX, posY, posX+1, posY))
				posX +=1;
			break;
		default :
			throw new IllegalArgumentException("The given direction is unknown.");
		}
		}catch(IsNotARobotException e) {
			System.err.println("ERROR: There was an attempt to move a non robot position " + e.getMessage());
		}
    }
	/*
	 * Add a certain amount of energy, can be negative but cannot go over 10 and on 0 or below the robot is destroyed
	 */
	public void addEnergy(int value) {

		this.energy += value;
		if(this.energy > 10)
			this.energy = 10;
		if(this.energy <= 0){
			CombatMap map = CombatMap.getInstance();
			map.destroyRobot(posX,posY);
		}
		Display.updateElement(posX,posY);
	}
}



