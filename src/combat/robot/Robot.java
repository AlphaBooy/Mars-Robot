package combat.robot;
import java.io.BufferedReader;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileReader;
import java.util.Scanner; // Import the Scanner class to read text files

import map.Map;
import map.MapObject;
import combat.map.CombatMap;
import combat.map.IsNotARobotException;
import combat.robot.*;
import robot.Direction;

public class Robot {

	private int energy;
    /** The X position of the robot (supposed to evolve) */
    private int posX;
    /** The Y position of the robot (supposed to evolve) */
    private int posY;
    /** The current line of the robot's command log */
    private static int c;
    /** The current line of the robot's data log */
    private static int d;
    
    private static int commandLog[];
    
    private static String name;
   

    

    public Robot(String chosenName, int posX, int posY) {
		this.name = chosenName;
		this.energy = 10;
		this.posX = posX;
		this.posY = posY;
		this.d = 0;
		this.c = 0;
		this.commandLog = new int[100];
		initBot();
	}
 
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
    
   
    public int getCommand() {
    	return commandLog[this.c];
 
    }
    public int getData()
    {
    	return d;
    }

    public String getName() {
    	return name;
    }
    public int getC()
    {
    	return c;
    }
    public int getEnergy() {
    	return energy;
    }
    public static void initBot() {
    	File robotFile= new File("files\\combat\\bots\\" +name+ ".txt");
    		
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
    
    
    

    public void executeCommand(){
    	int x,y;
    	CombatMap map = CombatMap.getInstance();
    	PublicStack pStack = PublicStack.getInstance();
    	switch(getCommand()) {
    	  case 'p':
    		  pStack.stack(d);
    		  System.out.printf("Action : p");
    	    break;
    	  case 'g':
    	    y = pStack.unStack();
    	    
    	    x = pStack.unStack();
    	    
    	    x = (x%2);
    	    y = (y%2);
    	    System.out.printf("Action : g");
    	    

    	    pStack.stack(x);
    	    break;
    	    
    	  case 'd':
      	    this.d = pStack.unStack();
      	  System.out.printf("Action : d");
      	  break;
    	  case 'm':
      	   this.d = pStack.unStack();
      	 System.out.printf("Action : m");
      	   break;
    	  case 'k':
    		  c = d;
        	   d =0;
        	   System.out.printf("Action : k");
      	    break;
    	  case 'y':
    		  switch(d)
    		  {
    		  case ' ' :
    			  moveRobot(Direction.EAST);
    			  break;
    		  case '!' :
    			  moveRobot(Direction.NORTH);
        		  break;
    		  case '"' :
    			  moveRobot(Direction.WEST);
        		  break;
    		  case '#' :
    			  moveRobot(Direction.SOUTH);
        		  break;
    		  case '$' :

    			 
    					  map.damageRobots(posX,posY);
 

				  map.damageRobots(posX, posY);

        		  break;
    		  case '%' :
    			  map.rechargeRobots(posX, posY);
        		  break;
    		  case '&' :
    			  map.destroyWalls(posX, posY);
        		  break;
    		  }
    		  System.out.printf("Action : y");
      	    break;
      	 
    	  case 'i':
      	    x = pStack.unStack();
      	    y = pStack.unStack();
      	  System.out.printf("Action : i");
      	    break;
    	  default:

    	    map.destroyRobot(posX, posY);

    	    break;
    	}
    	c++;
    	System.out.printf("\n%d",c);

    	
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
			System.err.println("ERROR: There was an atempt to move a non robot position " + e.getMessage());
		}
    }
	/*
	 * Add a certain amount of energy, can be negative but cannot go over 10 and on 0 or below the robot is destroyed
	 */
	public void addEnergy(int value) {
		CombatMap map = CombatMap.getInstance();
		this.energy += value;
		if(this.energy > 10)
			this.energy = 10;

		else if(this.energy <= 0);

		else if(this.energy <= 0)
			map.destroyRobot(posX, posY);
}

	
	

 
 

}



