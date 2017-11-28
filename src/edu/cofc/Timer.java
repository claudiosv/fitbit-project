package edu.cofc;

/**
 * Created by Claudio on 20/11/2017.
 */
public class Timer {
    private boolean timerCounting;
    private int remainingTime;
    Thread timerThread;

    /**
     * Timer constructor
     * Initializes all class variables to zero
     */
    public Timer() {
        timerCounting = false;
        remainingTime = 0;
        timerThread = null;
    }

    /**
     * Starts the timer in a thread to run while the UI can continue functioning
     * Increments the seconds down by 1 every 1000 milliseconds
     * @param seconds
     */
    public void startTimer(int seconds) {
        remainingTime = seconds;

        Runnable timerRunnable = new Runnable() {
            public void run() {
                try {
                    while (remainingTime > 0 && timerCounting) {
                        remainingTime--;
                        Thread.sleep(1000);
                    }
                }
                catch(InterruptedException e) {
                    remainingTime = 0;
                    timerCounting = false;
                }
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

    public int secondsRemaining() {
        return remainingTime;
    }
}
