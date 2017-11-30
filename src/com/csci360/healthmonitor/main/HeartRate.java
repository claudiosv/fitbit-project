package com.csci360.healthmonitor.main;

import java.util.Date;

/**
 * Created by Claudio on 20/11/2017.
 */
public class HeartRate {
    private double bpm;
    private Date saveTime;

    public HeartRate(double bpm) {
        this.bpm = bpm;
        this.saveTime = new Date();
    }

    //Gets the beats per minute
    public double getBpm() {
        return bpm;
    }

    //Gets the time when the data was saved
    public Date getSaveTime() {
        return saveTime;
    }
}
