package robot;

public class Laser {
    private String name;
    private double power;

    private static final int MIN_POWER = 1;

    public Laser(String name, double power) {
        this.name = name;
        this.power = power;
    }

    public Laser() {
        this.name = "Default Laser";
        this.power = 100;
    }

    public double getPower() {
        return power;
    }

    public void loosePower(long time, Double blunt) {
        this.power -= time * blunt;
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
