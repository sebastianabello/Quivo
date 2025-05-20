package com.quivo.booking_service.domain;

import com.quivo.booking_service.domain.model.BookingStatus;
import com.quivo.booking_service.domain.model.BookingSummary;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    List<BookingEntity> findByStatus(BookingStatus status);

    Optional<BookingEntity> findByReservationNumber(String reservationNumber);

    @Query(
            "select new com.quivo.booking_service.domain.model.BookingSummary(b.reservationNumber, b.status) from BookingEntity b where b.username = :username")
    List<BookingSummary> findByUsername(String username);

    @Query(
            "select distinct b from BookingEntity b left join fetch b.reservationItems where b.username = :username and b.reservationNumber = :reservationNumber")
    Optional<BookingEntity> findByUsernameAndReservationNumber(String username, String reservationNumber);

    @Query("""
        SELECT b FROM BookingEntity b
        JOIN b.reservationItems i
        WHERE i.code IN :roomCodes
          AND b.status <> 'CANCELLED'
          AND (
            b.checkDate.inDate < :endDate AND
            b.checkDate.outDate > :startDate
          )
    """)
    List<BookingEntity> findConflictingBookings(
            @Param("roomCodes") List<String> roomCodes,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);



    default void updateBookingStatus(String reservationNumber, BookingStatus status) {
        BookingEntity bookingEntity = findByReservationNumber(reservationNumber).orElseThrow();
        bookingEntity.setStatus(status);
        this.save(bookingEntity);
    }
}
