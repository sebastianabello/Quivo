package com.quivo.inventory_service.domain;

import org.springframework.data.jpa.repository.JpaRepository;

interface RoomRepository extends JpaRepository<RoomEntity, Long> {}
