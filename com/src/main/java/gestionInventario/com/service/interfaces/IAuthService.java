package gestionInventario.com.service.interfaces;

import gestionInventario.com.model.dto.auth.LoginRequestDTO;
import gestionInventario.com.model.dto.auth.AuthResponseDTO;
import gestionInventario.com.model.dto.auth.RegisterRequestDTO;

public interface IAuthService {
    AuthResponseDTO login(LoginRequestDTO userDto);
    AuthResponseDTO register(RegisterRequestDTO userToRegisterDto);
}
