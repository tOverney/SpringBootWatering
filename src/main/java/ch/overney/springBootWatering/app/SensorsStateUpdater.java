package ch.overney.springBootWatering.app;

import ch.overney.springBootWatering.data.MockDataHandler;
import ch.overney.springBootWatering.data.Sensor;
import ch.overney.springBootWatering.data.SensorData;
import ch.overney.springBootWatering.data.SensorGroup;
import ch.overney.springBootWatering.util.Point;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

/**
 * Takes care of updating
 */
public class SensorsStateUpdater {
    private static int iterationCounter;
    private static HashSet<Sensor> sensorsBeingWatered;

    // for this example I chose to parallelize on groups and not company as I've only generated data for one company.
    public static void runForever() {
        iterationCounter = 0;
        // for this simple example we assume that this is the beginning of everything, anytime and that no plants near sensors are being watered yet.
        sensorsBeingWatered = new HashSet<>();

        while(true) {
            List<SensorGroup> allGroups = MockDataHandler.getInstance().getAllGroups();
            allGroups.parallelStream().forEach(SensorsStateUpdater::checkAllSensors);
            System.out.println("Updater checked all sensors "+(++iterationCounter)+" times!");
        }
    }

    // handle all sensors from a group.
    private static void checkAllSensors(SensorGroup group) {
        Stream<Sensor> allSensorsFromGroup =
                MockDataHandler.getInstance().getAllSensors().parallelStream().filter(sensor -> sensor.getGroup() == group);
        allSensorsFromGroup.forEach(sensor -> handleSensor(sensor, group));
    }

    // handle a single sensors.
    private static void handleSensor(Sensor sensor, SensorGroup group) {
        // fetch the sensor data
        RestTemplate restTemplate = new RestTemplate();
        SensorData sensorData =
                restTemplate.getForObject("http://localhost:8080/status/"+group.getCompanyId()+"/"+sensor.getSensorId(), SensorData.class);

        // check the humidity level
        double humidityLevel = sensorData.getHumidityLevel();
        if (humidityLevel < group.getHumiditySweetspot()) {
            startWatering(sensor);
            if (humidityLevel <= group.getMinHumidityThreshold()) {
                emergencyMessaging(sensor, "not enough");
            }
        } else if (humidityLevel > group.getHumiditySweetspot()) {
            stopWatering(sensor);
            if (humidityLevel >= group.getMaxHumidityThreshold()) {
                emergencyMessaging(sensor, "too much");
            }
        }

        // check position
        Point newPosition = sensorData.getPosition();
        if (!newPosition.equals(sensor.getLastPosition())) {
            System.out.println("WARNING, sensor #" + sensor.getSensorId() + " was moved to " + newPosition.prettyPrint() +
                    " you might want to change the group it belongs to.");
            sensor.setLastPosition(newPosition);
        }
    }

    private static void startWatering(Sensor sensor) {
        // if true, the sensor was not in the set yet.
        if (sensorsBeingWatered.add(sensor)) {
            System.out.println("Start watering sensor #"+sensor.getSensorId());
            managePutRequest("http://localhost:8080/start/"+sensor.getCompanyId()+"/"+sensor.getSensorId());
        }
    }

    private static void stopWatering(Sensor sensor) {
        // if true, the sensor was in the set and watering needs to be stopped.
        if (sensorsBeingWatered.remove(sensor)) {
            System.out.println("Stop watering sensor #"+sensor.getSensorId());
            managePutRequest("http://localhost:8080/stop/" + sensor.getCompanyId() + "/" + sensor.getSensorId());
        }
    }

    private static void managePutRequest(String url) {
        // we do not care about what object is being sent as request.
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.put(url, "");
        } catch (HttpClientErrorException httpError) {
            httpError.printStackTrace();
        }
    }

    private static void emergencyMessaging(Sensor sensor, String quantitative) {
        String message = quantitative + " humidity for sensor #" + sensor.getSensorId() + " at position: " +
                sensor.getLastPosition().prettyPrint();
        MockDataHandler.contactEmergencyNumberForCompany(sensor.getCompanyId(), message);
    }
}
