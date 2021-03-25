package combat.robot;

import combat.map.CombatMap;
import combat.map.IsNotARobotException;

public class PublicStack {
	private static int stack[];
	private static int p;
	
	private static volatile PublicStack instance = null;
	 
	// Since PublicStack is unique and shared between each robot i decided to use a singleton
	 public static PublicStack getInstance() {
	    	if(instance == null) {
	    		//The keyword synchronized is only useful for when several threads try to get a new singleton at the same time,
	    		//it should not happen but it is imperative to prevent this case from happening
	    		synchronized(PublicStack.class) {
	    			if(instance == null) {
	    	    		instance =  new PublicStack();
	    			}
	    		}
	    	}
	    	return instance;
	 }
	public PublicStack() {
		this.stack = new int[100];
		this.p = 0;
	}
	public static int getP() {
		return p;
	}
	
	public static int getStack(int pos) {
		return stack[pos];
	}
	public static void setStack(int value) {
		stack[p] = value;
	}
	public static void stack(int value) {
		stack[p]=value;
		p++;	
	}
	public static int unStack() {
		int unstackedValue;
		unstackedValue = stack[p];
		p--;
		if(p < 0)
		{
			unstackedValue = -1;
			p = 0;
		}
		return unstackedValue;
	}
	
}
