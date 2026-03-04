package cityrescue;

import cityrescue.enums.UnitStatus;

public class Unit {
    int unitId;
    int stationId;
    UnitStatus status = UnitStatus.IDLE;
    int x;
    int y;

    public Unit(int unitId, int stationId, int x, int y) {
        this.unitId = unitId;
        this.stationId = stationId;
        this.x = x;
        this.y = y;
    }
}

// TODO: Make additional unit subclasses (Ambulance, FireEngine, PoliceCar)
