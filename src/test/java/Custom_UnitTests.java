import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import cityrescue.*;
import cityrescue.enums.*;
import cityrescue.exceptions.*;

public class Custom_UnitTests {
    private CityRescue cr;

    // Create a complex initial state to conduct unit tests in realistic scenario
    @BeforeEach
    void setUp() throws Exception {
        cr = new CityRescueImpl();
        cr.initialise(5, 5);

        // Create initial stations, units, and incidents
        int s1 = cr.addStation("Station 1", 0, 0);
        int s2 = cr.addStation("Station 2", 1, 1);
        int del_s1 = cr.addStation("Station A", 1, 1);
        int s3 = cr.addStation("Station 3", 4, 4);
        int del_s2 = cr.addStation("Station B", 1, 1);
        int u1 = cr.addUnit(s1, UnitType.AMBULANCE);
        int u2 = cr.addUnit(s1, UnitType.POLICE_CAR);
        int del_u1 = cr.addUnit(s1, UnitType.POLICE_CAR);
        int del_u2 = cr.addUnit(s1, UnitType.POLICE_CAR);
        int u3 = cr.addUnit(s2, UnitType.FIRE_ENGINE);
        int u4 = cr.addUnit(s3, UnitType.AMBULANCE);
        int del_u3 = cr.addUnit(s3, UnitType.AMBULANCE);

        int i1 = cr.reportIncident(IncidentType.MEDICAL, 1, 0, 1);
        int i2 = cr.reportIncident(IncidentType.FIRE, 2, 0, 1);
        int i3 = cr.reportIncident(IncidentType.CRIME, 1, 0, 1);
        int i4 = cr.reportIncident(IncidentType.MEDICAL, 3, 3, 1);

        // Delete some of the stations, units, and incidents
        cr.decommissionUnit(del_u1);
        cr.decommissionUnit(del_u3);
        cr.decommissionUnit(del_u2);
        cr.removeStation(del_s2);
        cr.removeStation(del_s1);

        int u5 = cr.addUnit(s3, UnitType.AMBULANCE);

        cr.getStatus();

        // Run a couple of cycles
        cr.dispatch();
        for (int i = 0; i < 2; i++) {
            cr.tick();
            System.out.println(cr.getStatus() + "\n");
        }
    }

    @Test
    void map_methods_basic_test() throws Exception {
        cr.getGridSize();
        cr.addObstacle(3, 4);
        cr.addObstacle(1, 2);
    }

    @Test
    void station_methods_test() throws Exception {
        // Adding and removing stations
        int s1 = cr.addStation("Station 4", 0, 0);
        int s2 = cr.addStation("Station 5", 1, 1);
        assertEquals(5, cr.getStationIds().length);
        cr.removeStation(s2);
        assertEquals(4, cr.getStationIds().length);


        // Station capacity modification
        int u1 = cr.addUnit(s1, UnitType.AMBULANCE);
        int u2 = cr.addUnit(s1, UnitType.POLICE_CAR);
        cr.decommissionUnit(u2);
        int u3 = cr.addUnit(s1, UnitType.POLICE_CAR);
        // TODO: Problem Here

        cr.setStationCapacity(s1, 2); 
        cr.setStationCapacity(s1, 3); 
        // cr.setStationCapacity(s1, 1); 

        
        // Station get functions
        cr.getStationIds();
    }
}
