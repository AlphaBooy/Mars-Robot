package map;

/**
 * Batterie class
 */
public class Batterie {
    private int maxLife; 
    private String name; 
    private int currentLife; 

    public Batterie(String name, int maxLife){
        this.name = name; 
        this.maxLife = maxLife; 
    }

    public void setCurrentLife(int currentLife) {
        this.currentLife = currentLife;
    }

    public int getCurrentLife() {
        return currentLife;
    }

    public void loseBatterie(int points){
        this.setCurrentLife(this.getCurrentLife() - points);
    }


}
