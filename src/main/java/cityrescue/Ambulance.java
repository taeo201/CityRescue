package cityrescue;

import cityrescue.enums.UnitType;

public class Ambulance extends Unit {
    UnitType type = UnitType.AMBULANCE;

    public Ambulance(int unitId, int stationId, int x, int y) {
        super(unitId, stationId, x, y);
    }
}
