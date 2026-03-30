package com.moviebooking.models;

public class Address {

    private final String streetNo;
    private final String city;
    private final String state;
    private final String pinCode;

    public Address(String streetNo, String city, String state, String pinCode) {
        this.streetNo = streetNo;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPinCode() {
        return pinCode;
    }

    @Override
    public String toString() {
        return streetNo + ", " + city + ", " + state + " - " + pinCode;
    }
}
