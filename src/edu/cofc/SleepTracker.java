package edu.cofc;

import java.util.Date;

/**
 * Created by Claudio on 20/11/2017.
 */
public class SleepTracker {
    private boolean isAsleep;
    private Date sleepTime;
    private Date wakeTime;

    public boolean checkIfSleeping()
    {
        return false;
    }

    public NightsSleep saveSleep()
    {
        return new NightsSleep();
    }
}
