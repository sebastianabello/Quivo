package com.quivo.booking_service.domain;

public class InvalidBookingException extends RuntimeException {

    public InvalidBookingException(String message) {
        super(message);
    }
}
