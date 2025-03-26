package gestionInventario.com.service.implementations;

import gestionInventario.com.model.dto.email.SendWelcomeMessageDTO;
import gestionInventario.com.service.interfaces.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class EmailService implements IEmailService {
    JavaMailSender mailSender;

    @Override
    public void sendWelcomeMessage(SendWelcomeMessageDTO sendWelcomeMessageDTO) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(sendWelcomeMessageDTO.getTo());
            helper.setSubject(sendWelcomeMessageDTO.getSubject());
            helper.setText(getContentEmail(sendWelcomeMessageDTO.getUsername()), true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage());
        }

    }

    private String getContentEmail(String userName) {
        return String.format("""
        <!DOCTYPE html>
        <html lang="es">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Mensaje</title>
        </head>
        <body style="font-family: Arial, sans-serif; text-align: center;">
            <h1 style="color: #4CAF50;">¡Bienvenido!</h1>
            <p> %s, Te has registrado con exito en la pagina : )</p>
            <button style="background: #4CAF50; color: white; padding: 10px; border: none; border-radius: 5px;">
                Volver al catálogo
            </button>
        </body>
        </html>
        """, userName);
    }
}
