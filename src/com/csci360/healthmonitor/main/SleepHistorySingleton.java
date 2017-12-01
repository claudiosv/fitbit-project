package com.csci360.healthmonitor.main;

import java.util.ArrayList;

public class SleepHistorySingleton {
    public ArrayList<NightsSleep> sleepTimes = new ArrayList<>();
    public SleepTracker sleepTracker = new SleepTracker();
    private static SleepHistorySingleton instance = null;

    protected SleepHistorySingleton() {

    }

    public static SleepHistorySingleton getInstance() {
        if(instance == null) {
            instance = new SleepHistorySingleton();
        }
        return instance;
    }
    public void addSleepTimes(NightsSleep sleep) {
        sleepTimes.add(sleep);
    }

    public NightsSleep getSleepTimes(int index) {
        return sleepTimes.get(index);
    }

    public int historySize() {
        return sleepTimes.size();
    }
}
