package com.quivo.booking_service.domain;

import com.quivo.booking_service.domain.model.BookingCreateEvent;
import com.quivo.booking_service.domain.model.CreateBookingRequest;
import com.quivo.booking_service.domain.model.CreateBookingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final BookingValidator roomValidator;
    private final BookingEventService bookingEventService;

    BookingService(BookingRepository bookingRepository, BookingValidator roomValidator, BookingEventService bookingEventService) {
        this.bookingRepository = bookingRepository;
        this.roomValidator = roomValidator;
        this.bookingEventService = bookingEventService;
    }

    public CreateBookingResponse createBooking(String username, CreateBookingRequest request) {
        roomValidator.validate(request);
        BookingEntity bookingEntity = BookingMapper.convertToEntity(request);
        bookingEntity.setUsername(username);
        BookingEntity savedBooking = this.bookingRepository.save(bookingEntity);
        log.info("Booking created: {}", savedBooking.getReservationNumber());
        BookingCreateEvent bookingCreateEvent = BookingEventMapper.buildBookingCreateEvent(savedBooking);
        bookingEventService.save(bookingCreateEvent);
        return new CreateBookingResponse(savedBooking.getReservationNumber());
    }
}
