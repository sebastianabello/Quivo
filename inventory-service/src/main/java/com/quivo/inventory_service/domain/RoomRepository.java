package com.quivo.inventory_service.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    Optional<RoomEntity> findByCode(String name);
}
