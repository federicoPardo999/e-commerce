package gestionInventario.com.model.dto.email;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendWelcomeMessageDTO {
    String to;
    String subject;
    String username;
}