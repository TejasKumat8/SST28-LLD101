package com.moviebooking.models;

public class Seat {

    private final char row;
    private final int number;
    private final String seatId;

    public Seat(char row, int number) {
        this.row = row;
        this.number = number;
        this.seatId = String.valueOf(row) + number;
    }

    public char getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }

    public String getSeatId() {
        return seatId;
    }

    @Override
    public String toString() {
        return seatId;
    }
}
