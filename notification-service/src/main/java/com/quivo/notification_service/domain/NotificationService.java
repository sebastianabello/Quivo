package com.quivo.notification_service.domain;

import com.quivo.notification_service.ApplicationProperties;
import com.quivo.notification_service.domain.model.BookingCancelledEvent;
import com.quivo.notification_service.domain.model.BookingCreateEvent;
import com.quivo.notification_service.domain.model.BookingErrorEvent;
import com.quivo.notification_service.domain.model.BookingReservedEvent;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final JavaMailSender javaMailSender;
    private final ApplicationProperties applicationProperties;

    public NotificationService(JavaMailSender javaMailSender, ApplicationProperties applicationProperties) {
        this.javaMailSender = javaMailSender;
        this.applicationProperties = applicationProperties;
    }

    public void sendBookingCreatedNotification(BookingCreateEvent event) {
        String message =
                """
                ===================================================
                Order Created Notification
                ----------------------------------------------------
                Dear %s,
                Your order with orderNumber: %s has been created successfully.

                Thanks,
                BookStore Team
                ===================================================
                """
                        .formatted(event.customer().name(), event.reservationNumber());
        log.info("\n{}", message);
        sendEmail(event.customer().email(), "Order Created Notification", message);
    }

    public void sendBookingReservedNotification(BookingReservedEvent event) {
        String message =
                """
                ===================================================
                Order Delivered Notification
                ----------------------------------------------------
                Dear %s,
                Your order with orderNumber: %s has been delivered successfully.

                Thanks,
                BookStore Team
                ===================================================
                """
                        .formatted(event.customer().name(), event.reservationNumber());
        log.info("\n{}", message);
        sendEmail(event.customer().email(), "Order Delivered Notification", message);
    }

    public void sendBookingCancelledNotification(BookingCancelledEvent event) {
        String message =
                """
                ===================================================
                Order Cancelled Notification
                ----------------------------------------------------
                Dear %s,
                Your order with orderNumber: %s has been cancelled.
                Reason: %s

                Thanks,
                BookStore Team
                ===================================================
                """
                        .formatted(event.customer().name(), event.reservationNumber(), event.reason());
        log.info("\n{}", message);
        sendEmail(event.customer().email(), "Order Cancelled Notification", message);
    }

    public void sendBookingErrorEventNotification(BookingErrorEvent event) {
        String message =
                """
                ===================================================
                Order Processing Failure Notification
                ----------------------------------------------------
                Hi %s,
                The order processing failed for orderNumber: %s.
                Reason: %s

                Thanks,
                BookStore Team
                ===================================================
                """
                        .formatted(applicationProperties.supportEmail(), event.reservationNumber(), event.reason());
        log.info("\n{}", message);
        sendEmail(applicationProperties.supportEmail(), "Order Processing Failure Notification", message);
    }

    private void sendEmail(String recipient, String subject, String content) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(applicationProperties.supportEmail());
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(content);
            javaMailSender.send(mimeMessage);
            log.info("Email sent to: {}", recipient);
        } catch (Exception e) {
            throw new RuntimeException("Error while sending email", e);
        }
    }
}
