package edu.cofc;

public class Main {
    //UI Needs to have access to our Classes
    public static User user = new User();
    public static StepHistory stepHistory = new StepHistory();
    public static SleepHistory sleepHistory = new SleepHistory();
    public static HeartHistory heartHistory = new HeartHistory();
    public static Sync sync = new Sync(user);
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
                        //thread interrupted display error
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
                        //thread interrupted display error
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

        /* Sync, Companion and User
            - Sync should be called at the opening of the app
            - This will set up the user (just fills in values)
            - The sync process takes 2.5 seconds (just uses a sleep so this can change) so UI can show a 2.5 progress bar for the sync process?
         */

        /*
        if(sync.addCompanion()) {
            Runnable syncRunnable = new Runnable() {
                public void run() {
                    try {
                        System.out.println("Starting the sync process");
                        sync.syncData("Empty Data to send");

                        Thread.sleep(2550);
                        System.out.println("Sync process completed");

                        System.out.println("user: " + user);
                        System.out.printf("Username: %s\n", user.username);
                        System.out.printf("Gender: %s\n", user.getGender());
                        System.out.printf("Birthday: %s\n", user.getBirthday());
                        System.out.printf("Height: %d\n", user.getHeight());
                        System.out.printf("Weight: %d\n", user.getWeight());
                    } catch (InterruptedException e) {
                        //thread interrupted, display sync error
                    }
                }
            };

            Thread syncThread = new Thread(syncRunnable);
            syncThread.start();
        }
        */

        /* Sleep Tracker */

        /*
        if(sleepHistory.sleepTracker.startSleepTracker()) {
            Runnable testRun = new Runnable() {
                public void run() {
                    try {
                        while (!sleepHistory.sleepTracker.isSleeping()) {
                            System.out.println("waiting to fall asleep");
                            Thread.sleep(500);
                        }
                        while(sleepHistory.sleepTracker.isSleeping()) {
                            System.out.println("waiting to wake up");
                            Thread.sleep(500);
                        }

                        System.out.printf("Saving sleep times\n");
                        sleepHistory.addSleepTimes(sleepHistory.sleepTracker.saveNightsSleep());

                        NightsSleep lastNight = sleepHistory.getSleepTimes(0);
                        System.out.printf("Fell Asleep At: %s\n", lastNight.getSleepTime());
                        System.out.printf("Woke Up At: %s\n", lastNight.getWakeTime());
                        System.out.printf("Seconds Asleep: %d\n", lastNight.secondsAsleep());
                    }
                    catch (InterruptedException e) {
                        //thread interrupted, display error
                    }
                }
            };

            Thread testThread = new Thread(testRun);
            testThread.start();
        }
        */
    }
}
