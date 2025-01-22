package gestionInventario.com.notification;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String to, String subject) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        String htmlContent = """
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Mensaje</title>
            </head>
            <body style="font-family: Arial, sans-serif; text-align: center;">
                <h1 style="color: #4CAF50;">¡Operación exitosa!</h1>
                <p>El pedido se ha realizado con exito.</p>
                <button style="background: #4CAF50; color: white; padding: 10px; border: none; border-radius: 5px;">
                    Volver al catálogo
                </button>
            </body>
            </html>
            """;
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // El segundo parámetro indica que el contenido es HTML
            mailSender.send(mimeMessage);
        }catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}