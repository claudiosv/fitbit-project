package edu.cofc;

/**
 * Created by Claudio on 20/11/2017.
 */
public class Steps {
    private static final int walkingSpeed = 500;

    private Thread stepThread;
    private Accelerometer sensor;
    private int numSteps;

    /**
     * Initialize numSteps to zero
     * Creates and starts our Accelerometer step reader
     */
    public Steps() {
        numSteps = 0;
        sensor = new Accelerometer();
    }

    /**
     * Begins taking readings from the Accelerometer Sensor
     */
    public boolean startCounter() {
        if(!sensor.isReading) {
            sensor.start();

            Runnable stepRunnable = new Runnable() {
                public void run() {
                    try {
                        while (sensor.isReading) {
                            int speed = sensor.read();
                            if (speed > walkingSpeed)
                                numSteps++;

                            Thread.sleep(500);
                        }
                    }
                    catch (InterruptedException e) {
                        sensor.stop();
                    }
                }
            };

            stepThread = new Thread(stepRunnable);
            stepThread.start();
            return true;
        }

        return false;
    }

    /**
     * Stops the Accelerometer Sensor from taking more readings
     */
    public void stopCounter() {
        sensor.stop();
    }

    /**
     * Reset the step count to 0
     * Stops the sensor from reading steps
     */
    public void resetSteps() {
        numSteps = 0;
    }

    /**
     * Returns the number of steps taken
     * @return
     */
    public int readStepCount() {
        return numSteps;
    }

    /**
     * Create a DailySteps copy of this object to save the daily count
     * @return
     */
    public DailySteps saveDailyCount() {
        return new DailySteps(numSteps);
    }
}
