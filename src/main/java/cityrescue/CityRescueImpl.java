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
        // Guard clauses to check if capacity is valid
        if (maxUnits <= 0) {
            throw new InvalidCapacityException("Given station capacity must be greater than zero");
        }
        // TODO: Add guard clause to check if current number of units is less than new
        // capacity
        station.capacity = maxUnits;
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

    @Override
    public int addUnit(int stationId, UnitType type)
            throws IDNotRecognisedException, InvalidUnitException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService)
            throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getUnitIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
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
        status += "\nUNITS";
        // TODO: Implement listing of units

        return status;
    }
}
