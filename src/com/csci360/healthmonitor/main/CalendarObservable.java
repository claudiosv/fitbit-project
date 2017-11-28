package com.csci360.healthmonitor.main;

import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

public class CalendarObservable {
    java.util.Timer timeThread;
    SimpleStringProperty currentTime;
    long time = System.currentTimeMillis();

    public CalendarObservable() {
        currentTime = new SimpleStringProperty("00:00");

        Runnable update = new Runnable() {
            @Override
            public void run() {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                currentTime.setValue(sdf.format(cal.getTime()));
            }
        };

        timeThread = new java.util.Timer();
        timeThread.schedule(new TimerTask() {
            @Override
            public void run() {
                update.run();
            }
        }, 60000 - (time % 60000), 60000);

        update.run();
    }
}
