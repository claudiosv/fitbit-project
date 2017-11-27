package edu.cofc;

import java.util.Date;

/**
 * Created by Claudio on 20/11/2017.
 */
public class SleepTracker {
    private static final int sleepSpeed = 1000;

    private boolean sleeping = false;
    private Thread sleepThread;
    private Accelerometer sensor;
    private Date sleepTime;
    private Date wakeTime;

    public SleepTracker() {
        sensor = new Accelerometer();
        sleepTime = null;
        wakeTime = null;
    }

    /**
     * Reading from sensor to check if sleeping
     * @return
     */
    private boolean checkIfSleeping() {
        if(sensor.isReading) {
            int speed = sensor.read();
            System.out.printf("reading: %d\n", speed);
            if(speed < sleepSpeed)
                return true;
        }
        return false;
    }

    /**
     * Starts the thread to check sleep pattern in the background
     * @return
     */
    public boolean startSleepTracker() {
        if(!sensor.isReading) {
            sensor.start();

            Runnable sleepRunnable = new Runnable() {
                public void run() {
                    try {
                        //small delay to allow UI thread to be ready
                        Thread.sleep(1000);

                        while(!checkIfSleeping() && sensor.isReading) {
                            //check if sleep every 30 seconds
                            Thread.sleep(1000);
                        }

                        if(sensor.isReading) {
                            sleepTime = new Date();
                            sleeping = true;
                        }

                        //Ten second delay to compensate for not having a real way to detect sleep
                        //Just to give some data for proof of concept
                        Thread.sleep(10000);

                        while(checkIfSleeping() && sensor.isReading) {
                            //check to see if we are still asleep
                            Thread.sleep(1000);
                        }

                        if(sensor.isReading) {
                            wakeTime = new Date();
                            sleeping = false;
                        }
                    }
                    catch (InterruptedException e) {
                        sensor.stop();
                    }
                }
            };

            sleepThread = new Thread(sleepRunnable);
            sleepThread.start();
            return true;
        }
        return false;
    }

    /**
     * Force stop sleep tracker
     */
    public void stopSleepTracker() {
        sensor.stop();
    }

    /**
     * reset the sleep tracker to defaults
     */
    public void resetSleepTracker() {
        this.sleeping = false;
        this.sleepTime = null;
        this.wakeTime = null;
    }

    /**
     * method for UI to check if user is asleep
     * @return
     */
    public boolean isSleeping() {
        return this.sleeping;
    }

    /**
     * returns the time the user fell asleep
     * @return
     */
    public Date getSleepTime() {
        return this.sleepTime;
    }

    /**
     * returns the time the user woke up
     * @return
     */
    public Date getWakeTime() {
        return this.wakeTime;
    }

    /**
     * Save a nights sleep copy
     * @return
     */
    public NightsSleep saveNightsSleep() {
        return new NightsSleep(sleepTime, wakeTime);
    }
}
