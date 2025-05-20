package com.quivo.inventory_service.web.controllers;

import com.quivo.inventory_service.domain.PagedResult;
import com.quivo.inventory_service.domain.Room;
import com.quivo.inventory_service.domain.RoomService;
import com.quivo.inventory_service.domain.model.CreateRoomRequest;
import com.quivo.inventory_service.domain.model.UpdateRoomRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{code}")
    ResponseEntity<Room> getRoomByCode(@PathVariable("code") String code) {
        return roomService
                .getRoomByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRoom(@Valid @RequestBody CreateRoomRequest request) {
        roomService.createRoom(request);
    }

    @PutMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRoom(
            @PathVariable String code,
            @Valid @RequestBody UpdateRoomRequest request) {
        roomService.updateRoom(code, request);
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateRoom(@PathVariable String code) {
        roomService.deactivateRoom(code);
    }


}
