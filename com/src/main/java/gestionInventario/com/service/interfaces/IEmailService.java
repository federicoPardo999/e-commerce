package gestionInventario.com.service.interfaces;

import gestionInventario.com.model.dto.email.SendWelcomeMessageDTO;

public interface IEmailService {
    void sendWelcomeMessage(SendWelcomeMessageDTO sendWelcomeMessage);
}
