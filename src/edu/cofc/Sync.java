package edu.cofc;

import java.util.Date;

/**
 * Created by Claudio on 20/11/2017.
 */
public class Sync {
    private Companion companionDevice;
    private User deviceUser;

    public Sync(User userData) {
        companionDevice = new Companion();
        deviceUser = userData;
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
        //So maybe at the original open of the app we sync data and this sets up the user

        //parse here, just using raw data for project purposes
        deviceUser.addLogin("admin", "password");
        deviceUser.addBirthday(new Date());
        deviceUser.addGender("male");
        deviceUser.addHeightWeight(182, 79); //height - cm, weight - kg
    }

    /**
     * Runs the sync operation
     * @param ourData
     */
    public void syncData(String ourData) {
        Runnable syncThread = new Runnable() {
            public void run() {
                try {
                    if(!companionDevice.send(ourData)) {
                        System.out.println("Send Failed");
                    }

                    Thread.sleep(2500); //Sleep two and a half seconds to simulate the sync

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
