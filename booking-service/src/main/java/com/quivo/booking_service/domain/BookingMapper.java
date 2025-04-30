package com.quivo.booking_service.domain;

import com.quivo.booking_service.domain.model.BookingDTO;
import com.quivo.booking_service.domain.model.BookingItem;
import com.quivo.booking_service.domain.model.BookingStatus;
import com.quivo.booking_service.domain.model.CreateBookingRequest;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookingMapper {

    static BookingEntity convertToEntity(CreateBookingRequest request) {
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setReservationNumber(UUID.randomUUID().toString());
        bookingEntity.setStatus(BookingStatus.NEW);
        bookingEntity.setCustomer(request.customer());
        bookingEntity.setCheckDate(request.checkDate());

        Set<BookingItemEntity> bookingItemEntities = new HashSet<>();
        for (BookingItem item : request.items()) {
            BookingItemEntity bookingItemEntity = new BookingItemEntity();
            bookingItemEntity.setCode(item.code());
            bookingItemEntity.setName(item.name());
            bookingItemEntity.setPrice(item.price());
            bookingItemEntity.setGuest(item.guest());
            bookingItemEntity.setReservation(bookingEntity);
            bookingItemEntities.add(bookingItemEntity);
        }
        bookingEntity.setReservationItems(bookingItemEntities);
        return bookingEntity;
    }

    static BookingDTO convertToDTO(BookingEntity bookingEntity) {
        Set<BookingItem> items = bookingEntity.getReservationItems().stream()
                .map(item -> new BookingItem(item.getCode(), item.getName(), item.getPrice(), item.getGuest()))
                .collect(Collectors.toSet());

        return new BookingDTO(
                bookingEntity.getReservationNumber(),
                bookingEntity.getUsername(),
                items,
                bookingEntity.getCustomer(),
                bookingEntity.getCheckDate(),
                bookingEntity.getStatus(),
                bookingEntity.getComments(),
                bookingEntity.getCreatedAt());
    }
}
