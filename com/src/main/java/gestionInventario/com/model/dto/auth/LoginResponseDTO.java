package gestionInventario.com.model.dto.auth;

import gestionInventario.com.model.enumerator.user.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String username;
    private String token;
    private Role role;
}
