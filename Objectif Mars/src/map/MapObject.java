package map;

public class MapObject {
    /** Position of the object within a line */
    private int posX;
    /** Position of the object within a collumn */
    private int posY;
    /** Name that describe the object (it's used later on to display each element */
    private String name;
    /** Char representation of the object that can be stored in a text file */
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
     * Create an object without specifying the name. It'll be given automatically based on it's char representation
     * @param posX the position of the object on the X axis (within a line)
     * @param posY the position of the object on the Y axis (within a collumn)
     * @param mapRepresentation The character representation of the object on the map
     */
    public MapObject(int posX, int posY, char mapRepresentation) {
        this.posX = posX;
        this.posY = posY;
        this.mapRepresentation = mapRepresentation;
        this.name = getName(mapRepresentation);
    }

    /**
     * @return the name of the object (ex: "sable")
     */
    public String getName() {
        return name;
    }

    /**
     * Return the name of the element described as it's char representation
     * @param representation the char representation of an element on the map
     * @return the name of the given element
     */
    public String getName(char representation) {
        switch (representation) {
            case '*': return "Diamant";
            case '%': return "Rocher dur";
            case '.': return "Sable";
            case ':': return "Gres";
            case '=': return "Roche volcanique";
            case '-': return "Petit rocher";
            case '#': return "Rubis";
            case '@': return "Base";
        }
        return "NC";
    }

    /**
     * Return a printable String that can be displayed to represent a MapObject
     * Ex: Base [x:2, y:5, @]
     * @return a String that represent the given MapObject
     */
    public String toString() {
        return name + " [x:" + posX + ", y:" + posY + ", " + mapRepresentation + "]";
    }

    /**
     * Get the MapObject from it's coordinates
     * @param posX It's position on the X axis
     * @param posY It's position on the Y axis
     * @return the MapObject found on the given position, null otherwise
     */
    public static MapObject getObject(int posX, int posY) {
        return null;
    }
}
