package edu.cofc;

import java.util.ArrayList;

/**
 * Created by Claudio on 20/11/2017.
 */
public class StepHistory {
    private ArrayList<DailySteps> stepCounts = new ArrayList<DailySteps>();
    public Steps stepCounter = new Steps();

    public StepHistory() { }

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
}
