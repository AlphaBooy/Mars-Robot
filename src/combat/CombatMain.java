package combat;
import combat.map.CombatMap;

public class CombatMain {

	public static void main(String[] args) {
		
		CombatMap map = new CombatMap("files/maps/zone_combat_1.txt",10,10);
		map.DiplayMap();
	}

}
