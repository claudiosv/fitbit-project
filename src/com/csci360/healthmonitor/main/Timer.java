package com.csci360.healthmonitor.main;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by Claudio on 20/11/2017.
 */
public class Timer {
    private boolean timerCounting;
    private SimpleIntegerProperty remainingTime;
    Thread timerThread;

    public int getRemainingTime() {
        return remainingTime.get();
    }

    public SimpleIntegerProperty remainingTimeProperty() {
        return remainingTime;
    }

    /**
     * Timer constructor
     * Initializes all class variables to zero
     */
    public Timer() {
        timerCounting = false;
        remainingTime = new SimpleIntegerProperty(0);
        timerThread = null;
    }

    /**
     * Starts the timer in a thread to run while the UI can continue functioning
     * Increments the seconds down by 1 every 1000 milliseconds
     * @param seconds
     */
    public void startTimer(int seconds) {
        remainingTime.set(seconds);

        Runnable timerRunnable = new Runnable() {
            public void run() {
                try {
                    while (remainingTime.get() > 0 && timerCounting) {
                        remainingTime.setValue(remainingTime.get() - 1);
                        Thread.sleep(1000);
                    }
                }
                catch(InterruptedException e) {
                    remainingTime.set(0);
                    timerCounting = false;
                }

                //Somehow needs to signal to UI when this is completed. Create a 'signalAlarm' method?
                System.out.println("Timer completed");

            }
        };

        timerCounting = true;
        timerThread = new Thread(timerRunnable);
        timerThread.start();

    }

    /**
     * Can be used to stop the timer before it is finished
     */
    public void stopTimer() {
        timerCounting = false;
    }
}
