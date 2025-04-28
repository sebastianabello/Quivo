package com.quivo.booking_service.domain;

import com.quivo.booking_service.domain.model.BookingCreateEvent;
import com.quivo.booking_service.domain.model.BookingItem;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookingEventMapper {

    static BookingCreateEvent buildBookingCreateEvent(BookingEntity bookingEntity) {
        return new BookingCreateEvent(
                UUID.randomUUID().toString(),
                bookingEntity.getReservationNumber(),
                getBookingItems(bookingEntity),
                bookingEntity.getCustomer(),
                bookingEntity.getCheckDate(),
                LocalDateTime.now()
        )
        ;
    }

    private static Set<BookingItem> getBookingItems(BookingEntity bookingEntity) {
        return bookingEntity.getReservationItems().stream()
                .map(item -> new BookingItem(item.getCode(),
                        item.getName(), item.getPrice(), item.getGuest()))
                .collect(Collectors.toSet());
    }
}
