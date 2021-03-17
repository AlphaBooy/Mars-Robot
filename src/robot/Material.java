package robot;

public class Material extends Battery {

    private Object object;
    private int cost;

    public Material(Object object, int cost) {
        this.object = object;
        this.cost = cost;
    }

    public static Material[] getDefault() {
        return new Material[] {
                new Material(new Battery(), 200),
                new Material(new Laser(), 200)
        };
    }

    public String toString() {
        return "[Object: " + object.toString() + "; Cost: " + cost + "]";
    }
}
