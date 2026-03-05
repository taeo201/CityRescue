package cityrescue;

import cityrescue.enums.UnitType;
import cityrescue.enums.IncidentType;

public class Ambulance extends Unit {
    public Ambulance(int unitId, int stationId, int x, int y) {
        super(unitId, stationId, x, y);
        type = UnitType.AMBULANCE;
    }

    public boolean canHandle(IncidentType type) {
        return (type == IncidentType.MEDICAL);
    }

    public int getTicksToResolve(int severity) {
        return 2;
    }
}
