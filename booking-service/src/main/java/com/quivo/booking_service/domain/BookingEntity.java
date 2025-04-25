package com.quivo.booking_service.domain;

import com.quivo.booking_service.domain.model.Check;
import com.quivo.booking_service.domain.model.Customer;
import com.quivo.booking_service.domain.model.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name = "reservations")
class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_seq_gen")
    @SequenceGenerator(name = "reservation_seq_gen", sequenceName = "reservation_id_seq")
    private Long id;

    @Column(nullable = false)
    private String reservationNumber;

    @Column(nullable = false)
    private String username;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reservation")
    private Set<BookingItemEntity> reservationItems;

    @Embedded
    @AttributeOverrides(
            value = {
                    @AttributeOverride(name = "name", column = @Column(name = "customer_name")),
                    @AttributeOverride(name = "email", column = @Column(name = "customer_email")),
                    @AttributeOverride(name = "phone", column = @Column(name = "customer_phone")),
            })
    private Customer customer;


    @Embedded
    @AttributeOverrides(
            value = {
                    @AttributeOverride(name = "in_date", column = @Column(name = "check_in_date")),
                    @AttributeOverride(name = "out_date", column = @Column(name = "check_out_date"))
            })
    private Check checkDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String comments;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<BookingItemEntity> getReservationItems() {
        return reservationItems;
    }

    public void setReservationItems(Set<BookingItemEntity> reservationItems) {
        this.reservationItems = reservationItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
