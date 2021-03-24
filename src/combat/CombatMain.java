package combat;
import combat.map.CombatMap;
import combat.robot.Robot;

public class CombatMain {

	public static void main(String[] args) {
Robot Discovery = new combat.robot.Robot("Curiosity",10,5);
        
		CombatMap map = CombatMap.getInstance();
		
		map.DiplayMap();
		
	}

}
