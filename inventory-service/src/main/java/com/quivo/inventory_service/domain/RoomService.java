package com.quivo.inventory_service.domain;

import com.quivo.inventory_service.ApplicationProperties;
import java.util.Optional;

import com.quivo.inventory_service.domain.model.CreateRoomRequest;
import com.quivo.inventory_service.domain.model.UpdateRoomRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoomService {
    private final RoomRepository roomRepository;
    private final ApplicationProperties applicationProperties;

     RoomService(RoomRepository roomRepository, ApplicationProperties applicationProperties) {
        this.roomRepository = roomRepository;
        this.applicationProperties = applicationProperties;
    }

    public PagedResult<Room> getRooms(int pageNo) {
        Sort sort = Sort.by("name").ascending();
        pageNo = pageNo <= 1 ? 0 : pageNo - 1;
        Pageable pageable = PageRequest.of(pageNo, applicationProperties.pageSize(), sort);
        Page<Room> roomsPage = roomRepository.findAll(pageable).map(RoomMapper::toRoom);

        return new PagedResult<>(
                roomsPage.getContent(),
                roomsPage.getTotalElements(),
                roomsPage.getNumber() + 1,
                roomsPage.getTotalPages(),
                roomsPage.isFirst(),
                roomsPage.isLast(),
                roomsPage.hasNext(),
                roomsPage.hasPrevious());
    }

    public Optional<Room> getRoomByCode(String code) {
        return roomRepository.findByCode(code).map(RoomMapper::toRoom);
    }

    public void createRoom(CreateRoomRequest request) {
        if (roomRepository.findByCode(request.code()).isPresent()) {
            throw new IllegalArgumentException("Room with code already exists: " + request.code());
        }

        RoomEntity room = new RoomEntity();
        room.setCode(request.code());
        room.setName(request.name());
        room.setDescription(request.description());
        room.setImageUrl(request.imageUrl());
        room.setPrice(request.price());
        room.setStatus(request.status() != null ? request.status() : RoomStatus.AVAILABLE);

        roomRepository.save(room);
    }

    public void updateRoom(String code, UpdateRoomRequest request) {
        RoomEntity room = roomRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + code));

        room.setName(request.name());
        room.setDescription(request.description());
        room.setImageUrl(request.imageUrl());
        room.setPrice(request.price());
        room.setStatus(request.status());

        roomRepository.save(room);
    }

    public void deactivateRoom(String code) {
        RoomEntity room = roomRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + code));

        if (room.getStatus() == RoomStatus.INACTIVE) {
            throw new IllegalStateException("Room is already inactive: " + code);
        }

        room.setStatus(RoomStatus.INACTIVE);
        roomRepository.save(room);
    }



}
