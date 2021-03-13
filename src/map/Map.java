package map;

public class MapObject {
    private char[][] representation;
    private int sizeX;
    private int sizeY;

    public Map(char[][] representation) {
        sizeX = representation[0].length;
        sizeY = representation.length;
    }

    public static String toString() {
        String result = "";
        for (int i = 0; i < this.sizeY; i++) {
            for (int j = 0; j < this.sizeX; j++) {
                result += this.representation[i][j];
            }
        }
        return result;
    }
}
