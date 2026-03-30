package com.moviebooking.models;

import com.moviebooking.enums.SeatStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Showtime {

    private final String showtimeId;
    private final Movie movie;
    private final Screen screen;
    private final LocalDate date;
    private final LocalTime startTime;
    private final Map<String, SeatStatus> seatAvailability;

    public Showtime(Movie movie, Screen screen, LocalDate date, LocalTime startTime) {
        this.showtimeId = UUID.randomUUID().toString();
        this.movie = movie;
        this.screen = screen;
        this.date = date;
        this.startTime = startTime;
        this.seatAvailability = new ConcurrentHashMap<>();
        for (Seat seat : screen.getSeats()) {
            seatAvailability.put(seat.getSeatId(), SeatStatus.AVAILABLE);
        }
    }

    public synchronized List<String> bookSeats(List<String> seatIds) {
        for (String seatId : seatIds) {
            if (!seatAvailability.containsKey(seatId)) {
                throw new IllegalArgumentException("Seat " + seatId + " does not exist in this screen.");
            }
            if (seatAvailability.get(seatId) == SeatStatus.BOOKED) {
                throw new IllegalStateException("Seat " + seatId + " is already booked.");
            }
        }
        for (String seatId : seatIds) {
            seatAvailability.put(seatId, SeatStatus.BOOKED);
        }
        return seatIds;
    }

    public synchronized void releaseSeats(List<String> seatIds) {
        for (String seatId : seatIds) {
            seatAvailability.put(seatId, SeatStatus.AVAILABLE);
        }
    }

    public List<String> getAvailableSeats() {
        List<String> available = new ArrayList<>();
        for (Map.Entry<String, SeatStatus> entry : seatAvailability.entrySet()) {
            if (entry.getValue() == SeatStatus.AVAILABLE) {
                available.add(entry.getKey());
            }
        }
        return available;
    }

    public SeatStatus getSeatStatus(String seatId) {
        return seatAvailability.getOrDefault(seatId, null);
    }

    public String getShowtimeId() {
        return showtimeId;
    }

    public Movie getMovie() {
        return movie;
    }

    public Screen getScreen() {
        return screen;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return "Showtime{movie=" + movie.getTitle() + ", screen=" + screen.getScreenNumber()
                + ", date=" + date + ", time=" + startTime + "}";
    }
}
