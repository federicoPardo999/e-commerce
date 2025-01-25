package gestionInventario.com.model.dto.auth;

import gestionInventario.com.model.enumerator.user.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequestDTO {
    String username;
    String password;
    String address;
    String mail;
    Role role;

}
