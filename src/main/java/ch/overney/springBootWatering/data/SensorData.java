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

    @Override
    public String toString() {
        return "SensorData{" +
                "sensorId=" + sensorId +
                ", position=" + position +
                ", humidityLevel=" + humidityLevel +
                '}';
    }
}
