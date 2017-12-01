package com.csci360.healthmonitor.test;

import com.csci360.healthmonitor.main.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class AccelerometerTest {
    Accelerometer sensor;

    /**
     * Setting up our variables
     */
    @Before
    public void setup() throws Exception {
        sensor = new Accelerometer();
    }

    /**
     * Starts the accelerometer and makes sure the sensor is reading
     */
    @Test
    public void testReading() {
        sensor.start();
        boolean isReading = sensor.isReading;
        assertTrue("The Accelerometer is reading", isReading);
    }

    /**
     * Read values from the accelerometer
     * Min = 0, Max = 2000
     */
    @Test
    public void testAccelerometer() {
        int sensorReading = sensor.read();
        System.out.printf("Sensor Reading: %d\n", sensorReading);

        assertTrue("Reading is greater than 0", sensorReading > 0);
        assertTrue("Reading is less than 2000", sensorReading < 2000);
    }

    /**
     * Makes sure that when the sensor has been stopped, it is no longer reading
     */
    @Test
    public void testStop() {
        sensor.stop();
        assertTrue("The sensor was stopped and is not reading", !sensor.isReading);
    }
}
