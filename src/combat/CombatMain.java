package combat;
import combat.map.CombatMap;
import combat.robot.Robot;

public class CombatMain {

	public static void main(String[] args) {
        
		CombatMap map = CombatMap.getInstance();
		
		map.DiplayMap();
		
	}

}
