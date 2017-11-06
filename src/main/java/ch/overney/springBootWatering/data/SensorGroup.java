package ch.overney.springBootWatering.data;

/**
 * Represents a group of Sensor.
 * It allows for setting thresholds on the min & max humidity levels.
 */
public class SensorGroup {
    private double minHumidityThreshold;
    private double maxHumidityThreshold;
    private final long companyId;
    private String name;

    public SensorGroup(String name, long companyId, double minThreshold, double maxThreshold) {
        this.name = name;
        this.companyId = companyId;
        this.minHumidityThreshold = minThreshold;
        this.maxHumidityThreshold = maxThreshold;
    }

    public long getCompanyId() {
        return companyId;
    }

    public String getName() {
        return name;
    }

    public double getMaxHumidityThreshold() {
        return maxHumidityThreshold;
    }

    public double getMinHumidityThreshold() {
        return minHumidityThreshold;
    }

    // return the mean of the thresholds.
    public double getHumiditySweetspot() {
        return (maxHumidityThreshold + minHumidityThreshold) / 2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxHumidityThreshold(double maxHumidityThreshold) {
        assert maxHumidityThreshold > minHumidityThreshold;
        this.maxHumidityThreshold = maxHumidityThreshold;
    }

    public void setMinHumidityThreshold(double minHumidityThreshold) {
        assert minHumidityThreshold < maxHumidityThreshold;
        this.minHumidityThreshold = minHumidityThreshold;
    }
}
