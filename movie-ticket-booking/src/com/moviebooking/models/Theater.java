package com.moviebooking.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Theater {

    private final String theaterId;
    private final String name;
    private final Address address;
    private final List<Screen> screens;

    public Theater(String name, Address address, int numberOfScreens) {
        this.theaterId = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.screens = new ArrayList<>();
        for (int i = 1; i <= numberOfScreens; i++) {
            screens.add(new Screen(i));
        }
    }

    public String getTheaterId() {
        return theaterId;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public List<Screen> getScreens() {
        return screens;
    }

    public Screen getScreen(int screenNumber) {
        return screens.get(screenNumber - 1);
    }

    @Override
    public String toString() {
        return "Theater{name=" + name + ", city=" + address.getCity()
                + ", screens=" + screens.size() + "}";
    }
}
