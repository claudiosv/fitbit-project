package com.csci360.healthmonitor.main;

import java.util.ArrayList;

public class StepHistorySingleton {
    private ArrayList<DailySteps> stepCounts = new ArrayList<DailySteps>();
    public Steps stepCounter = new Steps();
    private static StepHistorySingleton instance = null;

    protected StepHistorySingleton() {
    }

    public static StepHistorySingleton getInstance() {
        if(instance == null) {
            instance = new StepHistorySingleton();
        }
        return instance;
    }
    /**
     * Adds a DailySteps object to history list
     * @param dailyCount
     */
    public void addDailyCount(DailySteps dailyCount) {
        stepCounts.add(dailyCount);
    }

    /**
     * Get daily step count from a saved index
     * @param index
     * @return
     */
    public DailySteps getDailyCount(int index) {
        return stepCounts.get(index);
    }

    public int historySize() {
        return stepCounts.size();
    }
}
