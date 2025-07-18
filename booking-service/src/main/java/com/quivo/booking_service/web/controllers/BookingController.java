package com.quivo.booking_service.web.controllers;

import com.quivo.booking_service.domain.BookingNotFoundException;
import com.quivo.booking_service.domain.BookingService;
import com.quivo.booking_service.domain.SecurityService;
import com.quivo.booking_service.domain.model.*;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    private final BookingService bookingService;
    private final SecurityService securityService;

    public BookingController(BookingService bookingService, SecurityService securityService) {
        this.bookingService = bookingService;
        this.securityService = securityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateBookingResponse createBooking(@Valid @RequestBody CreateBookingRequest request) {
        String username = securityService.getLoginUser();
        log.info("Creating booking for user {}", username);
        return bookingService.createBooking(username, request);
    }

    @GetMapping
    List<BookingSummary> getBookings() {
        String username = securityService.getLoginUser();
        log.info("Getting bookings for user {}", username);
        return bookingService.findReservations(username);
    }

    @GetMapping(value = "/{reservationNumber}")
    BookingDTO getBooking(@PathVariable(value = "reservationNumber") String reservationNumber) {
        String username = securityService.getLoginUser();
        log.info("Getting booking {}", reservationNumber);
        return bookingService
                .findUserReservation(username, reservationNumber)
                .orElseThrow(() -> new BookingNotFoundException(reservationNumber));
    }

    @PutMapping("/{reservationNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBooking(
            @PathVariable String reservationNumber, @Valid @RequestBody UpdateBookingRequest request) {
        String username = securityService.getLoginUser();
        log.info("Updating booking {} for user {}", reservationNumber, username);
        bookingService.updateBooking(reservationNumber, request);
    }

    @DeleteMapping("/{reservationNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelBooking(
            @PathVariable String reservationNumber, @RequestBody @Valid CancelBookingRequest request) {
        String username = securityService.getLoginUser();
        log.info("Cancelling booking {} for user {}", reservationNumber, username);
        bookingService.cancelBooking(reservationNumber, request.reason());
    }
}
