package edu.cofc;

import java.util.Random;

/**
 * Created by Claudio on 20/11/2017.
 */
public class Accelerometer implements Sensors<Integer> {
    public boolean isReading = false;
    Random accelerometer = null;

    public Accelerometer() {
        accelerometer = new Random();
    }

    @Override
    public void start() {
        isReading = true;
    }

    @Override
    public void stop() {
        isReading = false;
    }

    @Override
    public Integer read() {
        int reading = accelerometer.nextInt(2000);
        return reading;
    }
}
