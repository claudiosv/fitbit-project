package com.csci360.healthmonitor.main;

import java.util.Random;

public class Accelerometer implements Sensors<Integer> {
    //Is reading from sensor
    public boolean isReading = false;
    //Random generator for accelerometer simulation
    private Random accelerometer = null;

    public Accelerometer() {
        accelerometer = new Random();
    }

    //Starts the sensor
    @Override
    public void start() {
        isReading = true;
    }

    //Stops the sensor
    @Override
    public void stop() {
        isReading = false;
    }

    //Reads from the sensor
    @Override
    public Integer read() {
        int reading = accelerometer.nextInt(2000);
        return reading;
    }
}
