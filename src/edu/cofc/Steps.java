package edu.cofc;

/**
 * Created by Claudio on 20/11/2017.
 */
public class Steps {
    private int numSteps;

    /**
     * Initialize numSteps to zero
     */
    public Steps() {
        numSteps = 0;
    }

    /**
     * Reset the step count to 0
     */
    public void resetSteps() {
        numSteps = 0;
    }

    /**
     * Returns the number of steps taken
     * @return
     */
    public int getSteps() {
        return numSteps;
    }

    /**
     * Increments the step count by one
     */
    public void takeStep() {
        numSteps++;
    }

    /**
     * Create a DailySteps copy of this object to save the daily count
     * @return
     */
    public DailySteps saveDailyCount() {
        return new DailySteps(numSteps);
    }
}
