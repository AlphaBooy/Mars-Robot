package map;

/**
* Class Equipement qui permet de stocker la batterie et le laser actuelle du robot
* Il faut faire en sorte que quand on charge le fichier de config, 
* dedans il y est le laser et la batterie de base.
 */
public class Equipement {
    private Laser laser; 
    private Batterie batterie;

    public Laser getLaser() {
        return laser;
    }

    public Batterie getBatterie() {
        return batterie;
    }

    public void setLaser(Laser laser) {
        this.laser = laser;
    }

    public void setBatterie(Batterie batterie) {
        this.batterie = batterie;
    }
}
