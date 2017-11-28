package com.csci360.healthmonitor.main;

import java.util.ArrayList;

/**
 * Created by Claudio on 20/11/2017.
 */
public class SleepHistory {
    public ArrayList<NightsSleep> sleepTimes = new ArrayList<>();
    public SleepTracker sleepTracker = new SleepTracker();

    public void addSleepTimes(NightsSleep sleep) {
        sleepTimes.add(sleep);
    }

    public NightsSleep getSleepTimes(int index) {
        return sleepTimes.get(index);
    }
}
