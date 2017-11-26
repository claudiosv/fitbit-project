package edu.cofc;

import java.util.Date;

/**
 * Created by Claudio on 20/11/2017.
 */
public class User {
    //User Attributes
    public String username;
    private String password;
    private Date birthday;
    private int height;
    private int weight;
    private String gender;

    public void addLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void addBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void addHeightWeight(int height, int weight) {
        this.height = height;
        this.weight = weight;
    }

    public void addGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return this.username;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWeight() {
        return this.weight;
    }

    public String getGender() {
        return this.gender;
    }

    public boolean beingSetup() {
        return false;
    }
}
