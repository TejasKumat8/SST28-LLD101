package com.moviebooking.models;

import java.util.UUID;

public class User {

    private final String userId;
    private final String name;
    private final String email;
    private final String mobileNumber;

    public User(String name, String email, String mobileNumber) {
        this.userId = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    @Override
    public String toString() {
        return "User{name=" + name + ", email=" + email + "}";
    }
}
