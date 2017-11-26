package edu.cofc;

import java.util.Random;

/**
 * Created by Claudio on 20/11/2017.
 */
public class HeartMonitor implements Sensors<Double> {
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
    public Double read() {
        Random rand = new Random();
        double value = rand.nextDouble() + 0.7; //Random double between 0.7 and 1.7
        return value;
    }
}
