package com.csci360.healthmonitor.main;

import java.util.ArrayList;

/**
 * Created by Claudio on 20/11/2017.
 */
public class HeartHistorySingleton {
    private ArrayList<HeartRate> heartRates = new ArrayList<>();
    public Heart heartMonitor = new Heart();
    private static HeartHistorySingleton instance = null;

    protected HeartHistorySingleton() {

    }

    public static HeartHistorySingleton getInstance() {
        if(instance == null) {
            instance = new HeartHistorySingleton();
        }
        return instance;
    }

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
