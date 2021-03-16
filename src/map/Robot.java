package map;


public class Robot {
    private Direction direction;
    private Equipement equipement;
    private int posX; 
    private int posY;  

	public Direction getDirection() {
		return this.direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Equipement getEquipement() {
		return this.equipement;
	}

	public void setEquipement(Equipement equipement) {
		this.equipement = equipement;
	}

	public int getPosX() {
		return this.posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return this.posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}


    public void changeDirection(Direction direction){
        this.direction = direction; 
    }

    /** 
     * Il manque le fait qu'il faut checker la case de deavant savoir si elle est vide ou si y'a un minerai ou autre
     * pour savoir si on peut avancer directement 
     * ou si il faut faire du minage avant d'avancer
     *
    */
    public void avancer(int steps, int cost, int costTime){
        switch (this.getDirection()) {
            case NORD:
                this.setPosY(this.getPosY() + steps);
                break;
            case SUD:
                this.setPosY(this.getPosY() - steps);
                break;
            case OUEST:
                this.setPosX(this.getPosX() - steps);
                break; 
            case EST:
                this.setPosX(this.getPosX() + steps);
                break;
            default:
                break;
        }
        try {
            Thread.sleep(costTime);
        } catch (Exception e) {
            System.out.println("Error with costTime");
        }
        this.equipement.getBatterie().loseBatterie(cost);
    }
}
