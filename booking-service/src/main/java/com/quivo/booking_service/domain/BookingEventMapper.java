package com.quivo.booking_service.domain;

import com.quivo.booking_service.domain.model.*;
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
                LocalDateTime.now());
    }

    static BookingReservedEvent buildBookingReservedEvent(BookingEntity bookingEntity) {
        return new BookingReservedEvent(
                UUID.randomUUID().toString(),
                bookingEntity.getReservationNumber(),
                getBookingItems(bookingEntity),
                bookingEntity.getCustomer(),
                bookingEntity.getCheckDate(),
                LocalDateTime.now());
    }

    static BookingCancelledEvent buildBookingCancelledEvent(BookingEntity bookingEntity, String reason) {
        return new BookingCancelledEvent(
                UUID.randomUUID().toString(),
                bookingEntity.getReservationNumber(),
                getBookingItems(bookingEntity),
                bookingEntity.getCustomer(),
                bookingEntity.getCheckDate(),
                reason,
                LocalDateTime.now());
    }

    static BookingErrorEvent buildBookingErrorEvent(BookingEntity order, String reason) {
        return new BookingErrorEvent(
                UUID.randomUUID().toString(),
                order.getReservationNumber(),
                getBookingItems(order),
                order.getCustomer(),
                order.getCheckDate(),
                reason,
                LocalDateTime.now());
    }

    private static Set<BookingItem> getBookingItems(BookingEntity bookingEntity) {
        return bookingEntity.getReservationItems().stream()
                .map(item -> new BookingItem(item.getCode(), item.getName(), item.getPrice(), item.getGuest()))
                .collect(Collectors.toSet());
    }
}
