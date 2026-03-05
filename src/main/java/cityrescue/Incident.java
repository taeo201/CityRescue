package cityrescue;

import cityrescue.enums.IncidentStatus;
import cityrescue.enums.IncidentType;

public class Incident {
    int incidentId;
    IncidentType type;
    IncidentStatus status;
    int ticksRemaining;
    int severity;
    int unitId;
    int x;
    int y;

    public Incident(int incidentId, IncidentType type, int severity, int x, int y) {
        this.incidentId = incidentId;
        this.type = type;
        this.status = IncidentStatus.REPORTED;
        this.severity = severity;
        this.x = x;
        this.y = y;
    }
}
