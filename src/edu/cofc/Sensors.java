package edu.cofc;

/**
 * Created by Claudio on 20/11/2017.
 */
public interface Sensors {
    boolean isReading = false;
    void start();
    void stop();
    void read();

}
