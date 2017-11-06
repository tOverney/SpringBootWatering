package ch.overney.springBootWatering.data;

import ch.overney.springBootWatering.util.Point;

import java.util.*;

/**
 * For mock purposes, and instead of a Database
 * This handler is where all our dummy data lives.
 */
public class MockDataHandler {

    private static final Random random = new Random();
    private static final int WORLD_SIZE = 50;
    private static MockDataHandler instance = null;
    private static long currentSensorId = 0L;
    private final HashSet<Long> companiesHandled;
    private final List<Sensor> allSensors;
    private final List<SensorGroup> allGroups;
    private final Map<Long, SimulatedSensor> simulatedGarden;

    private MockDataHandler() {
        allSensors = new LinkedList<>();
        allGroups = new LinkedList<>();
        companiesHandled = new HashSet<>();
        simulatedGarden = new HashMap<>();
    }

    /**
     * Generates dummy sensors for a given company.
     * @param companyId id of the company that owns those sensors.
     */
    public void generateDataForCompany(long companyId) {
        // avoid generating twice the data for a given company id.
        if (companiesHandled.contains(companyId)) {
            return;
        }
        companiesHandled.add(companyId);

        // determine the amount of sensor and groups we'll generate.
        int sensorAmnt = 4 + random.nextInt(24);
        int groupAmnt = 1 + random.nextInt(sensorAmnt - 1);

        // creates the groups with random thresholds min being 0-50% and max 51-100% humidity.
        SensorGroup[] groups = new SensorGroup[groupAmnt];
        for( int i = 0; i < groupAmnt; i ++) {
            SensorGroup newGroup = new SensorGroup("g_" + i, companyId, random.nextDouble() * 50,
                    51 + random.nextDouble() * 49 );
            groups[i] = newGroup;
            allGroups.add(newGroup);
        }

        // create the sensors.
        for( int i = 0; i < sensorAmnt; i ++) {
            Point initialPosition = new Point(random.nextInt(WORLD_SIZE), random.nextInt(WORLD_SIZE));
            Sensor newSensor = new Sensor(companyId, initialPosition);
            SensorGroup assignedGroup = groups[i % groupAmnt];
            newSensor.setGroup(assignedGroup);
            allSensors.add(newSensor);

            // add the sensor to the simulated garden
            SimulatedSensor newSimulatedSensor = new SimulatedSensor(newSensor.getSensorId(), companyId,
                    initialPosition, assignedGroup.getHumiditySweetspot());
            simulatedGarden.put(newSensor.getSensorId(), newSimulatedSensor);
        }
    }

    public List<Sensor> getAllSensors() {
        return new LinkedList<>(allSensors);
    }

    public List<SensorGroup> getAllGroups() {
        return allGroups;
    }

    public SimulatedSensor fetchSimulated(Long sensorId) {
        return simulatedGarden.get(sensorId);
    }

    public static MockDataHandler getInstance() {
        if (instance == null) {
            instance = new MockDataHandler();
        }
        return instance;
    }

    public static void contactEmergencyNumberForCompany(long companyId, String message) {
        System.out.println("EMERGENCY for Company #"+companyId+", " + message);
    }

    /*
     * Returns the first non allocated sensorId
     * and increment the tracking of allocated sensorIds.
     * (ideally you wouldn't want those ids to be sequential.)
     */
    public static long allocateSensorId() {
        return currentSensorId++;
    }
}
