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
    private int d;
    
    private String name;
   
    private static char Log[];

    
    
    public Robot(int energy, int posX, int posY, int d) {
		super();
		this.name = "Curiosity";
		this.energy = 10;
		this.posX = 0;
		this.posY = 0;
		this.d = 0;
		this.c = 0;
	}



	public static final String PATH_TO_IMAGE = "textures/robot.png";
    
    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }
    
    private char getData(int pos){
    	return Log[pos];
    	
    }
    private char getCommand(int pos) {
    	return Log[pos];
    }
    public static void readBot() {
    	File directory=new File("U:\\git\\projet-java-objectif-mars\\files\\combat\\bots");
    	String[] pathnames;
    	pathnames = directory.list();
    	    int fileCount=directory.list().length;
    	for(String pathname:pathnames)
    	{
    		
    		
    		try {
    			BufferedReader r = new BufferedReader(new FileReader(pathname));
    			int ch;
    			while((ch=r.read())!=-1){
    				 if (Character.isUpperCase(ch)){
    		                Character.toLowerCase(ch);
    		         }
    					switch(ch) {
    			    	  case 'p':
    			    		  Log[c] = 'p';
    			    	    break;
    			    	  case 'g':
    			    		  Log[c] = 'g';
    			    	    break;
    			    	  case 'd':
    			    		  Log[c] = 'd';
    			      	  break;
    			    	  case 'm':
    			    		  Log[c] = 'm';
    			      	   break;
    			    	  case 'k':
    			    		  Log[c] = 'k';
    			      	    break;
    			    	  case 'y':
    			    		  Log[c] = 'y';
    			      	    break;
    			    	  case 'i':
    			    		  Log[c] = 'i';
    			      	    break;
    			    	  default:
    					}  
    			}
    		
    		} catch(Exception e) {
   	         // if any error occurs
   			e.printStackTrace();}
 	
    	}
    	
    }
    
    

    private void executeCommand(char command){
    	char x,y;
    	switch(command) {
    	  case 'p':
    		  PublicStack.stack(getData(d));
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
    		  switch(getData(d))
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



