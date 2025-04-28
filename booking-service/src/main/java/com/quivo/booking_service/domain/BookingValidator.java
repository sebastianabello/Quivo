package com.quivo.booking_service.domain;

import com.quivo.booking_service.client.Inventory.Room;
import com.quivo.booking_service.client.Inventory.RoomServiceClient;
import com.quivo.booking_service.domain.model.BookingItem;
import com.quivo.booking_service.domain.model.CreateBookingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class BookingValidator {
    private static final Logger log = LoggerFactory.getLogger(BookingValidator.class);

    private final RoomServiceClient roomServiceClient;

    public BookingValidator(RoomServiceClient roomServiceClient) {
        this.roomServiceClient = roomServiceClient;
    }

    void validate(CreateBookingRequest request) {
        Set<BookingItem> items = request.items();
        for (BookingItem item : items) {
            Room room = roomServiceClient.getRoomByCode(item.code())
                    .orElseThrow(() -> new InvalidBookingException("Invalid Product code:" + item.code()));
            if (item.price().compareTo(room.price()) != 0) {
                log.error("Invalid booking price:{}", item.price());
                throw new InvalidBookingException("Invalid Product price:" + item.code());
            }
        }
    }
}
