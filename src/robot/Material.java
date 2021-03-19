package robot;

public class Material extends Battery {

    private Object object;
    private int cost;
    private double time;

    /**
     * A Material object is basically an object of the game (Battery / Laser / ...) with a cost and an installation time
     * Each material can be "plugged" into the robot. This cost "cost" and last for "time" seconds
     * @param object The object that can be sold to the robot
     * @param cost The cost of the material within the shop
     * @param time The time needed to build the component on the robot
     */
    public Material(Object object, int cost, int time) {
        this.object = object;
        this.cost = cost;
        this.time = time;
    }

    /**
     * @return the default material list (composed of the default battery and the default laser)
     */
    public static Material[] getDefault() {
        return new Material[] {
                new Material(new Battery(), 200, 3),
                new Material(new Laser(), 200, 3)
        };
    }

    public String toString() {
        return "[Object: " + object.toString() + "; Cost: " + cost + "]";
    }
}
