package combat.robot;



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
	/** 
	 * @return Return the stack's P value
	 */
	public  int getP() {
		return this.p;
	}
	/** 
	 * @return Return the stack's value at pos value
	 *  @param value to select the position of the value we want to return
	 */
	public int getStack(int pos) {
		return this.stack[pos];
	}
	/** 
	 * @param the value to set at the stack's current position
	 */
	public void setStack(int value) {
		this.stack[p] = value;
	}
	/** 
	 *  Set the stack's current value
	 * @param the value to put on the stack at the current position
	 */
	public void stack(int value) {
		this.stack[p]=value;
		this.p++;	
	}
	/**
	 * @return Return the stack's current value and decrement the P value
	 */
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
