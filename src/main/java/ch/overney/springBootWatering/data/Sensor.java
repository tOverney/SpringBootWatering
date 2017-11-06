package ch.overney.springBootWatering.data;

import ch.overney.springBootWatering.util.Point;

/**
 * Represents a Sensor, uniquely identified by its sensorId.
 *
 */
public class Sensor {

    private final long sensorId = MockDataHandler.allocateSensorId();
    private final long companyId;
    private SensorGroup group;
    private Point referencePosition;
    private Point lastPosition;

    public Sensor(long companyId, Point position) {
        this.companyId = companyId;
        this.referencePosition = position;
        this.lastPosition = position;
    }

    public void setGroup(SensorGroup group) {
        this.group = group;
    }

    public SensorGroup getGroup() {
        return group;
    }

    public long getSensorId() {
        return sensorId;
    }

    public Point getLastPosition() {
        return lastPosition;
    }

    public long getCompanyId() {
        return companyId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Sensor) {
            return sensorId == ((Sensor) obj).sensorId;
        }
        return false;
    }
}
