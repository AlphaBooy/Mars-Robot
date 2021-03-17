package robot;

public class Laser {
    private String name;
    private int power;

    public Laser(String name, int power) {
        this.name = name;
        this.power = power;
    }

    public Laser() {
        this.name = "Default Laser";
        this.power = 100;
    }

    @Override
    public String toString() {
        return "Laser{" +
                "name='" + name + '\'' +
                ", power=" + power +
                '}';
    }
}
