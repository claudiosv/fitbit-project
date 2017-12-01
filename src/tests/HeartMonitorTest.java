package tests;

import com.csci360.healthmonitor.main.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class HeartMonitorTest {
    HeartMonitor sensor;

    /**
     * Setting up our variables
     */
    @Before
    public void setup() throws Exception {
        sensor = new HeartMonitor();
    }

    /**
     * Starts the heart monitor and makes sure the sensor is reading
     */
    @Test
    public void testReading() {
        sensor.start();
        boolean isReading = sensor.isReading;
        assertTrue("The HeartMonitor is reading", isReading);
    }

    /**
     * Read values from the accelerometer
     * Min = 0, Max = 2000
     */
    @Test
    public void testAccelerometer() {
        double sensorReading = sensor.read();
        System.out.printf("Sensor Reading: %.1f\n", sensorReading);

        assertTrue("Reading is greater than 0.7", sensorReading > 0.7);
        assertTrue("Reading is less than 1.7", sensorReading < 1.7);
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
