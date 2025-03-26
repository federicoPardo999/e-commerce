package gestionInventario.com.controller;

import gestionInventario.com.model.dto.purchasedProduct.CartItemRequestDTO;

import gestionInventario.com.model.dto.purchasedProduct.CartRequestDTO;
import gestionInventario.com.model.entity.UserEntity;
import gestionInventario.com.service.interfaces.ICartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static gestionInventario.com.controller.contextUser.GetUser.getUserFromToken;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CartController {
    ICartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody CartItemRequestDTO cartItemRequestDTO) {
        UserEntity user = getUserFromToken();
        cartService.addItemToCart(cartItemRequestDTO, user.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getCart() {
        UserEntity user = getUserFromToken();
        return ResponseEntity.ok(cartService.getCart(user.getId()));
    }

}