package com.csci360.healthmonitor.test;

import com.csci360.healthmonitor.main.*;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.Before;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StepHistorySingletonTest {
    StepHistorySingleton stepHistory;

    /*
    Methods are named to have them be tested in a certain order
    This is needed to test certain functions as it will always happen in a specified order
     */

    @Before
    public void setup() throws Exception {
        stepHistory = StepHistorySingleton.getInstance();
    }

    /**
     * Tests to make sure the history list is empty
     */
    @Test
    public void a_historyEmpty() {
        int size = stepHistory.historySize();
        assertEquals("Step history is empty", 0, size);
    }

    /**
     * Starts the step counter
     * Waits 5 seconds then gets the step count
     * step count should be greater than 0 (assumes the user is walking)
     */
    @Test
    public void b_startStepTest() throws Exception {
        stepHistory.stepCounter.startCounter();
        Thread.sleep(5000);

        int stepCount = stepHistory.stepCounter.getNumSteps();
        assertTrue("Step count is greater than 0", stepCount > 0);
    }

    /**
     * stops the step counter
     * wait 1 second
     * step count should be the same as before stopped
     */
    @Test
    public void c_stopStepTest() throws Exception {
        int beforeCount = stepHistory.stepCounter.getNumSteps();
        stepHistory.stepCounter.stopCounter();
        Thread.sleep(1000);

        int afterCount = stepHistory.stepCounter.getNumSteps();
        assertEquals("Step counter was stopped correctly", beforeCount, afterCount);
    }

    /**
     * Saves the current step count into step history
     * Checks step history size to makes sure it is 1
     */
    @Test
    public void d_saveStepTest() {
        DailySteps ds = stepHistory.stepCounter.saveDailyCount();
        stepHistory.addDailyCount(ds);

        int historySize = stepHistory.historySize();
        assertEquals("Step history has data", 1, historySize);
    }

    /**
     * resets the step counter
     * count should be set to 0
     */
    @Test
    public void e_resetHeartTest() {
        stepHistory.stepCounter.resetSteps();

        int stepCount = stepHistory.stepCounter.getNumSteps();
        assertEquals("Step count is 0", 0, stepCount);
    }

    /**
     * Makes sure the step count was not effected by the reset
     */
    @Test
    public void f_getStepTest() {
        DailySteps ds = stepHistory.getDailyCount(stepHistory.historySize() - 1);
        int stepCount = ds.stepCount;

        assertNotEquals("Step count is not 0 (unaffected by reset)", 0, stepCount);
    }
}
