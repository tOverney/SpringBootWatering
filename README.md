# SpringBootWatering
Project structure

### SpringBootWaterApplication
Entry point, it starts the server and then also starts the updater which will then query the server.

This is here that you can easilly generate data for a second, third company! 

### SensorController
Handle all the REST entry points.

## Util

Holds the two helper classes, `Point` which represent a 2D point and a dummy user which wasn't really necessary for the sake of this small project.

## Data

Data holds all the data structures and the Data handling classes

### SimulatedSensor
Those are used only by `SensorController`
and mimic the behavior that a real sensor could have on the terrain (dumbed down). It is worth noting that the state of a sensor updates each time it's queried (before giving back an answer) and that's it. There is no automatic evolution of those simulated sensors.

### MockDataHandler
Holds all the dummy data that is generated for that small project. it fakes the sensors and everything when queried to do so for a given company id.

## App

Only contains the `SensorsStateUpdater` which is the big never ending loop which parallelize the collection of status update from the sensors by querying each one and then start/stop watering them depending on the need!
