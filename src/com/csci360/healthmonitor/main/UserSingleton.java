package com.csci360.healthmonitor.main;

import java.util.Date;

public class UserSingleton {
    //UserSingleton Attributes
    public String username;
    private String password;
    private Date birthday;
    private int height;
    private int weight;
    private String gender;
    private static UserSingleton instance;

    protected UserSingleton()
    {
    }

    public static UserSingleton getInstance() {
        if(instance == null) {
            instance = new UserSingleton();
        }
        return instance;
    }

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
