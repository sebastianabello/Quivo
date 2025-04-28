package com.quivo.booking_service.jobs;

import com.quivo.booking_service.domain.BookingEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class BookingEventsPublishingJob {

    private static final Logger log = LoggerFactory.getLogger(BookingEventsPublishingJob.class);

    private final BookingEventService bookingEventService;

    BookingEventsPublishingJob(BookingEventService bookingEventService) {
        this.bookingEventService = bookingEventService;
    }

    @Scheduled(cron = "*/5 * * * * *")

    public void publishOrderEvents() {
        log.info("Publishing order events at {}", Instant.now());
        bookingEventService.publishBookingEvents();
    }
}
