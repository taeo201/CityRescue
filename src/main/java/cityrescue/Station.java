package cityrescue;

import java.util.Arrays;

import cityrescue.exceptions.InvalidCapacityException;

public class Station {
    int stationId;
    String name;
    int x;
    int y;

    Unit[] units = new Unit[0];
    int unitsCounter = 0;
    int capacity;

    public Station(int stationId, String name, int x, int y) {
        this.stationId = stationId;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public void setCapacity(int maxUnits) throws InvalidCapacityException {
        // Guard clauses to check if capacity is valid
        if (maxUnits <= 0) {
            throw new InvalidCapacityException("Given station capacity must be greater than zero");
        }
        // TODO: Add guard clause to check if current number of units is less than new
        // capacity
        // TODO: Proper way to shrink array if there are free spaces from removed items
        units = Arrays.copyOf(units, maxUnits);
        System.out.println(units.length);
        this.capacity = maxUnits;
    }
}
