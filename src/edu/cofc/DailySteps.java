package edu.cofc;

import java.util.Date;

/**
 * Created by Claudio on 20/11/2017.
 */
public class DailySteps {
    public int stepCount;
    public Date saveTime;

    /**
     * Constructor to save the Step attributes to a saved object
     * @param stepCount
     */
    public DailySteps(int stepCount) {
        this.stepCount = stepCount;
        this.saveTime = new Date();
    }

    /**
     * Returns step count from this saved Step object
     * @return
     */
    public int stepCount() {
        return stepCount;
    }

    /**
     * Returns save date from this saved Step object
     * @return
     */
    public Date stepDate() {
        return saveTime;
    }
}
