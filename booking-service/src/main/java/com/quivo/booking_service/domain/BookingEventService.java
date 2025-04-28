package com.quivo.booking_service.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quivo.booking_service.domain.model.BookingCreateEvent;
import com.quivo.booking_service.domain.model.BookingEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookingEventService {
    private static final Logger log = LoggerFactory.getLogger(BookingEventService.class);

    private final BookingEventRepository bookingEventRepository;
    private final BookingEventPublisher bookingEventPublisher;
    private final ObjectMapper objectMapper;

    public BookingEventService(BookingEventRepository bookingEventRepository, BookingEventPublisher bookingEventPublisher, ObjectMapper objectMapper) {
        this.bookingEventRepository = bookingEventRepository;
        this.bookingEventPublisher = bookingEventPublisher;
        this.objectMapper = objectMapper;
    }

    void save(BookingCreateEvent event) {
        BookingEventEntity bookingEventEntity = new BookingEventEntity();
        bookingEventEntity.setEventId(event.eventId());
        bookingEventEntity.setEventType(BookingEventType.BOOKING_CREATED);
        bookingEventEntity.setReservationNumber(event.reservationNumber());
        bookingEventEntity.setCreatedAt(event.createAt());
        bookingEventEntity.setPayload(toJsonPayload(event));
        this.bookingEventRepository.save(bookingEventEntity);
    }

    public void publishBookingEvents() {
        Sort sort = Sort.by( "createdAt").ascending();
        List<BookingEventEntity> bookingEventEntities = bookingEventRepository.findAll(sort);
        log.info("Booking events published: {}", bookingEventEntities.size());
        for (BookingEventEntity bookingEventEntity : bookingEventEntities) {
           this.publishEvent(bookingEventEntity);
           bookingEventRepository.delete(bookingEventEntity);
        }
    }

    private void publishEvent(BookingEventEntity event) {
        BookingEventType eventType = event.getEventType();
        switch (eventType) {
            case BOOKING_CREATED:
                BookingCreateEvent bookingCreateEvent = fromJsonPayload(event.getPayload(), BookingCreateEvent.class);
                bookingEventPublisher.publish(bookingCreateEvent);
                break;
            default:
                log.warn("Event type not recognized: {}", eventType);
        }

    }

    private String toJsonPayload(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T fromJsonPayload(String payload, Class<T> clazz) {
        try {
            return objectMapper.readValue(payload, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
