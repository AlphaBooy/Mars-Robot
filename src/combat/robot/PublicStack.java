package combat.robot;

public class PublicStack {
	private static char stack[];
	private static int p;
	
	
	public static int getP() {
		return p;
	}
	
	public static char getStack(int pos) {
		return stack[pos];
	}
	public static void setStack(char value) {
		stack[p] = value;
	}
	public static void stack(char value) {
		stack[p]=value;
		p++;	
	}
	public static char unStack() {
		char unstackedValue;
		unstackedValue = stack[p];
		p--;
		return unstackedValue;
	}
	
}
