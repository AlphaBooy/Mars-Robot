package map;

public class MapObject {
    private int posX;
    private int posY;
    private String name;
    public char mapRepresentation;

    /**
     * Create an object that can be placed latter on a Map
     * @param posX the position of the object on the X axis (within a line)
     * @param posY the position of the object on the Y axis (within a collumn)
     * @param name Name of the object that can be displayed in order to help the user
     * @param mapRepresentation The character representation of the object on the map
     */
    public MapObject(int posX, int posY, String name, char mapRepresentation) {
        this.posX = posX;
        this.posY = posY;
        this.name = name;
        this.mapRepresentation = mapRepresentation;
    }

    /**
     * Return a printable String that can be displayed to represent a MapObject
     * Ex: Base [x:2, y:5, @]
     * @return a String that represent the given MapObject
     */
    public static String toString() {
        return name + " [x:" + posX + ", y:" + posY + ", " + mapRepresentation + "]";
    }

    /**
     * Get the MapObject from it's coordinates
     * @param posX It's position on the X axis
     * @param posY It's position on the Y axis
     * @return the MapObject found on the given position, null otherwise
     */
    public static MapObject getObject(int posX, int posY) {
        //TODO
    }
}
