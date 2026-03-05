package cityrescue;

import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;

public class PoliceCar extends Unit {
    public PoliceCar(int unitId, int stationId, int x, int y) {
        super(unitId, stationId, x, y);
        type = UnitType.POLICE_CAR;
    }

    public boolean canHandle(IncidentType type) {
        return (type == IncidentType.CRIME);
    }

    public int getTicksToResolve(int severity) {
        return 3;
    }
}
