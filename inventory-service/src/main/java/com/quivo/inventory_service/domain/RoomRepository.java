package com.quivo.inventory_service.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    Optional<RoomEntity> findByCode(String name);
}
