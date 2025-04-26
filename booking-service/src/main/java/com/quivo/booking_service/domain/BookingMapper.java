package com.quivo.booking_service.domain;

import com.quivo.booking_service.domain.model.BookingItem;
import com.quivo.booking_service.domain.model.CreateBookingRequest;
import com.quivo.booking_service.domain.model.OrderStatus;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BookingMapper {

    static BookingEntity convertToEntity(CreateBookingRequest request) {
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setReservationNumber(UUID.randomUUID().toString());
        bookingEntity.setStatus(OrderStatus.NEW);
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
}
