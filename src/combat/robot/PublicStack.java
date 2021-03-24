package combat.robot;

public class PublicStack {
	private static int stack[];
	private static int p;
	
	
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
		return unstackedValue;
	}
	
}
