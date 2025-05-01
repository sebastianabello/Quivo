package com.quivo.notification_service.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingEventRepository extends JpaRepository<BookingEventEntity, Long> {
    boolean existsByEventId(String eventId);
}
