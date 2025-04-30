package com.quivo.booking_service.jobs;

import com.quivo.booking_service.domain.BookingService;
import java.time.Instant;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BookingProcessingJob {

    private static final Logger log = LoggerFactory.getLogger(BookingProcessingJob.class);

    private final BookingService bookingService;

    BookingProcessingJob(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Scheduled(cron = "*/10 * * * * *")
    @SchedulerLock(name = "processNewBooking")
    public void processNewBooking() {
        LockAssert.assertLocked();
        log.info("Processing new orders at {}", Instant.now());
        bookingService.processNewReservations();
    }
}
