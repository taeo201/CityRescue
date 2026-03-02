package cityrescue;

public class Station {
    int stationId;
    String name;
    int capacity;
    int x;
    int y;

    public Station(int stationId, String name, int x, int y) {
        this.stationId = stationId;
        this.name = name;
        this.x = x;
        this.y = y;
    }
}
