package cityrescue;

import cityrescue.enums.UnitStatus;
import cityrescue.enums.UnitType;

public class Unit {
    int unitId;
    int stationId;
    UnitType type;
    UnitStatus status = UnitStatus.IDLE;
    int incidentId = 0;
    int work = 0;
    int x;
    int y;

    public Unit(int unitId, int stationId, int x, int y) {
        this.unitId = unitId;
        this.stationId = stationId;
        this.x = x;
        this.y = y;
    }
}
