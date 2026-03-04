package cityrescue;

import cityrescue.enums.UnitType;

public class FireEngine extends Unit {
    UnitType type = UnitType.FIRE_ENGINE;

    public FireEngine(int unitId, int stationId, int x, int y) {
        super(unitId, stationId, x, y);
    }
}
