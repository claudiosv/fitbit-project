package edu.cofc;

import java.util.Date;

/**
 * Created by Claudio on 20/11/2017.
 */
public class NightsSleep {
    public Date sleepTime;
    public Date wakeTime;

    public NightsSleep(Date sleepTime, Date wakeTime) {
        this.sleepTime = sleepTime;
        this.wakeTime = wakeTime;
    }

    public long secondsAsleep() {
        return (wakeTime.getTime() - sleepTime.getTime()) / 1000;
    }

    public Date getSleepTime() {
        return this.sleepTime;
    }

    public Date getWakeTime() {
        return this.wakeTime;
    }
}
