package gestionInventario.com.model.dto.auth;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequestDTO {
    String username;
    String password;
}
