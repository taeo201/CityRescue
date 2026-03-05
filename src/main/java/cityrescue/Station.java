package cityrescue;

import java.util.Arrays;

import cityrescue.exceptions.InvalidCapacityException;

public class Station {
    int stationId;
    String name;
    int x;
    int y;

    int capacity = 6; // Default station capacity (arbitrary because not given in spec but required
                      // for tests)
    Unit[] units = new Unit[capacity];
    int unitsCounter = 0;

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

    public void addUnit(Unit unit) throws IllegalStateException {
        if (unitsCounter >= capacity) {
            throw new IllegalStateException("The station has no free space");
        }

        for (int i = 0; i < units.length; i++) {
            if (units[i] == null) {
                units[i] = unit;
                unit.stationId = stationId;
                unitsCounter += 1;
                break;
            }
        }
    }

    public void removeUnit(int unitId) {
        int unitIndex = 0;
        for (int i = 0; i < units.length; i++) {
            if (units[i] != null && units[i].unitId == unitId) {
                unitIndex = i;
                break;
            }
        }
        units[unitIndex] = null;
        unitsCounter--;
    }
}
