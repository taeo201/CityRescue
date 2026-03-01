package cityrescue;

import cityrescue.enums.*;
import cityrescue.exceptions.*;

public class CityRescueImpl implements CityRescue {
    int tick = 0;
    CityMap map;
    Station[] stations;
    Unit[] units;
    Incident[] incidents;

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
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void removeObstacle(int x, int y) throws InvalidLocationException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int addStation(String name, int x, int y) throws InvalidNameException, InvalidLocationException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits)
            throws IDNotRecognisedException, InvalidCapacityException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getStationIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
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
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
