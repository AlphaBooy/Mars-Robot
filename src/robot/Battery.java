package robot;

public class Battery {

    private String name;
    private int capacity;
    private int level;

    /**
     * Constructor that build the battery object that can be assign to the robot at any time.
     * This battery is full.
     * By definition, a battery will lose power over time (with each movements and actions performed)
     * and the robot will stop (you will loose the game) whenever the battery level reach 0.
     * @param name The (unique) name that'll describe the battery (you can perform action thanks to this identifier).
     * @param capacity The amount of power the battery can store at it maximum level
     */
    public Battery(String name, int capacity) {
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
    public Battery(String name, int capacity, int level) {
        if (level > capacity) // To much power inside the battery
            this.explode();   // The robot can't handle it so it'll explode
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
    public void chargeBattery(int power) throws InterruptedException {
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
    public void useBattery(Double power) {
        if (this.level - power <= 0)
            this.emptyBattery(); //The battery is empty, the game is over
        // Use one "charging unit" to decrease the battery level
        this.level -= power;
        System.out.println(this.level);
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
        //TODO game over
    }

    private void explode() {
        // (easter egg)
        // The battery is in overloading !!! Robot will explode now (the game is over)
        //TODO game over
    }
}
