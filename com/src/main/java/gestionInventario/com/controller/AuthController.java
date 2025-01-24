package gestionInventario.com.controller;

import gestionInventario.com.model.dto.auth.LoginRequestDTO;
import gestionInventario.com.model.dto.auth.RegisterRequestDTO;
import gestionInventario.com.service.interfaces.IAuthService;
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
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthController {
    IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity <?> login(@RequestBody LoginRequestDTO loginRequestDTO){
        return  ResponseEntity.ok(authService.login(loginRequestDTO));
    }

    @PostMapping("/register")
    public ResponseEntity <?> login(@RequestBody RegisterRequestDTO registerRequestDTO){
        return new ResponseEntity<>(authService.register(registerRequestDTO),HttpStatus.CREATED);
    }

}
