package gestionInventario.com.controller;

import gestionInventario.com.model.dto.email.SendWelcomeMessageDTO;
import gestionInventario.com.service.interfaces.IEmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class EmailController {
    IEmailService emailService;

    @PostMapping("/welcome")
    public ResponseEntity <?> sendWelcomeEmail(@RequestBody SendWelcomeMessageDTO sendWelcomeMessage){
        emailService.sendWelcomeMessage(sendWelcomeMessage);
        return new ResponseEntity<>( HttpStatus.CREATED);
    }

}