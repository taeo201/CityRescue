package cityrescue;

import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;

public class FireEngine extends Unit {
    public FireEngine(int unitId, int stationId, int x, int y) {
        super(unitId, stationId, x, y);
        type = UnitType.FIRE_ENGINE;
    }
    
    public boolean canHandle(IncidentType type) {
        return (type == IncidentType.FIRE);
    }

    public int getTicksToResolve(int severity) {
        return 4;
    }
}
