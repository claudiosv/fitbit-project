package com.csci360.healthmonitor.test;

import com.csci360.healthmonitor.main.*;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.Before;
import org.junit.runners.MethodSorters;

import java.util.Date;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SleepHistorySingletonTest {
    SleepHistorySingleton sleepHistory;

   /*
    Methods are named to have them be tested in a certain order
    This is needed to test certain functions as it will always happen in a specified order
    */

    @Before
    public void setup() throws Exception {
        sleepHistory = SleepHistorySingleton.getInstance();
    }

    /**
     * Tests to make sure the history list is empty
     */
    @Test
    public void a_historyEmpty() {
        int size = sleepHistory.historySize();
        assertEquals("The sleep history is empty", 0, size);
    }

    /**
     * Starts the sleep monitor
     * Waits until the sleep monitor has finished (approx 11 seconds)
     * Makes sure a sleep and wake time were set
     */
    @Test
    public void b_sleepTest() throws Exception {
        System.out.println("Starting the sleep timer (approx run time: 11 seconds");

        if(sleepHistory.sleepTracker.startSleepTracker()) {
            while(!sleepHistory.sleepTracker.isSleeping())
                Thread.sleep(100);
            while(sleepHistory.sleepTracker.isSleeping())
                Thread.sleep(100);

            System.out.println("User has awoken");
            Date sleepTime = sleepHistory.sleepTracker.getSleepTime();
            Date wakeTime = sleepHistory.sleepTracker.getWakeTime();

            assertNotNull("Sleep time was set", sleepTime);
            assertNotNull("Wake time was set", wakeTime);
            assertNotSame("Sleep and wake times are different", sleepTime, wakeTime);
        }
        else {
            assertTrue("Tracker was unable to start",false);
        }
    }

    /**
     * Saves the sleep and wake times into the sleep history
     * Makes sure the sleep history now has a size of 1
     */
    @Test
    public void c_saveSleepTest() {
        NightsSleep ns = sleepHistory.sleepTracker.saveNightsSleep();
        sleepHistory.addSleepTimes(ns);

        int historySize = sleepHistory.historySize();
        assertEquals("Heart history is size 1", 1, historySize);
    }

    /**
     * resets the sleep tracker
     * sleeping should be false
     */
    @Test
    public void d_resetSleepTest() {
        sleepHistory.sleepTracker.resetSleepTracker();
        assertFalse("User is not asleep", sleepHistory.sleepTracker.isSleeping());
    }

    /**
     * Makes sure the times in the sleep history were not effected by the reset
     */
    @Test
    public void e_getSleepTest() {
        NightsSleep ns = sleepHistory.getSleepTimes(sleepHistory.historySize() - 1);
        assertNotNull("Sleep time is not null", ns.getSleepTime());
        assertNotNull("Wake time is not null", ns.getWakeTime());
    }
}
