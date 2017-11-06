package ch.overney.springBootWatering.data;

import ch.overney.springBootWatering.util.Point;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Data format exchanged when using the API to query a sensor status.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorData {

    private long sensorId;
    private Point position;
    private double humidityLevel;

    // Needed by Jackson!
    public SensorData() { }

    public void setHumidityLevel(double humidityLevel) {
        this.humidityLevel = humidityLevel;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }
    // -----

    public SensorData(long sensorId, Point position, double humidityLevel) {
        this.sensorId = sensorId;
        this.position = position;
        this.humidityLevel = humidityLevel;
    }

    public long getSensorId() {
        return sensorId;
    }

    public Point getPosition() {
        return position;
    }

    public double getHumidityLevel() {
        return humidityLevel;
    }

    public String toJSON() {
        return "{" + "\"sensorId\": " + sensorId +
                ", \"position\": " + position.toJSON() +
                ", \"humidityLevel\": " + humidityLevel + '}';
    }
}
