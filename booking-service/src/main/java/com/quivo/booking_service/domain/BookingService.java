package com.quivo.booking_service.domain;

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

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public CreateBookingResponse createBooking(String username, CreateBookingRequest request) {
        BookingEntity bookingEntity = BookingMapper.convertToEntity(request);
        bookingEntity.setUsername(username);
        BookingEntity savedBooking = this.bookingRepository.save(bookingEntity);
        log.info("Booking created: {}", savedBooking.getReservationNumber());
        return new CreateBookingResponse(savedBooking.getReservationNumber());
    }
}
