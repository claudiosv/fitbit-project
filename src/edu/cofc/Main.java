package edu.cofc;

public class Main {
    //UI Needs to have access to our Classes
    public static User user = new User();
    public static StepHistory stepHistory = new StepHistory();
    public static SleepHistory sleepHistory = new SleepHistory();
    public static HeartHistory heartHistory = new HeartHistory();
    public static Sync sync = new Sync();
    public static Timer timer = new Timer();

    public static void main(String[] args) {


         /* How the step counter works: (This will be activated by switching to step counter screen and pressing start)
            - Starts the step counter (constantly reading from the accelerometer)
            - Thread will be created to update the step count on screen
            - Stops the counter when the stop button is pressed
            - When saved is pressed it will save the daily step count
         */

        /*
        if(stepHistory.stepCounter.startCounter()) {
            Runnable testingThread = new Runnable() {
                public void run() {
                    try {
                        //This will be a while(onStepCountScreen) {} using sleep for testing purposes
                        //Update the text on screen
                        Thread.sleep(2000);
                        System.out.printf("Step Count: %d\n", stepHistory.stepCounter.readStepCount());

                    } catch (InterruptedException e) {
                        //In case of an error, the step counter is stopped and reset but not saved
                        stepHistory.stepCounter.stopCounter();
                        stepHistory.stepCounter.resetSteps();
                    }

                    //Stop button pressed
                    stepHistory.stepCounter.stopCounter();

                    //Saves button pressed, saves and resets
                    stepHistory.addDailyCount(stepHistory.stepCounter.saveDailyCount());
                    stepHistory.stepCounter.resetSteps();
                }
            };

            Thread testThread = new Thread(testingThread);
            testThread.start();
        }
        */


        /*
           How the Timer works: (activated by switching to the timer screen and pressing start)
             - Inputs the desired seconds from the UI
             - Creates thread to count down
             - Stops when time has been reached
         */

        /*
        timer.startTimer(100); //1 minute 40 seconds for testing

         */

        /* Heart Monitor
            - Heart.startHeart will be called from pressing a button
            - A thread should be created to constantly be reading the Heart.getHeartRate()
            - Use this to update the UI
            - Heart.stopHeart and Heart.resetHeart will be used to stop the heart monitor from buttons
         */

        /*
        if(heartHistory.heartMonitor.startHeart()) {
            Runnable testingThread = new Runnable() {
                public void run() {
                    try {
                        for(int i = 0; i < 40; i++) {
                            //Read for twenty seconds
                            System.out.printf("Heart Rate (BPM): %f\n", heartHistory.heartMonitor.readHeartRate());

                            Thread.sleep(500);
                        }
                    } catch (InterruptedException e) {
                        //In case of an error, the step counter is stopped and reset but not saved
                        heartHistory.heartMonitor.stopHeart();
                        heartHistory.heartMonitor.resetHeart();
                    }

                    //Stop button pressed
                    heartHistory.heartMonitor.stopHeart();

                    //Saves button pressed, saves and resets
                    heartHistory.addHeartRate(heartHistory.heartMonitor.saveHeartRate());
                    heartHistory.heartMonitor.resetHeart();
                }
            };

            Thread testThread = new Thread(testingThread);
            testThread.start();
        }
        */
    }
}
