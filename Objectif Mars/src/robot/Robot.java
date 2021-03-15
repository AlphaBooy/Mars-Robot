package robot;


public class Robot {
    /** A direction that can be one of {NORTH , SOUTH, EAST, WEST} */
    private Direction direction;
    /** A complete list of the material carried by the robot */
    private Material[] material;
    /** The X position of the robot (supposed to evolve) */
    private int posX;
    /** The Y position of the robot (supposed to evolve) */
    private int posY;
    /** The battery of the robot, if it hit 0, the robot stops */
    private Battery battery;

    public Robot(Material[] material, int posX, int posY) {
        this.material = material;
        this.posX = posX;
        this.posY = posY;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Material[] getEquipement() {
        return this.material;
    }

    public int getPosX() {
        return this.posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void rotate(Direction direction){
        this.direction = direction;
    }

    public void move() {
        /* Handle the progress of the robot on the map with it's position */
        if (this.getDirection() == Direction.SOUTH) {
            posY--;
        } else if (this.getDirection() == Direction.EAST) {
            posX++;
        } else if (this.getDirection() == Direction.WEST) {
            posX--;
        } else if (this.getDirection() == Direction.NORTH) {
            posY++;
        }
    }
}
