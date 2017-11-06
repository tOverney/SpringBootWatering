package ch.overney.springBootWatering.data;

import ch.overney.springBootWatering.util.Point;

import java.util.Random;

/**
 * Simulate what's happening in the terrain/garden.
 * This only gets updated when we query its status.
 * It has a low probability to move.
 */
public class SimulatedSensor {
    private final static double MOVE_RATE = .02;
    private final static double HUMIDITY_INCREASE_RATE = 4.2;
    private final static double DRYING_RATE = 3.4;
    private final long sensorId;
    private final long companyId;
    private Point position;
    private double humidityLevel;
    private boolean beingWatered;

    public SimulatedSensor(long sensorId, long companyId, Point position, double humidityLevel) {
        this.sensorId = sensorId;
        this.companyId = companyId;
        this.position = new Point(position);
        this.humidityLevel = humidityLevel;
        this.beingWatered = false;
    }

    public boolean startWatering(long requesterCompanyId) {
        if (hasPermission(requesterCompanyId)) {
            beingWatered = true;
            return true;
        }
        return false;
    }

    public boolean stopWatering(long requesterCompanyId) {
        if (hasPermission(requesterCompanyId)) {
            beingWatered = false;
            return true;
        }
        return false;
    }

    public SensorData getStatus(long requesterCompanyId) {
        if (!hasPermission(requesterCompanyId)) {
            return null;
        }

        // update the humidity level wrt being watered or not
        if (beingWatered) {
            humidityLevel -= HUMIDITY_INCREASE_RATE;
        } else {
            humidityLevel -= DRYING_RATE;
        }

        // check if we make the sensor move
        if (new Random().nextDouble() <= MOVE_RATE) {
            // we don't really care about the accuracy or the world size, we just want to move it for the sake of it.
            position = new Point(position.getX() + 1, position.getY() - 1);
            System.out.println("Sensor #"+sensorId+" was moved by the simulation");
        }

        return toSensorData();

    }

    private SensorData toSensorData() {
        return new SensorData(sensorId, new Point(position), humidityLevel);
    }

    // check if allowed to return info.
    private boolean hasPermission(long requesterCompanyId) {
        if (requesterCompanyId != companyId) {
            System.out.println("This is confidential data that do not belong to you!");
            return false;
        }
        return true;
    }


}
