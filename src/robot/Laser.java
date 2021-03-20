package robot;

public class Laser {
    private String name;
    private int power;

    private static final int MIN_POWER = 1;

    public Laser(String name, int power) {
        this.name = name;
        this.power = power;
    }

    public Laser() {
        this.name = "Default Laser";
        this.power = 100;
    }

    public int getPower() {
        return power;
    }

    public void loosePower(int time, int hardness) {
        this.power -= time * hardness;
        if (this.power < MIN_POWER)
            this.power = MIN_POWER;
    }

    @Override
    public String toString() {
        return "Laser{" +
                "name='" + name + '\'' +
                ", power=" + power +
                '}';
    }
}
