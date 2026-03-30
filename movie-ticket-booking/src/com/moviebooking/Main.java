package com.moviebooking;

import com.moviebooking.models.Address;
import com.moviebooking.models.Movie;
import com.moviebooking.models.Reservation;
import com.moviebooking.models.Showtime;
import com.moviebooking.models.Theater;
import com.moviebooking.models.User;
import com.moviebooking.system.BookingSystem;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=========================================");
        System.out.println("  MOVIE TICKET BOOKING SYSTEM - LLD");
        System.out.println("  Inspired by BookMyShow");
        System.out.println("=========================================");

        BookingSystem system = BookingSystem.getInstance();

        Movie movie1 = system.addMovie("Inception", "Sci-Fi", 148, "English");
        Movie movie2 = system.addMovie("RRR", "Action", 182, "Hindi");
        Movie movie3 = system.addMovie("Interstellar", "Sci-Fi", 169, "English");

        Address addr1 = new Address("123 MG Road", "Bangalore", "Karnataka", "560001");
        Address addr2 = new Address("456 Anna Salai", "Chennai", "Tamil Nadu", "600002");

        Theater theater1 = system.addTheater("PVR Cinemas", addr1, 3);
        Theater theater2 = system.addTheater("INOX", addr2, 2);

        LocalDate today = LocalDate.now();
        Showtime showtime1 = system.addShowtime(movie1, theater1, 1, today, LocalTime.of(10, 0));
        Showtime showtime2 = system.addShowtime(movie1, theater1, 2, today, LocalTime.of(14, 0));
        Showtime showtime3 = system.addShowtime(movie2, theater2, 1, today, LocalTime.of(18, 0));
        Showtime showtime4 = system.addShowtime(movie3, theater1, 3, today, LocalTime.of(20, 30));

        User user1 = system.registerUser("Alice", "alice@example.com", "9876543210");
        User user2 = system.registerUser("Bob", "bob@example.com", "9123456789");
        User user3 = system.registerUser("Charlie", "charlie@example.com", "9000011111");

        System.out.println("\n--- SCENARIO 1: Search Movies by Title ---");
        List<Movie> results = system.searchMoviesByTitle("inc");
        System.out.println("  Search 'inc' -> " + results.size() + " result(s):");
        results.forEach(m -> System.out.println("  " + m));

        System.out.println("\n--- SCENARIO 2: Browse Showtimes for a Movie ---");
        List<Showtime> inceptionShowtimes = system.getShowtimesForMovie(movie1.getMovieId());
        System.out.println("  Showtimes for 'Inception':");
        inceptionShowtimes.forEach(s -> System.out.println("  " + s));

        System.out.println("\n--- SCENARIO 3: Browse Showtimes at a Theater ---");
        List<Showtime> pvrShowtimes = system.getShowtimesForTheater(theater1.getTheaterId());
        System.out.println("  Showtimes at PVR Cinemas:");
        pvrShowtimes.forEach(s -> System.out.println("  " + s));

        System.out.println("\n--- SCENARIO 4: View Available Seats ---");
        List<String> available = system.getAvailableSeats(showtime1.getShowtimeId());
        System.out.println("  Total available: " + available.size() + " seats (26 rows x 21 seats = 546)");
        System.out.println("  Sample seats: " + available.subList(0, 6));

        System.out.println("\n--- SCENARIO 5: Book Multiple Seats ---");
        Reservation res1 = system.bookSeats(user1.getUserId(), showtime1.getShowtimeId(),
                Arrays.asList("A0", "A1", "A2"));
        Reservation res2 = system.bookSeats(user2.getUserId(), showtime1.getShowtimeId(),
                Arrays.asList("B0", "B1", "B2", "B3"));
        System.out.println("  " + res1);
        System.out.println("  " + res2);
        System.out.println("  Available after bookings: "
                + system.getAvailableSeats(showtime1.getShowtimeId()).size());

        System.out.println("\n--- SCENARIO 6: Concurrent Booking of Same Seat ---");
        System.out.println("  Two threads attempt to book seat C5 simultaneously...");

        CountDownLatch latch = new CountDownLatch(1);
        String[] results2 = new String[2];

        Thread t1 = new Thread(() -> {
            try {
                latch.await();
                Reservation r = system.bookSeats(user1.getUserId(), showtime2.getShowtimeId(),
                        Arrays.asList("C5", "C6"));
                results2[0] = "Thread-1 SUCCESS -> Confirmation: " + r.getConfirmationId();
            } catch (Exception e) {
                results2[0] = "Thread-1 FAILED -> " + e.getMessage();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                latch.await();
                Reservation r = system.bookSeats(user2.getUserId(), showtime2.getShowtimeId(),
                        Arrays.asList("C5", "C7"));
                results2[1] = "Thread-2 SUCCESS -> Confirmation: " + r.getConfirmationId();
            } catch (Exception e) {
                results2[1] = "Thread-2 FAILED -> " + e.getMessage();
            }
        });

        t1.start();
        t2.start();
        latch.countDown();
        t1.join();
        t2.join();

        System.out.println("  " + results2[0]);
        System.out.println("  " + results2[1]);

        System.out.println("\n--- SCENARIO 7: Cancel Reservation ---");
        System.out.println("  Before cancel -> Status: " + res1.getBookingStatus()
                + " | Payment: " + res1.getPaymentStatus());
        system.cancelReservation(res1.getConfirmationId());
        System.out.println("  After cancel  -> Status: " + res1.getBookingStatus()
                + " | Payment: " + res1.getPaymentStatus());
        System.out.println("  Available after cancellation: "
                + system.getAvailableSeats(showtime1.getShowtimeId()).size());

        System.out.println("\n--- SCENARIO 8: Book Unavailable Seat (Exception Handling) ---");
        try {
            system.bookSeats(user3.getUserId(), showtime1.getShowtimeId(),
                    Arrays.asList("B0", "Z20"));
        } catch (IllegalStateException e) {
            System.out.println("  Caught expected exception: " + e.getMessage());
        }

        System.out.println("\n=========================================");
        System.out.println("  SIMULATION COMPLETE");
        System.out.println("=========================================");
    }
}
