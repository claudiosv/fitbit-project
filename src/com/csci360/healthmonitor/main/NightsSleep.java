package com.csci360.healthmonitor.main;

import java.util.Date;

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
