package com.csci360.healthmonitor.test;

import com.csci360.healthmonitor.main.*;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.Before;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SyncSingletonTest {
    SyncSingleton sync;
    UserSingleton user;

    /*
    Methods are named to have them be tested in a certain order
    This is needed to test certain functions as it will always happen in a specified order
     */

    @Before
    public void setup() throws Exception {
        sync = SyncSingleton.getInstance();
        user = UserSingleton.getInstance();
    }

    /**
     * Tests to make sure the companion device can be added
     */
    @Test
    public void a_testAddCompanion() {
        boolean addResult = sync.addCompanion();
        assertTrue("Companion added successfully", addResult);
    }

    /**
     * Checks to make sure that out sync process completed successfully
     */
    @Test
    public void b_testSyncData() throws Exception {
        sync.syncData("admin|password|male|06-22-1997|182|79");
        Thread.sleep(3000);
        assertTrue("Sync process completed", sync.successful());
    }

    /**
     * Tests to makes sure the user data is set up after the parse
     */
    @Test
    public void c_testParseData() {
        assertNotNull("Username was set", user.username);
        assertNotNull("Gender was set", user.getGender());
        assertNotNull("DOB was set", user.getBirthday());
        assertNotEquals("Height was set", 0, user.getHeight());
        assertNotEquals("Weight was set", 0, user.getWeight());
    }
}
