package com.quivo.inventory_service.domain;

public class RoomMapper {
    static Room toRoom(RoomEntity roomEntity) {
        return new Room(
                roomEntity.getCode(),
                roomEntity.getName(),
                roomEntity.getDescription(),
                roomEntity.getImageUrl(),
                roomEntity.getPrice(),
                roomEntity.getStatus());
    }
}
