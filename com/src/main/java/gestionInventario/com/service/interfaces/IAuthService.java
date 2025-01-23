package gestionInventario.com.service.interfaces;

import gestionInventario.com.model.dto.auth.LoginRequestDTO;
import gestionInventario.com.model.dto.auth.LoginResponseDTO;
import gestionInventario.com.model.dto.auth.RegisterRequestDTO;

public interface IAuthService {
    LoginResponseDTO login(LoginRequestDTO userDto);
    LoginResponseDTO register(RegisterRequestDTO userToRegisterDto);
}
