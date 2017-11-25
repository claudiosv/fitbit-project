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
                        System.out.printf("Timer Thread running: %d\n", remainingTime);
                        remainingTime--;
                        Thread.sleep(1000);
                    }
                }
                catch(InterruptedException e) {
                    System.out.println("Timer Exception Caught: " + e.getMessage());
                }

                //Somehow needs to signal to UI when this is completed. Create a 'signalAlarm' method?
                System.out.println("Thread completed");
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
