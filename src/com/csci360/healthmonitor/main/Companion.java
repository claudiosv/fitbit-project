package com.csci360.healthmonitor.main;

public class Companion {
    private boolean connected;

    /**
     * Disconnecting our companion device
     * @return
     */
    public boolean disconnect() {
        connected = false;
        return true;
    }

    /**
     * Sending data to our companion device
     * @param data
     * @return
     */
    public boolean send(String data) {
        if(!connected)
            return false;

        //For sake of the project send always succeeds
        return true;
    }

    /**
     * Receiving data from our companion device
     * @return
     */
    public String receive() {
        if(!connected)
            return null;

        //This would come from the companion device in a real scenario
        return "Synced Data";
    }

    /**
     * Connect the companion device
     * @return
     */
    public boolean connect() {
        connected = true;
        //For sake of the project connect always succeeds
        return true;
    }
}
