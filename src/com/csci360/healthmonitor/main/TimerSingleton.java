package com.csci360.healthmonitor.main;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class TimerSingleton {
    private boolean timerCounting;
    private SimpleIntegerProperty remainingTime;
    private Thread timerThread;
    private static TimerSingleton instance = null;

    public int getRemainingTime() {
        return remainingTime.get();
    }

    public SimpleIntegerProperty remainingTimeProperty() {
        return remainingTime;
    }

    public static TimerSingleton getInstance() {
        if(instance == null) {
            instance = new TimerSingleton();
        }
        return instance;
    }

    /**
     * TimerSingleton constructor
     * Initializes all class variables to zero
     */
    protected TimerSingleton() {
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
                        Thread.sleep(1000);
                        remainingTime.setValue(remainingTime.get() - 1);
                    }
                }
                catch(InterruptedException e) {
                    remainingTime.set(0);
                    timerCounting = false;
                }
                System.out.println("TimerSingleton completed");

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
        remainingTime.set(0);
    }
}
