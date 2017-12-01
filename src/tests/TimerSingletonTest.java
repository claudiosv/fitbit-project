package tests;

import com.csci360.healthmonitor.main.*;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.Before;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TimerSingletonTest {
    TimerSingleton timer;

    /*
    Methods are named to have them be tested in a certain order
    This is needed to test certain functions as it will always happen in a specified order
     */

    @Before
    public void setup() throws Exception {
        timer = TimerSingleton.getInstance();
    }

    /**
     * starts our timer with 10 seconds
     * makes sure there is 10 seconds on the clock
     */
    @Test
    public void a_startTimer() {
        timer.startTimer(10);
        assertEquals("Timer set to 10 seconds", 10, timer.getRemainingTime());
    }

    /**
     * Waits for 4 seconds
     * Checks to make sure the timer has seconds remaining
     * @throws Exception
     */
    @Test
    public void b_checkTime() throws Exception {
        Thread.sleep(4010); //extra 10 milliseconds to account for threads not being exactly aligned

        assertEquals("Timer has 6 seconds remaining", 6, timer.getRemainingTime());
    }

    /**
     * Waits for 2 seconds
     * Stops the timer and makes sure it has 0 seconds remaining (without stop is would have 4)
     * @throws Exception
     */
    @Test
    public void c_stopTimer() throws Exception {
        Thread.sleep(2000);
        timer.stopTimer();

        assertEquals("Timer is finished (0 seconds remaining)", 0, timer.getRemainingTime());
    }
}
