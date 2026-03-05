package cityrescue;

import cityrescue.enums.UnitStatus;
import cityrescue.enums.UnitType;

public abstract class Unit {
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

    public abstract int getTicksToResolve(int severity);

    public void moveTowards(int targetX, int targetY, CityMap map) {
        int[] dx = {  0, 1,  0, -1 };
        int[] dy = { -1, 0,  1,  0 };
        int currentDist = Math.abs(x - targetX) + Math.abs(y - targetY);

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (map.checkMoveIsLegal(nx, ny)) {
                if (Math.abs(nx - targetX) + Math.abs(ny - targetY) < currentDist) {
                    x = nx; y = ny; return;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (map.checkMoveIsLegal(nx, ny)) {
                x = nx; y = ny; return;
            }
        }
    }
}
