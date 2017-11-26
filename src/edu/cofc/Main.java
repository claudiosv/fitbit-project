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
            - When saved is pressed it will save the daily step count */

        /*
        stepHistory.stepCounter.startCounter();

        Runnable testingThread = new Runnable() {
            public void run() {
                try {
                    //This will be a while(onStepCountScreen) {} using sleep for testing purposes
                    //Update the text on screen
                    Thread.sleep(2000);
                    System.out.printf("Step Count: %d\n", stepHistory.stepCounter.readStepCount());

                }
                catch(InterruptedException e) {
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

    }
}
