package com.quivo.booking_service.web.controllers;

import com.quivo.booking_service.domain.BookingService;
import com.quivo.booking_service.domain.SecurityService;
import com.quivo.booking_service.domain.model.CreateBookingRequest;
import com.quivo.booking_service.domain.model.CreateBookingResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    private final BookingService orderService;
    private final SecurityService securityService;

    public BookingController(BookingService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateBookingResponse createBooking(@Valid @RequestBody CreateBookingRequest request) {
        String username = securityService.getLoginUser();
        log.info("Creating booking for user {}", username);
        return orderService.createBooking(username, request);
    }
}
