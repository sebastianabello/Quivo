package com.quivo.inventory_service.web.controllers;

import com.quivo.inventory_service.domain.PagedResult;
import com.quivo.inventory_service.domain.Room;
import com.quivo.inventory_service.domain.RoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    PagedResult<Room> getRooms(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        return roomService.getRooms(pageNo);
    }
}
