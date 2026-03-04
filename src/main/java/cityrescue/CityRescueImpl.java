package cityrescue;

import java.util.Arrays;

import cityrescue.enums.*;
import cityrescue.exceptions.*;

public class CityRescueImpl implements CityRescue {
    int tick = 0;
    CityMap map;
    Station[] stations;
    Unit[] units;
    Incident[] incidents;
    // Counters (to store current number of items)
    int stationsCounter = 0;
    int unitsCounter = 0;
    int incidentsCounter = 0;
    // ID Counters (used to generate new ids)
    int stationIdCounter = 0;
    int unitIdCounter = 0;

    /**
     * Initialises the CityRescue class by resetting state variables to their
     * initial values and creating a map of the specified width and
     * height (which must be positive integers).
     * 
     * @param width  the positive width of the map
     * @param height the positive height of the map
     * @throws InvalidGridException if width or height is negative or zero
     */
    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        // Guard clause to check preconditions
        if (width <= 0 || height <= 0) {
            throw new InvalidGridException(
                    "Invalid grid size (width and height) specified. Width and height must be greater than zero");
        }

        // Reset state variables to initial values
        tick = 0;
        map = new CityMap(width, height);
        stations = new Station[20];
        units = new Unit[50];
        incidents = new Incident[200];
        // Reset counters
        stationsCounter = 0;
        unitsCounter = 0;
        incidentsCounter = 0;
        // Reset ID counters
        stationIdCounter = 0;
    }

    /**
     * Returns width and height of the active map/grid.
     * 
     * @return Array containing {width, height}
     */
    @Override
    public int[] getGridSize() {
        return new int[] { map.mapWidth, map.mapHeight };
    }

    @Override
    public void addObstacle(int x, int y) throws InvalidLocationException {
        map.addObstacle(x, y);
    }

    @Override
    public void removeObstacle(int x, int y) throws InvalidLocationException {
        map.removeObstacle(x, y);
    }

    /**
     * Creates a new station and adds it to the simulation.
     * 
     * @param name the name of the station (must not be an empty string)
     * @param x,y  the position of the station
     * @return the stationId of the new station
     * @throws InvalidNameException     if the given name is an empty string
     * @throws InvalidLocationException if the given location (x and y) is blocked
     *                                  by an obstacle or is out of bounds
     */
    @Override
    public int addStation(String name, int x, int y) throws InvalidNameException, InvalidLocationException {
        // Guard clauses to check preconditions
        if (name == "") {
            throw new InvalidNameException("Name must not be an empty string");
        }
        try {
            if (map.obstacles[x][y]) {
                throw new InvalidNameException("This location is blocked by an obstacle");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidNameException("Location out of bounds");
        }

        Station station = new Station(++stationIdCounter, name, x, y);
        // TODO: Try to move this to a generic method since it is used a lot. It needs
        // to be able to take multiple different object types.
        for (int i = 0; i < stations.length; i++) {
            if (stations[i] == null) {
                stations[i] = station;
                stationsCounter += 1;
                break;
            }
        }
        return station.stationId;
    }

    /**
     * Removes a given station from the simulation as long as it has no units
     * assigned to it.
     * 
     * @param stationId the ID of the station to be deleted
     * @throws IDNotRecognisedException if no station exists with the given
     *                                  stationId
     * @throws IllegalStateException    if the station still has units assigned to
     *                                  it
     */
    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
        // Search for a station with the given ID
        Station station = null;
        int stationIndex = 0;
        for (int i = 0; i < stations.length; i++) {
            if (stations[i].stationId == stationId) {
                station = stations[i];
                stationIndex = i;
                break;
            }
        }
        // Guard clause to check if station exists
        if (station == null) {
            throw new IDNotRecognisedException("Station with given stationId does not exist");
        }
        // Guard clause to check if station owns any units
        // TODO: Check if station owns any units before deleting

        // Delete station
        stations[stationIndex] = null;
    }

    /**
     * Updates a given station's unit capacity.
     * 
     * @param stationId the ID of the station to be updated
     * @param maxUnits  the new capacity of the station. Must be greater than zero
     *                  and the current number of units assigned to the station
     * @throws IDNotRecognisedException if no station exists with the given
     *                                  stationId
     * @throws InvalidCapacityException if the capacity is not greater than zero or
     *                                  less than the current number of units
     *                                  assigned to the station
     */
    @Override
    public void setStationCapacity(int stationId, int maxUnits)
            throws IDNotRecognisedException, InvalidCapacityException {
        // Use utility function to find station
        Station station = getStation(stationId);
        station.setCapacity(maxUnits);
    }

    // Private utility function since functionality is used in multiple methods
    private Station getStation(int stationId) throws IDNotRecognisedException {
        // Search for a station with the given ID
        Station station = null;
        for (int i = 0; i < stations.length; i++) {
            if (stations[i].stationId == stationId) {
                station = stations[i];
                break;
            }
        }
        // Guard clause to check if station exists
        if (station == null) {
            throw new IDNotRecognisedException("Station with given stationId does not exist");
        }
        return station;
    }

    // Private utility function since functionality is used in multiple methods
    private Unit getUnit(int unitId) throws IDNotRecognisedException {
        // Search for a unit with the given ID
        Unit unit = null;
        for (int i = 0; i < units.length; i++) {
            if (units[i].unitId == unitId) {
                unit = units[i];
                break;
            }
        }
        // Guard clause to check if station exists
        if (unit == null) {
            throw new IDNotRecognisedException("Unit with given unitId does not exist");
        }
        return unit;
    }

    /**
     * Provides a list of all current station IDs.
     * 
     * @return a list of station IDs sorted in ascending order
     */
    @Override
    public int[] getStationIds() {
        int[] stationIds = new int[stationsCounter];
        int stationIdsCounter = 0;
        for (Station station : stations) {
            stationIds[stationIdsCounter] = station.stationId;
            stationIdsCounter++;
        }
        Arrays.sort(stationIds);
        return stationIds;
    }

    /**
     * Creates a new unit and adds it to the simulation.
     * 
     * @param stationId the ID of the unit's assigned station
     * @param type      the UnitType enum of the unit
     * @return the unitId of the new unit
     * @throws IDNotRecognisedException if the ID of the unit's assigned station can
     *                                  not be found
     * @throws InvalidUnitException     if an invalid/null unit type is given
     * @throws IllegalStateException    if the unit's assigned station has no free
     *                                  capacity
     */
    @Override
    public int addUnit(int stationId, UnitType type)
            throws IDNotRecognisedException, InvalidUnitException, IllegalStateException {
        Station station = getStation(stationId);
        // TODO: Check if unit type is not null
        // TODO: Handle creating different unit subclasses based on type parameter

        Unit unit = new Unit(++unitIdCounter, station.stationId, station.x, station.y);
        // Add unit to units list
        for (int i = 0; i < units.length; i++) {
            if (units[i] == null) {
                units[i] = unit;
                unitsCounter += 1;
                break;
            }
        }

        // Add unit to selected station
        station.addUnit(unit);

        return unit.unitId;
    }

    /**
     * Removes a given unit from the simulation as long as it is not busy.
     * 
     * @param unitId the ID of the unit to be deleted
     * @throws IDNotRecognisedException if no unit exists with the given unitId
     * @throws IllegalStateException    if the unit is not free (enroute or at
     *                                  scene)
     */
    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        // Search for a unit with the given ID
        Unit unit = null;
        int unitIndex = 0;
        for (int i = 0; i < units.length; i++) {
            if (units[i].unitId == unitId) {
                unit = units[i];
                unitIndex = i;
                break;
            }
        }
        // Guard clause to check if unit exists
        if (unit == null) {
            throw new IDNotRecognisedException("Station with given stationId does not exist");
        }
        // Guard clause to check if unit is not busy
        if (unit.status == UnitStatus.EN_ROUTE || unit.status == UnitStatus.AT_SCENE) {
            throw new IllegalStateException("The unit is not free (enroute or at scene)");
        }

        // Get unit's assigned station
        Station station = getStation(unit.stationId);

        // Remove from global units list
        units[unitIndex] = null;
        unitsCounter--;

        // Remove from station units list
        station.removeUnit(unitId);
    }

    /**
     * Moves a unit to a new station as long as it is idle.
     * 
     * @param unitId       the ID of the unit to be moved
     * @param newStationId the ID of the station the unit is to be moved to
     * @throws IDNotRecognisedException if no unit or station exists with the given
     *                                  unitId
     * @throws IllegalStateException    if the given unit is not idle or the new
     *                                  station does not have space
     */
    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        Unit unit = getUnit(unitId);
        Station station = getStation(newStationId);
        // Guard clause to check if unit is idle
        if (unit.status != UnitStatus.IDLE) {
            throw new IllegalStateException("A unit must be idle for it to be transferred");
        }

        station.removeUnit(unitId);
        station.addUnit(unit);
        unit.x = station.x;
        unit.y = station.y;
    }

    /**
     * Either sets a given unit to the idle status or puts it out of service if
     * outOfService is true. A unit can only be set as out of service if it is
     * already idle.
     * 
     * @param unitId       the ID of the unit to be updated
     * @param outOfService this will set the unit to out of service rather than idle
     *                     if true
     * @throws IDNotRecognisedException if no unit exists with the given unitId
     * @throws IllegalStateException    if an attempt is made to put a unit out of
     *                                  service when it is not idle
     */
    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService)
            throws IDNotRecognisedException, IllegalStateException {
        Unit unit = getUnit(unitId);
        // Guard clause
        if (outOfService && unit.status != UnitStatus.IDLE) {
            throw new IllegalStateException("A unit must be idle for it to be put out of service");
        }

        if (outOfService) {
            unit.status = UnitStatus.OUT_OF_SERVICE;
        }
        if (!outOfService) {
            unit.status = UnitStatus.IDLE;
        }

    }

    /**
     * Provides a list of all current unit IDs.
     * 
     * @return a list of units IDs sorted in ascending order
     */
    @Override
    public int[] getUnitIds() {
        int[] unitIds = new int[unitsCounter];
        int unitIdsCounter = 0;
        for (Unit unit : units) {
            unitIds[unitIdsCounter] = unit.unitId;
            unitIdsCounter++;
        }
        Arrays.sort(unitIds);
        return unitIds;
    }

    /**
     * Returns a formatted string containing information about the given unit.
     * 
     * @param unitId the ID of the unit to be updated
     * @return a formatted string describing the unit
     * @throws IDNotRecognisedException if no unit exists with the given unitId
     */
    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        Unit unit = getUnit(unitId);
        // TODO: Output unit type (when subclasses implemented)
        // TODO: Output current unit incident (when implemented)
        String status = String.format("U#%d TYPE=%s HOME=%d LOC=(%d,%d) STATUS=%s INCIDENT=%d", unit.unitId,
                "NOT IMPLEMENTED", unit.stationId, unit.x, unit.y, unit.status, "NOT IMPLEMENTED");
        // TODO: Implement 'work' property when incidents are added
        // if (condition) {
        // status += String.format(" WORK=%d", "NOT IMPLEMENTED");
        // }

        return status;
    }

    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y)
            throws InvalidSeverityException, InvalidLocationException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity)
            throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getIncidentIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void dispatch() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void tick() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String getStatus() {
        String status = "";
        status += "TICK=" + tick;
        status += String.format("\nSTATIONS=%d UNITS=%d INCIDENTS=%d OBSTACLES=%d",
                stationsCounter, unitsCounter,
                incidentsCounter, map.obstaclesCounter);
        status += "\nINCIDENTS";
        // TODO: Implement listing of incidents
        // List all units
        status += "\nUNITS";
        for (int unitId : getUnitIds()) {
            try {
                status += ("\n" + viewUnit(unitId));
            } catch (IDNotRecognisedException e) {
                System.out.println(String.format("U#%d UNIT NOT FOUND", unitId));
            }
        }

        return status;
    }
}
