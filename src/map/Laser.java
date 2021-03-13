package map;

public class Laser {
    private String name; 
    private int maxDuration; 
    private int currentDuration; 
    private int efficacite;

    public Laser(String name, int maxDuration, int efficacite){
        this.name = name; 
        this.maxDuration = maxDuration; 
        this.efficacite = efficacite;
    }

	public String getName() {
		return this.name;
	}

	public int getMaxDuration() {
		return this.maxDuration;
	}

	public int getCurrentDuration() {
		return this.currentDuration;
	}

	public void setCurrentDuration(int currentDuration) {
		this.currentDuration = currentDuration;
	}

	public int getEfficacite() {
		return this.efficacite;
    }
    
    public void loseDuration(int points){
        this.setCurrentDuration(this.getCurrentDuration() - points);
    }
 

}
