package com.stjohns.stormchat.Objects.User;

/**
 * Created by nick on 11/6/17.
 */

public class User {

    private String name;
    private String email;
    private String imagePath;
    private String bio;

    private String college;
    private String major;


    public User(String name, String email, String imagePath, String bio, String college, String major)
    {
        this.name = name;
        this.email = email;
        this.imagePath = imagePath;
        this.bio = bio;

        this.college = college;
        this.major = major;

    }

    public User() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }



}
