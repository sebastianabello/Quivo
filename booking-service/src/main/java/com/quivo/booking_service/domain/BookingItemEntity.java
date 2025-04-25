package com.quivo.booking_service.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "reservations_items")
class BookingItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_item_id_generator")
    @SequenceGenerator(name = "reservation_item_id_generator", sequenceName = "reservation_item_id_seq")
    private Long id;

    @Column(nullable = false)
    private String code;

    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer guest;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reservation_id")
    private BookingEntity reservation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getGuest() {
        return guest;
    }

    public void setGuest(Integer guest) {
        this.guest = guest;
    }

    public BookingEntity getReservation() {
        return reservation;
    }

    public void setReservation(BookingEntity reservation) {
        this.reservation = reservation;
    }
}
