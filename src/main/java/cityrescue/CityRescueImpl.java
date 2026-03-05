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
    int incidentIdCounter = 0;

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
        unitIdCounter = 0;
        incidentIdCounter = 0;
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
        // Automatically throws an exception if location invalid
        checkValidLocation(x, y);

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

    // Private utility function since functionality used in multiple places
    // throws an exception directly if location is invalid with appropriate message
    private void checkValidLocation(int x, int y) throws InvalidLocationException {
        try {
            if (map.obstacles[x][y]) {
                throw new InvalidLocationException("This location is blocked by an obstacle");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidLocationException("Location out of bounds");
        }
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
            if (stations[i] == null) {
                continue;
            }
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
        if (station.unitsCounter > 0) {
            throw new IllegalStateException("The station can not be deleted because it still has units assigned to it");
        }

        // Delete station
        stations[stationIndex] = null;
        stationsCounter--;
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
            if (stations[i] != null && stations[i].stationId == stationId) {
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
            if (units[i] != null && units[i].unitId == unitId) {
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

    // Private utility function since functionality is used in multiple methods
    private Incident getIncident(int incidentId) throws IDNotRecognisedException {
        // Search for an incident with the given ID
        Incident incident = null;
        for (int i = 0; i < incidents.length; i++) {
            if (incidents[i] != null && incidents[i].incidentId == incidentId) {
                incident = incidents[i];
                break;
            }
        }
        // Guard clause to check if station exists
        if (incident == null) {
            throw new IDNotRecognisedException("Incident with given incidentId does not exist");
        }
        return incident;
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
            if (station == null) {
                continue;
            }
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
        // Create the appropriate subclass type based on specified unit type
        Unit unit;
        switch (type) {
            case AMBULANCE:
                unit = new Ambulance(++unitIdCounter, station.stationId, station.x, station.y);
                break;
            case FIRE_ENGINE:
                unit = new FireEngine(++unitIdCounter, station.stationId, station.x, station.y);
                break;
            case POLICE_CAR:
                unit = new PoliceCar(++unitIdCounter, station.stationId, station.x, station.y);
                break;
            default:
                throw new IllegalStateException("Invalid unit type given");
        }

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
            if (units[i] != null && units[i].unitId == unitId) {
                unit = units[i];
                unitIndex = i;
                break;
            }
        }
        // Guard clause to check if unit exists
        if (unit == null) {
            throw new IDNotRecognisedException("Unit with given unitId does not exist");
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

        getStation(unit.stationId).removeUnit(unitId);
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
            if (unit == null) {
                continue;
            }
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
        String status = String.format("U#%d TYPE=%s HOME=%d LOC=(%d,%d) STATUS=%s", unit.unitId,
                unit.type, unit.stationId, unit.x, unit.y, unit.status);
        switch (unit.incidentId) {
            case 0:
                status += " INCIDENT=-";
                break;

            default:
                status += String.format(" INCIDENT=%d", unit.incidentId);
                break;
        }

        if (unit.work > 0 && unit.incidentId != 0) {
            status += String.format(" WORK=%d", unit.work);
        }
        return status;
    }

    /**
     * Creates a new incident and adds it to the simulation.
     * 
     * @param type     the IncidentType of the incident
     * @param severity the severity (1-5) of the incident
     * @param x,y      the position of the incident
     * @return the incidentId of the new incident
     * @throws InvalidSeverityException if the incident severity is outside of the
     *                                  allowed range (1-5)
     * @throws InvalidLocationException if the given location (x and y) is blocked
     *                                  by an obstacle or is out of bounds
     */
    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y)
            throws InvalidSeverityException, InvalidLocationException {
        // Guard clauses
        if (type == null) {
            throw new IllegalStateException("Invalid incident type given");
        }
        if (severity < 1 || severity > 5) {
            throw new InvalidSeverityException("Invalid severity. Severity must be >= 1 and <= 5");
        }
        checkValidLocation(x, y);

        Incident incident = new Incident(++incidentIdCounter, type, severity, x, y);
        for (int i = 0; i < incidents.length; i++) {
            if (incidents[i] == null) {
                incidents[i] = incident;
                incidentsCounter += 1;
                break;
            }
        }

        return incident.incidentId;
    }

    /**
     * Changes an incident's status to CANCELLED and releases any assigned units if
     * it has been dispatched.
     * 
     * @param incidentId the ID of the incident being cancelled
     * @throws IDNotRecognisedException if no incident exists with the given
     *                                  incidentId
     * @throws IllegalStateException    if the incident is in a stage other than
     *                                  REPORTED or DISPATCHED
     */
    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        Incident incident = getIncident(incidentId);
        // Guard clause
        if (incident.status != IncidentStatus.REPORTED && incident.status != IncidentStatus.DISPATCHED) {
            throw new IllegalStateException(
                    "An incident can not be cancelled after it has been reported and dispatched");
        }

        if (incident.status == IncidentStatus.DISPATCHED) {
            getUnit(incident.unitId).unassignIncident();
        }
        incident.status = IncidentStatus.CANCELLED;
    }

    /**
     * Updates a given ongoing incidents's severity.
     * 
     * @param incidentId  the ID of the incident to be updated
     * @param newSeverity the new severity of the incident (1-5)
     * @throws IDNotRecognisedException if no incident exists with the given
     *                                  incidentId
     * @throws InvalidSeverityException If the severity is not in the range 1-5
     * @throws IllegalStateException    if the incident is already in the
     *                                  RESOLVED/CANCELLED state
     */
    @Override
    public void escalateIncident(int incidentId, int newSeverity)
            throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        Incident incident = getIncident(incidentId);
        // Guard clauses
        if (newSeverity < 1 || newSeverity > 5) {
            throw new InvalidSeverityException("Invalid severity. Severity must be >= 1 and <= 5");
        }
        if (incident.status == IncidentStatus.RESOLVED || incident.status == IncidentStatus.CANCELLED) {
            throw new IllegalStateException("The incidnet must not be resolved or cancelled already");
        }
        incident.severity = newSeverity;
    }

    /**
     * Provides a list of all current incident IDs.
     * 
     * @return a list of incident IDs sorted in ascending order
     */
    @Override
    public int[] getIncidentIds() {
        int[] incidentIds = new int[incidentsCounter];
        int incidentIdsCounter = 0;
        for (Incident incident : incidents) {
            if (incident == null) {
                continue;
            }
            incidentIds[incidentIdsCounter] = incident.incidentId;
            incidentIdsCounter++;
        }
        Arrays.sort(incidentIds);
        return incidentIds;
    }

    /**
     * Returns a formatted string containing information about the given incident.
     * 
     * @param incidentId the ID of the incident to be updated
     * @return a formatted string describing the incident
     * @throws IDNotRecognisedException if no incident exists with the given
     *                                  incidentId
     */
    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        Incident incident = getIncident(incidentId);
        String status = String.format("I#%d TYPE=%s SEV=%d LOC=(%d,%d) STATUS=%s", incident.incidentId,
                incident.type, incident.severity, incident.x, incident.y, incident.status);
        switch (incident.unitId) {
            case 0:
                status += " UNIT=-";
                break;

            default:
                status += String.format(" UNIT=%d", incident.unitId);
                break;
        }
        return status;
    }

    /**
     * Assigns valid units to waiting incidents (incidents with a
     * REPORTED status) and updates their status. Uses tie-breakers to determine how
     * to assign incidents deterministically. Then updates the incident's and unit's
     * status
     */
    @Override
    public void dispatch() {
        for (Incident incident : incidents) {
            if (incident == null)
                continue;
            if (incident.status != IncidentStatus.REPORTED)
                continue;

            Unit bestUnit = null;
            int bestDist = Integer.MAX_VALUE;

            for (Unit unit : units) {
                if (unit == null)
                    continue;
                if (unit.status == UnitStatus.OUT_OF_SERVICE)
                    continue;
                if (unit.status == UnitStatus.EN_ROUTE || unit.status == UnitStatus.AT_SCENE)
                    continue;
                if (unit.incidentId != 0)
                    continue;
                if (!unit.canHandle(incident.type))
                    continue;

                int dist = Math.abs(unit.x - incident.x) + Math.abs(unit.y - incident.y);

                boolean better = false;
                if (dist < bestDist) {
                    better = true;
                } else if (dist == bestDist) {
                    if (unit.unitId < bestUnit.unitId) {
                        better = true;
                    } else if (unit.unitId == bestUnit.unitId) {
                        if (unit.stationId < bestUnit.stationId) {
                            better = true;
                        }
                    }
                }

                if (better) {
                    bestUnit = unit;
                    bestDist = dist;
                }
            }

            if (bestUnit != null) {
                bestUnit.status = UnitStatus.EN_ROUTE;
                bestUnit.incidentId = incident.incidentId;
                incident.status = IncidentStatus.DISPATCHED;
                incident.unitId = bestUnit.unitId;
            }
        }
    }

    /**
     * Advances the simulation time by one tick. Increases the tick value by one and
     * then:
     * - Moves EN_ROUTE units towards incidents
     * - Detects when units have arrived at incidents
     * - Processes the work ticks of each on-scene unit
     * - Detects completed incidents
     */
    @Override
    public void tick() {
        tick++;
        for (int i = 0; i < units.length; i++) {
            Unit unit = units[i];
            if (unit == null || unit.status != UnitStatus.EN_ROUTE)
                continue;

            try {
                Incident target = getIncident(unit.incidentId);
                if (target == null)
                    continue;
                unit.moveTowards(target.x, target.y, map);
            } catch (Exception e) {
                throw new RuntimeException("Invalid incidentId for unknown reason");
            }
        }

        for (int i = 0; i < units.length; i++) {
            Unit unit = units[i];
            if (unit == null || unit.status != UnitStatus.EN_ROUTE)
                continue;

            try {
                Incident incident = getIncident(unit.incidentId);
                if (incident == null)
                    continue;

                if (unit.x == incident.x && unit.y == incident.y) {
                    unit.status = UnitStatus.AT_SCENE;
                    incident.status = IncidentStatus.IN_PROGRESS;
                    incident.ticksRemaining = unit.getTicksToResolve(incident.severity);
                }
            } catch (Exception e) {
                throw new RuntimeException("Invalid incidentId for unknown reason");
            }
        }

        for (int i = 0; i < units.length; i++) {
            Unit unit = units[i];
            if (unit == null || unit.status != UnitStatus.AT_SCENE)
                continue;

            try {
                Incident incident = getIncident(unit.incidentId);
                if (incident == null)
                    continue;
                unit.work++;
                incident.ticksRemaining--;
            } catch (Exception e) {
                throw new RuntimeException("Invalid incidentId for unknown reason");
            }
        }

        for (Incident incident : incidents) {
            if (incident == null)
                continue;
            if (incident.status != IncidentStatus.IN_PROGRESS)
                continue;
            if (incident.ticksRemaining > 0)
                continue;

            incident.status = IncidentStatus.RESOLVED;
            try {
                Unit unit = getUnit(incident.unitId);
                unit.unassignIncident();
            } catch (IDNotRecognisedException e) {
                // should not happen in normal flow
            }
            incident.unitId = 0;
        }
    }

    @Override
    public String getStatus() {
        String status = "";
        status += "TICK=" + tick;
        status += String.format("\nSTATIONS=%d UNITS=%d INCIDENTS=%d OBSTACLES=%d",
                stationsCounter, unitsCounter,
                incidentsCounter, map.obstaclesCounter);

        status += "\nINCIDENTS";
        // List all incidents
        for (int incidentId : getIncidentIds()) {
            try {
                status += ("\n" + viewIncident(incidentId));
            } catch (IDNotRecognisedException e) {
                System.out.println(String.format("I#%d INCIDENT NOT FOUND", incidentId));
            }
        }

        status += "\nUNITS";
        // List all units
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
