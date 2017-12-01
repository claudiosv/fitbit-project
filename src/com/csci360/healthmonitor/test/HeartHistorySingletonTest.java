package com.csci360.healthmonitor.test;

import com.csci360.healthmonitor.main.*;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.Before;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HeartHistorySingletonTest {
    HeartHistorySingleton heartHistory;

    /*
    Methods are named to have them be tested in a certain order
    This is needed to test certain functions as it will always happen in a specified order
     */

    @Before
    public void setup() throws Exception {
        heartHistory = HeartHistorySingleton.getInstance();
    }

    /**
     * Tests to make sure the history list is empty
     */
    @Test
    public void a_historyEmpty() {
        int size = heartHistory.historySize();
        assertEquals("The heart history is empty", 0, size);
    }

    /**
     * Starts the heart monitor
     * Waits 5 seconds then gets the bpm
     * bpm should be between 42 and 102
     */
    @Test
    public void b_startHeartTest() throws Exception {
        heartHistory.heartMonitor.startHeart();
        Thread.sleep(5000);

        double bpm = heartHistory.heartMonitor.getBpm();

        assertTrue("The bpm is greater than 42", bpm > 42.0);
        assertTrue("The bpm is less than 102", bpm < 102.0);
    }

    /**
     * stops the heart monitor
     * wait 1 second
     * bpm should be the same as before stopped to make sure the sensor is actually stopped
     */
    @Test
    public void c_stopHeartTest() throws Exception {
        double bpm = heartHistory.heartMonitor.getBpm();

        heartHistory.heartMonitor.stopHeart();
        Thread.sleep(1000);

        double afterBPM = heartHistory.heartMonitor.getBpm();

        assertEquals("The bpm values are the same", bpm, afterBPM, 0.01);
    }

    /**
     * Saves the current heart rate into the heart history
     * Checks heart history size to makes sure it is 1
     */
    @Test
    public void d_saveHeartTest() {
        HeartRate hr = heartHistory.heartMonitor.saveHeartRate();
        heartHistory.addHeartRate(hr);

        int hrSize = heartHistory.historySize();
        assertEquals("HeartHistory has a value in the list", 1, hrSize);
    }

    /**
     * resets the heart monitor
     * bpm should be set back to 0.0
     */
    @Test
    public void e_resetHeartTest() {
        heartHistory.heartMonitor.resetHeart();

        double bpm = heartHistory.heartMonitor.getBpm();
        assertEquals("Current BPM is reset to 0.0", 0.0, bpm, 0.01);
    }

    /**
     * Makes sure the heart rate in the heart history is not effected by the reset
     */
    @Test
    public void f_getHeartTest() {
        HeartRate hr = heartHistory.getHeartRate(heartHistory.historySize() - 1); //get last insert
        double bpm = hr.getBpm();
        assertNotEquals("Saved heart rate is not 0", 0.0, bpm, 0.01);
    }
}
