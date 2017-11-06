package ch.overney.springBootWatering;

import ch.overney.springBootWatering.data.MockDataHandler;
import ch.overney.springBootWatering.data.SimulatedSensor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
public class SensorController {

    @RequestMapping(value="/status/{companyId}/{sensorId}", method= RequestMethod.GET)
    public String getStatus(@PathVariable long companyId, @PathVariable long sensorId) {
        SimulatedSensor requestedSensor = MockDataHandler.getInstance().fetchSimulated(sensorId);
        return requestedSensor.getStatus(companyId).toJSON();
    }

    @RequestMapping(value="/start/{companyId}/{sensorId}", method= RequestMethod.PUT)
    public HttpStatus startWatering(@PathVariable long companyId, @PathVariable long sensorId) {
        System.out.println("check check check! " + sensorId);
        SimulatedSensor requestedSensor = MockDataHandler.getInstance().fetchSimulated(sensorId);
        return booleanToHttpStatus(requestedSensor.startWatering(companyId));
    }

    @RequestMapping(value="/stop/{companyId}/{sensorId}", method= RequestMethod.PUT)
    public HttpStatus stopWatering(@PathVariable long companyId, @PathVariable long sensorId) {
        SimulatedSensor requestedSensor = MockDataHandler.getInstance().fetchSimulated(sensorId);
        return booleanToHttpStatus(requestedSensor.stopWatering(companyId));
    }

    // returns a failure or a success HttpStatus.
    private HttpStatus booleanToHttpStatus(boolean status) {
        return status ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST;
    }
}
