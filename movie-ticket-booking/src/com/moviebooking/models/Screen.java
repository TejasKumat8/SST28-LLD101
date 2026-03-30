package com.moviebooking.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Screen {

    private static final int TOTAL_ROWS = 26;
    private static final int SEATS_PER_ROW = 21;

    private final String screenId;
    private final int screenNumber;
    private final List<Seat> seats;

    public Screen(int screenNumber) {
        this.screenId = UUID.randomUUID().toString();
        this.screenNumber = screenNumber;
        this.seats = initializeSeats();
    }

    private List<Seat> initializeSeats() {
        List<Seat> seatList = new ArrayList<>();
        for (int row = 0; row < TOTAL_ROWS; row++) {
            char rowChar = (char) ('A' + row);
            for (int num = 0; num < SEATS_PER_ROW; num++) {
                seatList.add(new Seat(rowChar, num));
            }
        }
        return seatList;
    }

    public String getScreenId() {
        return screenId;
    }

    public int getScreenNumber() {
        return screenNumber;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public int getTotalCapacity() {
        return TOTAL_ROWS * SEATS_PER_ROW;
    }

    @Override
    public String toString() {
        return "Screen{number=" + screenNumber + ", capacity=" + getTotalCapacity() + "}";
    }
}
