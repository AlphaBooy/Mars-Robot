package robot;

public class Battery {

    private String name;
    private double capacity;
    private double level;

    /**
     * Constructor that build the battery object that can be assign to the robot at any time.
     * This battery is full.
     * By definition, a battery will lose power over time (with each movements and actions performed)
     * and the robot will stop (you will loose the game) whenever the battery level reach 0.
     * @param name The (unique) name that'll describe the battery (you can perform action thanks to this identifier).
     * @param capacity The amount of power the battery can store at it maximum level
     */
    public Battery(String name, double capacity) {
        this.name = name;
        this.capacity = capacity;
        this.level = capacity;
    }

    /**
     * Constructor that build the object "Default Battery".
     * This object is the default battery object that spawn into the robot at the start of the simulation
     */
    public Battery() {
        this.name = "Default Battery";
        this.capacity = 200;
        this.level = 100;
    }

    /**
     * Constructor that build the object battery with a custom level of usage.
     * The battery level must be lower than the battery capacity
     * @param name The (unique) name that'll describe the battery (you can perform action thanks to this identifier).
     * @param capacity The amount of power the battery can store at it maximum level
     * @param level The initial level of power of the battery given to the robot
     */
    public Battery(String name, double capacity, double level) {
        if (level > capacity) // To much power inside the battery
            level = capacity;
        this.name = name;
        this.capacity = capacity;
        this.level = level;
    }

    /**
     * Charge a battery from it's current level to it's max capacity.
     * The battery will charge at a given speed delivered by the power supply.
     * @param power The power that is delivered by the power supply that will charge the battery
     * @throws InterruptedException If the battery charge is interrupted, handle the exception
     */
    public void chargeBattery(double power) throws InterruptedException {
        while (this.level <= this.capacity) {
            /* If you charge one second more, the battery will be full : */
            if (this.level + power > this.capacity) {
                /* So we fully charge the battery properly (so it's not over it capacity) */
                this.level = this.capacity;
                /* And we stop the charging process */
                break;
            }
            /* Simulate the time we have to wait to charge a battery */
            Thread.sleep(1000);
            /* Increase the battery level by one "charging unit" */
            this.level += power;
            System.out.println(this.level);
        }
        System.out.println("Battery fully charged !");
    }

    /**
     * Use a custom amount of power that is stored within the battery
     * @param power the amount of power drained to perform a given task
     */
    public void useBattery(double power) {
        if (this.level - power <= 0)
            this.emptyBattery(); //The battery is empty, the game is over
        // Use one "charging unit" to decrease the battery level
        this.level -= power;
    }

    public String getImageName() {
        double charge = (this.level / this.capacity) * 100;

        if (charge == 100)
            return "battery_100";
        if (charge >= 80)
            return "battery_80";
        if (charge >= 60)
            return "battery_60";
        if (charge >= 40)
            return "battery_40";
        if (charge >= 20)
            return "battery_20";
        if (charge == 0)
            return "battery_80";
        return "battery_default";
    }

    public double getLevel() {
        return level;
    }

    public double getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return "Battery{" +
                "name='" + name + '\'' +
                ", capacity=" + capacity +
                ", level=" + level +
                '}';
    }

    private void emptyBattery() {
        this.level = 0;
        System.out.println("the game is over !");
    }
}
