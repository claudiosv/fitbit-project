package com.csci360.healthmonitor.main;

import javafx.beans.property.SimpleDoubleProperty;

/**
 * Created by Claudio on 20/11/2017.
 */
public class Heart {
    private Thread heartThread = null;
    private HeartMonitor heart = null;
    private int secondsPassed;
    private double heartTotal;
    private SimpleDoubleProperty bpm;

    public double getBpm() {
        return bpm.get();
    }

    public SimpleDoubleProperty bpmProperty() {
        return bpm;
    }

    public Heart() {
        bpm = new SimpleDoubleProperty(0.0);
        heart = new HeartMonitor();
        secondsPassed = 0;
        heartTotal = 0.0;
    }

    /**
     * Thread to read our current heart rate
     * Every second it reads a value from the Heart Monitor
     * This value will be used to calculate out bpm
     * @return
     */
    public boolean startHeart() {
        if(!heart.isReading) {
            heart.start();

            Runnable heartRunnabe = new Runnable() {
                public void run() {
                    try {
                        while(heart.isReading) {
                            //Gets us a heart rate between 0.7 and 1.7 so after 60 seconds we will have a bpm between 42 and 102 (average heart rate in human is between 40 and 100 so pretty close
                            double rate = heart.read();
                            heartTotal = heartTotal + rate;
                            secondsPassed++;

                            //Update our bpm
                            bpm.set((heartTotal / secondsPassed) * 60);


                            Thread.sleep(1000);
                        }
                    }
                    catch (InterruptedException e) {
                        heart.stop();
                    }
                }
            };

            heartThread = new Thread(heartRunnabe);
            heartThread.start();
            return true;
        }
        return false;
    }

    /**
     * Stops the heart monitor
     */
    public void stopHeart() {
        heart.stop();
    }

    /**
     * Resets the values of the heart object
     */
    public void resetHeart() {
        bpm.set(0.0);
        heartTotal = 0.0;
        secondsPassed = 0;
    }

    /**
     * Creates a copy of our heart rate and returns it
     * @return
     */
    public HeartRate saveHeartRate() {
        return new HeartRate(bpm.get());
    }
}
