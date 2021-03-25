package combat;
import combat.map.CombatMap;
import combat.robot.*;
public class CombatMain {

	public static void main(String[] args) {

		PublicStack PS = PublicStack.getInstance();

		CombatMap map = CombatMap.getInstance();
		map.DisplayMap();   
		while(map.turn() != 's');
		
		
	}

}
      