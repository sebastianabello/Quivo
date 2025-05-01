package com.quivo.notification_service.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking_events")
public class BookingEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_event_id_generator")
    @SequenceGenerator(name = "booking_event_id_generator", sequenceName = "booking_event_id_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String eventId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public BookingEventEntity() {}

    public BookingEventEntity(String eventId) {
        this.eventId = eventId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
