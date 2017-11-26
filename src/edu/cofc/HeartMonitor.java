package edu.cofc;

/**
 * Created by Claudio on 20/11/2017.
 */
public class HeartMonitor implements Sensors<Integer> {
    public int beatCount;
    public int secondsPassed;

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public Integer read() {
        return 0;
    }

    public int getBPM()
    {return 0;}
}
