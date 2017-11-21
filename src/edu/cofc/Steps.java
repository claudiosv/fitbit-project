package edu.cofc;

/**
 * Created by Claudio on 20/11/2017.
 */
public class Steps {
    private int numSteps;

    public void takeStep() {
        numSteps++;
    }

    public DailySteps saveDailyCount()
    {
        return new DailySteps();
    }
}
