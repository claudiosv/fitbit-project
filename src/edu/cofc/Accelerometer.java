package edu.cofc;

import java.util.Random;

/**
 * Created by Claudio on 20/11/2017.
 */
public class Accelerometer implements Sensors<Integer> {
    public boolean isReading = false;

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
        Random accel = new Random();
        int reading = accel.nextInt(2000);
        return reading;
    }
}
