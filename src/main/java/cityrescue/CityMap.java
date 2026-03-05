package cityrescue;

import cityrescue.exceptions.InvalidLocationException;

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

    public void addObstacle(int x, int y) throws InvalidLocationException {
        try {
            obstacles[x][y] = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidLocationException("Location out of bounds");
        }
    }

    public void removeObstacle(int x, int y) throws InvalidLocationException {
        try {
            obstacles[x][y] = false;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidLocationException("Location out of bounds");
        }
    }

    public boolean checkMoveIsLegal(int x, int y) {
        if (x < 0 || x >= mapWidth || y < 0 || y >= mapHeight) return false;
        return !obstacles[x][y];
    }
}
