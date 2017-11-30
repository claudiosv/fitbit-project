package com.csci360.healthmonitor.main;

import java.util.Date;

/**
 * Created by Claudio on 20/11/2017.
 */
public class SyncSingleton {
    private Companion companionDevice;
    private UserSingleton deviceUser;
    private static SyncSingleton instance = null;

    protected SyncSingleton() {
        companionDevice = new Companion();
        deviceUser = deviceUser.getInstance();
    }

    public static SyncSingleton getInstance() {
        if(instance == null) {
            instance = new SyncSingleton();
        }
        return instance;
    }

    /**
     * Checks that the companion device was connected successfully
     * @return
     */
    public boolean addCompanion() {
        if(companionDevice.connect())
            return true;
        return false;
    }

    /**
     * parses received data from companion device
     * @param data
     */
    private void parseIncomingData(String data) {
        //data incoming would normally be to set up the user info
        //So maybe at the original open of the app we syncSingleton data and this sets up the user

        //parse here, just using raw data for project purposes
        deviceUser.addLogin("admin", "password");
        deviceUser.addBirthday(new Date());
        deviceUser.addGender("male");
        deviceUser.addHeightWeight(182, 79); //height - cm, weight - kg
    }

    /**
     * Runs the syncSingleton operation
     * @param ourData
     */
    public void syncData(String ourData) {
        Runnable syncThread = new Runnable() {
            public void run() {
                try {
                    if(!companionDevice.send(ourData)) {
                        System.out.println("Send Failed");
                    }

                    Thread.sleep(2500); //Sleep two and a half seconds to simulate the syncSingleton

                    String recvData = companionDevice.receive();
                    if(recvData == null) {
                        System.out.println("Receive Failed");
                    }

                    parseIncomingData(recvData);
                }
                catch (InterruptedException e) {
                    //handle exception
                }
            }
        };

        Thread syncDevice = new Thread(syncThread);
        syncDevice.start();
    }
}
