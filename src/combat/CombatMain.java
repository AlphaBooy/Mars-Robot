package combat;
import combat.map.CombatMap;
import combat.robot.Robot;

public class CombatMain {

	public static void main(String[] args) {
Robot Discovery = new combat.robot.Robot("Curiosity",10,5);
        
        System.out.printf("Nom : %s Position : %d %d \n Log :",Discovery.getName(),Discovery.getPosX(),Discovery.getPosY());
        for(int i = 0;i < 17;i++) {
        System.out.printf("%c",Discovery.getCommandData(i));
        }
        System.out.printf("\n");
		CombatMap map = CombatMap.getInstance();
		
		map.DiplayMap();
		
	}

}
