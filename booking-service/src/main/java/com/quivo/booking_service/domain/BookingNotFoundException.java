package com.quivo.booking_service.domain;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(String message) {
        super(message);
    }

    public static BookingNotFoundException forBookingNumber(String bookingNumber) {
        return new BookingNotFoundException("Booking not found for booking number: " + bookingNumber);
    }
}
