package combat.robot;

import combat.map.CombatMap;
import combat.map.IsNotARobotException;

public class PublicStack {
	private  int stack[];
	private int p;
	
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
	private PublicStack() {
		this.stack = new int[100];
		this.p = 0;
	}
	public  int getP() {
		return this.p;
	}
	
	public int getStack(int pos) {
		return this.stack[pos];
	}
	public void setStack(int value) {
		this.stack[p] = value;
	}
	public void stack(int value) {
		this.stack[p]=value;
		this.p++;	
	}
	public int unStack() {
		int unstackedValue;
		unstackedValue = this.stack[p];
		this.stack[p] = 0;
		this.p--;
		if(this.p < 0)
		{

			this.p = 0;
		}
		return unstackedValue;
	}
	
}
