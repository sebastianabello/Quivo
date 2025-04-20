package com.quivo.inventory_service.domain;

import com.quivo.inventory_service.ApplicationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoomService  {
    private final RoomRepository roomRepository;
    private final ApplicationProperties applicationProperties;

    public RoomService(RoomRepository roomRepository, ApplicationProperties applicationProperties) {
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
                roomsPage.hasPrevious()
        );

    }
}
