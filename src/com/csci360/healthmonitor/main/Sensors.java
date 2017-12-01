package com.csci360.healthmonitor.main;

public interface Sensors<Type> {
    //Is reading from sensor
    boolean isReading = false;

    //Starts reading from sensor
    public void start();

    //Stops reading from sensor
    public void stop();

    //Reads from sensor
    public Type read();
}
