package edu.cofc;

/**
 * Created by Claudio on 20/11/2017.
 */
public interface Sensors<Type> {
    boolean isReading = false;
    public void start();
    public void stop();
    public Type read();
}
