package com.example.project2;

public class location {
    String userID;
    String location;
    String address;
    String rating;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

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

    public location(String userID, String location, String address, String rating) {
        this.userID = userID;
        this.location = location;
        this.address = address;
        this.rating=rating;
    }
}
