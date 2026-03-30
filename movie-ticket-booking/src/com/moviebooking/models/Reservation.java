package com.moviebooking.models;

import com.moviebooking.enums.BookingStatus;
import com.moviebooking.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Reservation {

    private final String confirmationId;
    private final User user;
    private final Showtime showtime;
    private final List<String> bookedSeatIds;
    private BookingStatus bookingStatus;
    private PaymentStatus paymentStatus;
    private final LocalDateTime bookedAt;

    public Reservation(User user, Showtime showtime, List<String> bookedSeatIds) {
        this.confirmationId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.user = user;
        this.showtime = showtime;
        this.bookedSeatIds = bookedSeatIds;
        this.bookingStatus = BookingStatus.CONFIRMED;
        this.paymentStatus = PaymentStatus.PAID;
        this.bookedAt = LocalDateTime.now();
    }

    public void cancel() {
        this.bookingStatus = BookingStatus.CANCELLED;
        this.paymentStatus = PaymentStatus.REFUNDED;
        showtime.releaseSeats(bookedSeatIds);
    }

    public String getConfirmationId() {
        return confirmationId;
    }

    public User getUser() {
        return user;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public List<String> getBookedSeatIds() {
        return bookedSeatIds;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    @Override
    public String toString() {
        return "Reservation{id=" + confirmationId
                + ", user=" + user.getName()
                + ", movie=" + showtime.getMovie().getTitle()
                + ", seats=" + bookedSeatIds
                + ", status=" + bookingStatus
                + ", payment=" + paymentStatus + "}";
    }
}
