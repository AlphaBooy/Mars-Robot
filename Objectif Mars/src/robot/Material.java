package robot;

public class Material {

    private String nom;
    private int cost;
    private int initial_value;

    public Material(String nom, int cost, int initial_value) {
        this.nom = nom;
        this.cost = cost;
        this.initial_value = initial_value;
    }

    public static Material[] getDefault() {
        return new Material[] {
                new Material("Default Battery", 200, 100),
                new Material("Default Laser", 200, 100)
        };
    }
}
