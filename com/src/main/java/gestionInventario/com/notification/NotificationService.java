package gestionInventario.com.notification;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class NotificationService {
    JavaMailSender mailSender;

    public void sendWelcomeEmail(String to, String subject, String userName) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(getContentEmail(userName), true);
            mailSender.send(mimeMessage);
        }catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    private String getContentEmail(String userName){
        return
            """
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Mensaje</title>
            </head>
            <body style="font-family: Arial, sans-serif; text-align: center;">
                <h1 style="color: #4CAF50;">¡Operación exitosa! /h1>"""+
                """
                <p>"""+userName+"""
                El pedido se ha realizado con exito.</p>
                <button style="background: #4CAF50; color: white; padding: 10px; border: none; border-radius: 5px;">
                    Volver al catálogo
                </button>
            </body>
            </html>
            """;
    }
}