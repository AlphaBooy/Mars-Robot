package combat.robot;
import java.io.BufferedReader;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileReader;
import java.util.Scanner; // Import the Scanner class to read text files
import map.Map;
import map.MapObject;
import combat.robot.PublicStack;

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
    		  break;
    		  case '!' :
        		  break;
    		  case '"' :
        		  break;
    		  case '#' :
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
     * Destroy a map object to get loot an ore that can be sold at a given value.
     * @param map the map where the robot evolve
     */
 

 
 
}



