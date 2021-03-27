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
    /** The robot's C record */
    private int c;
    /** The robot's D record */
    private int d;
    /** The robot's command log */
    private int commandLog[];
    
    private String name;
   

    

    public Robot(String chosenName, int posX, int posY) {
		this.name = chosenName;
		this.energy = 10;
		this.posX = posX;
		this.posY = posY;
		this.d = 0;
		this.c = 0;
		this.commandLog = new int[100];
		initBot(chosenName);
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
    
    public int AND(int a,int b) {
        int value1 = a;
        int value2 = b;
        int result = value1 & value2;
        return result;
    }
    

    public void executeCommand(){
    	int x,y,z;
    	CombatMap map = CombatMap.getInstance();
    	PublicStack pStack = PublicStack.getInstance();
    	switch(getCommand()) {
    	  case 'p':
    		  pStack.stack(this.d);
    		  System.out.printf("Action : p\n");
    	    break;
    	  case 'g':
    	    y = pStack.unStack();
    	    
    	    x = pStack.unStack();
    	    
    	    x = (x%2);
    	    y = (y%2);
    	    z= AND(x,y);
    	    z = (~z);
    	    System.out.printf("Action : g\n");
    	    
    	    
    	    pStack.stack(z);
    	    break;
    	    
    	  case 'd':
      	    this.d = pStack.unStack();
      	  System.out.printf("Action : d\n");
      	  break;
    	  case 'm':
      	   this.d = pStack.unStack();
      	 System.out.printf("Action : m\n");
      	   break;
    	  case 'k':
    		   d =c;
    		   //d = 0;
        	   System.out.printf("Action : k\n");
      	    break;
    	  case 'y':
    		  switch(' ')
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
    			  System.out.printf("Action : y %\n");
        		  break;
    		  case '&' :
    			  map.destroyWalls(posX, posY);
    			  System.out.printf("Action : y &\n");
        		  break;
        	default :
        		System.out.println("Y with wrong d : " + (char)this.d + "|" + this.d);
    		  }
    		  
      	    break;
      	 
    	  case 'i':
      	    x = pStack.unStack();
      	    y = pStack.unStack();
      	  System.out.printf("Action : i\n");
      	    break;
    	  default:

    	    map.destroyRobot(posX, posY);

    	    break;
    	}
    	c++;

    	
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



