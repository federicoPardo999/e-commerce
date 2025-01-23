package gestionInventario.com.service.implementations;

import gestionInventario.com.config.jwt.JwtService;
import gestionInventario.com.exception.NotFoundException;
import gestionInventario.com.exception.RegisterException;
import gestionInventario.com.model.dto.auth.LoginRequestDTO;
import gestionInventario.com.model.dto.auth.LoginResponseDTO;
import gestionInventario.com.model.dto.auth.RegisterRequestDTO;
import gestionInventario.com.model.entity.UserEntity;
import gestionInventario.com.repository.IUserRepository;
import gestionInventario.com.service.interfaces.IAuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthImplService implements IAuthService {
    IUserRepository userRepository;
    JwtService jwtService;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;

    @Override
    public LoginResponseDTO login(LoginRequestDTO userDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(),
                userDto.getPassword()));

        UserEntity user = userRepository.findByUsername(userDto.getUsername()).orElseThrow(
                () -> new NotFoundException("user with the name: "+userDto.getUsername()+ " not exists")
        );

        return LoginResponseDTO.builder()
                .username(userDto.getUsername())
                .token(jwtService.getToken(user))
                .build();
    }

    @Override
    public LoginResponseDTO register(RegisterRequestDTO userToRegisterDto) {
        if(userRepository.existsByUsername(userToRegisterDto.getUsername())){
            throw new RegisterException("ya existe el usuario: "+userToRegisterDto.getUsername());
        }

        UserEntity user = UserEntity.builder()
                .username(userToRegisterDto.getUsername())
                .password(passwordEncoder.encode(userToRegisterDto.getPassword()))
                .mail(userToRegisterDto.getMail())
                .address(userToRegisterDto.getAddress())
                .role(userToRegisterDto.getRole())
                .build();

        userRepository.save(user);

        return LoginResponseDTO.builder()
                .username(userToRegisterDto.getUsername())
                .token(jwtService.getToken(user))
                .role(userToRegisterDto.getRole())
                .build();
    }
}
