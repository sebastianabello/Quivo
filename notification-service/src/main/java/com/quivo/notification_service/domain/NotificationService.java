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
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <title>Procesando reserva</title>
            </head>
            <body style="font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; color: #333;">
                <table width="100%%" cellpadding="0" cellspacing="0" style="max-width: 600px; margin: auto; background-color: #ffffff; border: 1px solid #e0e0e0; border-radius: 8px;">
                    <tr>
                        <td style="padding: 20px; text-align: center; background-color: #4CAF50; color: white; border-top-left-radius: 8px; border-top-right-radius: 8px;">
                            <h2>Procesando reserva</h2>
                        </td>
                    </tr>
                    <tr>
                        <td style="padding: 20px;">
                            <p>Estimad@ <strong>%s</strong>,</p>
                            <p>Nos complace hacerle saber que su reserva esta siendo procesada.</p>
                            <p><strong>Numero de reserva:</strong> %s</p>
                            <p>Si tiene alguna pregunta o inquietud, no dude en responder a este correo electrónico.</p>
                            <p>Gracias por elegirnos<strong>BookStore</strong>!</p>
                            <br>
                            <p style="font-size: 14px; color: #777;">— Equivo Quivo</p>
                        </td>
                    </tr>
                </table>
            </body>
            </html>
            """
                        .formatted(event.customer().name(), event.reservationNumber());
        log.info("\n{}", message);
        sendEmail(event.customer().email(), "Reserva Creada", message);
    }

    public void sendBookingReservedNotification(BookingReservedEvent event) {
        String message =
                """
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <title>Reserva confirmada</title>
            </head>
            <body style="font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; color: #333;">
                <table width="100%%" cellpadding="0" cellspacing="0" style="max-width: 600px; margin: auto; background-color: #ffffff; border: 1px solid #e0e0e0; border-radius: 8px;">
                    <tr>
                        <td style="padding: 20px; text-align: center; background-color: #4CAF50; color: white; border-top-left-radius: 8px; border-top-right-radius: 8px;">
                            <h2>Reserva confirmada</h2>
                        </td>
                    </tr>
                    <tr>
                        <td style="padding: 20px;">
                            <p>Estimad@ <strong>%s</strong>,</p>
                            <p>Nos complace hacerle saber que su reserva ha sido creada con éxito.</p>
                            <p><strong>Numero de reserva:</strong> %s</p>
                            <p>Si tiene alguna pregunta o inquietud, no dude en responder a este correo electrónico.</p>
                            <p>Gracias por elegirnos<strong>BookStore</strong>!</p>
                            <br>
                            <p style="font-size: 14px; color: #777;">— Equivo Quivo</p>
                        </td>
                    </tr>
                </table>
            </body>
            </html>
            """
                        .formatted(event.customer().name(), event.reservationNumber());
        log.info("\n{}", message);
        sendEmail(event.customer().email(), "Reserva Confirmada", message);
    }

    public void sendBookingCancelledNotification(BookingCancelledEvent event) {
        String message =
                """
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <title>Reserva cancelada</title>
            </head>
            <body style="font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; color: #333;">
                <table width="100%%" cellpadding="0" cellspacing="0" style="max-width: 600px; margin: auto; background-color: #ffffff; border: 1px solid #e0e0e0; border-radius: 8px;">
                    <tr>
                        <td style="padding: 20px; text-align: center; background-color: #4CAF50; color: white; border-top-left-radius: 8px; border-top-right-radius: 8px;">
                            <h2>Reserva cancelada</h2>
                        </td>
                    </tr>
                    <tr>
                        <td style="padding: 20px;">
                            <p>Estimad@ <strong>%s</strong>,</p>
                            <p>Lamentamos informarle que su reserva ha sido cancelada.</p>
                            <p><strong>Numero de reserva:</strong> %s</p>
                            <p><strong>Motivo:</strong> %s</p>
                            <p>Si tiene alguna pregunta o inquietud, no dude en responder a este correo electrónico.</p>
                            <p>Gracias por elegirnos<strong>BookStore</strong>!</p>
                            <br>
                            <p style="font-size: 14px; color: #777;">- Equivo Quivo</p>
                        </td>
                    </tr>
                </table>
            </body>
            </html>
            """
                        .formatted(event.customer().name(), event.reservationNumber(), event.reason());
        log.info("\n{}", message);
        sendEmail(event.customer().email(), "Reserva Cancelada", message);
    }

    public void sendBookingErrorEventNotification(BookingErrorEvent event) {
        String message =
                """
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <title>Reserva fallida</title>
            </head>
            <body style="font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; color: #333;">
                <table width="100%%" cellpadding="0" cellspacing="0" style="max-width: 600px; margin: auto; background-color: #ffffff; border: 1px solid #e0e0e0; border-radius: 8px;">
                    <tr>
                        <td style="padding: 20px; text-align: center; background-color: #4CAF50; color: white; border-top-left-radius: 8px; border-top-right-radius: 8px;">
                            <h2>Reserva fallida</h2>
                        </td>
                    </tr>
                    <tr>
                        <td style="padding: 20px;">
                            <p>Estimad@ <strong>%s</strong>,</p>
                            <p>Lamentamos informarle que su reserva ha sido fallida, vuelva a intentarlo por favor.</p>
                            <p><strong>Numero de reserva:</strong> %s</p>
                            <p><strong>Motivo:</strong> %s</p>
                            <p>Si tiene alguna pregunta o inquietud, no dude en responder a este correo electrónico.</p>
                            <p>Gracias por elegirnos<strong>BookStore</strong>!</p>
                            <br>
                            <p style="font-size: 14px; color: #777;">- Equivo Quivo</p>
                        </td>
                    </tr>
                </table>
            </body>
            </html>
            """
                        .formatted(event.customer().name(), event.reservationNumber(), event.reason());
        log.info("\n{}", message);
        sendEmail(applicationProperties.supportEmail(), "Reserva Fallida", message);
    }

    private void sendEmail(String recipient, String subject, String content) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(applicationProperties.supportEmail());
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(content, true);
            javaMailSender.send(mimeMessage);
            log.info("Email sent to: {}", recipient);
        } catch (Exception e) {
            throw new RuntimeException("Error while sending email", e);
        }
    }
}
