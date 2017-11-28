package com.csci360.healthmonitor.main;

import java.util.ArrayList;

/**
 * Created by Claudio on 20/11/2017.
 */
public class HeartHistory {
    private ArrayList<HeartRate> heartRates = new ArrayList<>();
    public Heart heartMonitor = new Heart();

    public HeartHistory() { }

    /**
     * Adds a heart rate to our history
     * @param heartRate
     */
    public void addHeartRate(HeartRate heartRate) {
        heartRates.add(heartRate);
    }

    /**
     * Gets a heart rate from our history
     * @param index
     * @return
     */
    public HeartRate getHeartRate(int index) {
        return heartRates.get(index);
    }
}
