package com.csci360.healthmonitor.main;

/**
 * Created by Claudio on 20/11/2017.
 */
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
