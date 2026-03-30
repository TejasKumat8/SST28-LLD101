package com.moviebooking.system;

import com.moviebooking.models.Address;
import com.moviebooking.models.Movie;
import com.moviebooking.models.Reservation;
import com.moviebooking.models.Screen;
import com.moviebooking.models.Showtime;
import com.moviebooking.models.Theater;
import com.moviebooking.models.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BookingSystem {

    private static volatile BookingSystem instance;

    private final Map<String, Movie> movies;
    private final Map<String, Theater> theaters;
    private final Map<String, Showtime> showtimes;
    private final Map<String, User> users;
    private final Map<String, Reservation> reservations;

    private BookingSystem() {
        this.movies = new ConcurrentHashMap<>();
        this.theaters = new ConcurrentHashMap<>();
        this.showtimes = new ConcurrentHashMap<>();
        this.users = new ConcurrentHashMap<>();
        this.reservations = new ConcurrentHashMap<>();
    }

    public static BookingSystem getInstance() {
        if (instance == null) {
            synchronized (BookingSystem.class) {
                if (instance == null) {
                    instance = new BookingSystem();
                }
            }
        }
        return instance;
    }

    public Movie addMovie(String title, String genre, int durationMinutes, String language) {
        Movie movie = new Movie(title, genre, durationMinutes, language);
        movies.put(movie.getMovieId(), movie);
        return movie;
    }

    public Theater addTheater(String name, Address address, int numberOfScreens) {
        Theater theater = new Theater(name, address, numberOfScreens);
        theaters.put(theater.getTheaterId(), theater);
        return theater;
    }

    public Showtime addShowtime(Movie movie, Theater theater, int screenNumber,
                                LocalDate date, LocalTime startTime) {
        Screen screen = theater.getScreen(screenNumber);
        Showtime showtime = new Showtime(movie, screen, date, startTime);
        showtimes.put(showtime.getShowtimeId(), showtime);
        return showtime;
    }

    public User registerUser(String name, String email, String mobileNumber) {
        User user = new User(name, email, mobileNumber);
        users.put(user.getUserId(), user);
        return user;
    }

    public List<Movie> searchMoviesByTitle(String searchTerm) {
        return movies.values().stream()
                .filter(m -> m.getTitle().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Showtime> getShowtimesForMovie(String movieId) {
        return showtimes.values().stream()
                .filter(s -> s.getMovie().getMovieId().equals(movieId))
                .collect(Collectors.toList());
    }

    public List<Showtime> getShowtimesForTheater(String theaterId) {
        Theater theater = theaters.get(theaterId);
        if (theater == null) {
            return Collections.emptyList();
        }
        Set<String> theaterScreenIds = new HashSet<>();
        for (Screen screen : theater.getScreens()) {
            theaterScreenIds.add(screen.getScreenId());
        }
        return showtimes.values().stream()
                .filter(s -> theaterScreenIds.contains(s.getScreen().getScreenId()))
                .collect(Collectors.toList());
    }

    public List<String> getAvailableSeats(String showtimeId) {
        Showtime showtime = showtimes.get(showtimeId);
        if (showtime == null) {
            throw new IllegalArgumentException("Showtime not found: " + showtimeId);
        }
        List<String> available = new ArrayList<>(showtime.getAvailableSeats());
        Collections.sort(available);
        return available;
    }

    public Reservation bookSeats(String userId, String showtimeId, List<String> seatIds) {
        User user = users.get(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
        Showtime showtime = showtimes.get(showtimeId);
        if (showtime == null) {
            throw new IllegalArgumentException("Showtime not found: " + showtimeId);
        }
        List<String> booked = showtime.bookSeats(seatIds);
        Reservation reservation = new Reservation(user, showtime, booked);
        reservations.put(reservation.getConfirmationId(), reservation);
        System.out.println("[BookingSystem] Confirmed: " + reservation.getConfirmationId()
                + " | User: " + user.getName() + " | Seats: " + seatIds);
        return reservation;
    }

    public void cancelReservation(String confirmationId) {
        Reservation reservation = reservations.get(confirmationId);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found: " + confirmationId);
        }
        reservation.cancel();
        System.out.println("[BookingSystem] Cancelled: " + confirmationId
                + " | Seats released: " + reservation.getBookedSeatIds());
    }

    public Reservation getReservation(String confirmationId) {
        return reservations.get(confirmationId);
    }

    public Map<String, Movie> getAllMovies() {
        return movies;
    }

    public Map<String, Theater> getAllTheaters() {
        return theaters;
    }
}
