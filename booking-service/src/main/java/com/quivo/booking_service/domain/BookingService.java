package com.quivo.booking_service.domain;

import com.quivo.booking_service.domain.model.*;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);
    private static final String BOOKING_PROCESSING_FAILED = "Booking processing failed";

    private final BookingRepository bookingRepository;
    private final BookingValidator roomValidator;
    private final BookingEventService bookingEventService;

    BookingService(
            BookingRepository bookingRepository,
            BookingValidator roomValidator,
            BookingEventService bookingEventService) {
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

    public List<BookingSummary> findReservations(String username) {
        return bookingRepository.findByUsername(username);
    }

    public Optional<BookingDTO> findUserReservation(String username, String reservationNumber) {
        return bookingRepository
                .findByUsernameAndReservationNumber(username, reservationNumber)
                .map(BookingMapper::convertToDTO);
    }

    public void processNewReservations() {
        List<BookingEntity> bookingEntities = bookingRepository.findByStatus(BookingStatus.NEW);
        log.info("Processing new reservations");
        for (BookingEntity bookingEntity : bookingEntities) {
            this.process(bookingEntity);
        }
    }

    private void process(BookingEntity bookingEntity) {
        try {
            log.info("Processing reservation: {}", bookingEntity.getReservationNumber());
            bookingRepository.updateBookingStatus(bookingEntity.getReservationNumber(), BookingStatus.COMPLETED);
            bookingEventService.save(BookingEventMapper.buildBookingReservedEvent(bookingEntity));

        } catch (RuntimeException e) {
            log.error("Error processing booking: {}", bookingEntity.getReservationNumber(), e);
            bookingRepository.updateBookingStatus(bookingEntity.getReservationNumber(), BookingStatus.FAILED);
            bookingEventService.save(BookingEventMapper.buildBookingErrorEvent(bookingEntity, e.getMessage()));
        }
    }
}
