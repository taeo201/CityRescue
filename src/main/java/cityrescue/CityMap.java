package cityrescue;

public class CityMap {
    int mapWidth;
    int mapHeight;
    boolean[][] obstacles;
    int obstaclesCounter = 0;

    public CityMap(int width, int height) {
        // Stores provided grid dimensions and initialises array to store
        // locations where an obstacle is present
        mapWidth = width;
        mapHeight = height;
        obstacles = new boolean[width][height];
    }

    public boolean checkMoveIsLegal() {
        // TODO: Implement checkMoveIsLegal
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
