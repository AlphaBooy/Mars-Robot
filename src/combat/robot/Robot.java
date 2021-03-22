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
import combat.robot.PublicStack;
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
    
    private static String name;
   
    private static char Log[];

    
    
    public Robot(String chosenName, int posX, int posY) {
		super();
		this.name = chosenName;
		this.energy = 10;
		this.posX = posX;
		this.posY = posY;
		this.d = 0;
		this.c = 0;
		this.Log = new char[100];
		initBot();
	}
 
    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }
    
   
    public char getCommandData(int pos) {
    	return Log[pos];
    }
    public char[] getLog() {
    	return Log;
    }
    public String getName() {
    	return name;
    }
    
    public static void initBot() {
    	File robotFile= new File("U:\\git\\projet-java-objectif-mars\\files\\combat\\bots\\" +name+ ".txt");
    		
    		try {
    			BufferedReader r = new BufferedReader(new FileReader(robotFile));
    			char ch;
    			while((ch=(char)r.read())!=' ');
    			
    			while((ch=((char)r.read()))!= '\n'){
    				 if (Character.isUpperCase(ch)){
    		                ch =Character.toLowerCase(ch);
    		         }
    			    		  Log[c] = ch;
    			    		  c++;
    			    		  d++;
    			}
    			
    			
    		} catch(Exception e) {
   	         // if any error occurs
   			e.printStackTrace();}
 	
    }
    	
    
    

    private void executeCommand(char command){
    	char x,y;
    	switch(command) {
    	  case 'p':
    		  PublicStack.stack(getCommandData(d));
    	    break;
    	  case 'g':
    	    y = PublicStack.getStack(PublicStack.getP());
    	    x = PublicStack.getStack(PublicStack.getP()-1);
    	    x = (char)(x%2);
    	    x &= (y%2);
    	    
    	    PublicStack.stack(x);
    	    break;
    	    
    	  case 'd':
      	    d = PublicStack.unStack();
      	  break;
    	  case 'm':
      	   Log[d] = PublicStack.unStack();
      	   break;
    	  case 'k':
    		  c = d;
        	   d =0;
      	    break;
    	  case 'y':
    		  switch(getCommandData(d))
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
        		  break;
    		  case '%' :
        		  break;
    		  case '&' :
    			 
        		  break;
    		  }
      	    break;
    	  case 'i':
      	    x = PublicStack.unStack();
      	    y = PublicStack.unStack();
      	    
      	    break;
    	  default:
    	    
    	}

    	
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
			if(map.moveContent(posX, posY, posX, posY-1, this))
				posY -=1;
			break;
		case WEST :
			if(map.moveContent(posX, posY, posX-1, posY, this))
				posX -=1;
			break;
		case SOUTH :
			if(map.moveContent(posX, posY, posX, posY+1, this))
				posY +=1;
			break;
		case EAST :
			if(map.moveContent(posX, posY, posX+1, posY, this))
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
		this.energy += value;
		if(this.energy > 10)
			this.energy = 10;
		else if(this.energy <= 0)
			this.destroyRobot();
	}
	
	public void destroyRobot() {
		//TODO
	}
    

 

 
 
}



