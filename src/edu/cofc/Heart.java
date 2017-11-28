package edu.cofc;

/**
 * Created by Claudio on 20/11/2017.
 */
public class Heart {
    private Thread heartThread = null;
    private HeartMonitor heart = null;
    private int secondsPassed;
    private double heartTotal;
    public double bpm;

    public Heart() {
        heart = new HeartMonitor();
        secondsPassed = 0;
        heartTotal = 0.0;
        bpm = 0;
    }

    /**
     * Thread to read our current heart rate
     * Every second it reads a value from the Heart Monitor
     * This value will be used to calculate out bpm
     * @return
     */
    public boolean startHeart() {
        if(!heart.isReading) {
            heart.start();

            Runnable heartRunnabe = new Runnable() {
                public void run() {
                    try {
                        while(heart.isReading) {
                            //Gets us a heart rate between 0.7 and 1.7 so after 60 seconds we will have a bpm between 42 and 102 (average heart rate in human is between 40 and 100 so pretty close
                            double rate = heart.read();
                            heartTotal = heartTotal + rate;
                            secondsPassed++;

                            //Update our bpm
                            bpm = (heartTotal / secondsPassed) * 60;

                            //trigger event

                            Thread.sleep(1000);
                        }
                    }
                    catch (InterruptedException e) {
                        heart.stop();
                    }
                }
            };

            heartThread = new Thread(heartRunnabe);
            heartThread.start();
            return true;
        }
        return false;
    }

    /**
     * Stops the heart monitor
     */
    public void stopHeart() {
        heart.isReading = false;
    }

    /**
     * Resets the values of the heart object
     */
    public void resetHeart() {
        bpm = 0.0;
        heartTotal = 0.0;
        secondsPassed = 0;
    }

    /**
     * Returns out beats per minute
     * @return
     */
    public double readHeartRate() {
        return bpm;
    }

    /**
     * Creates a copy of our heart rate and returns it
     * @return
     */
    public HeartRate saveHeartRate() {
        return new HeartRate(bpm);
    }
}
