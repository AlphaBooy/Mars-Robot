package robot;

public class Material {

    private String name;
    private int cost;
    private int initial_value;

    public Material(String nom, int cost, int initial_value) {
        this.name = name;
        this.cost = cost;
        this.initial_value = initial_value;
    }

    public static Material[] getDefault() {
        return new Material[] {
                new Material("Default Battery", 200, 100),
                new Material("Default Laser", 200, 100)
        };
    }

    public String toString() {
        return "[name: " + name + "; cost: " + cost + "; initial value: " + initial_value + "]";
    }
}
