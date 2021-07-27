package com.example.project2;

public class bookmark {
    String userID;
    String location;
    String address;


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public bookmark(String userID, String location, String address) {
        this.userID = userID;
        this.location = location;
        this.address = address;
    }
}
