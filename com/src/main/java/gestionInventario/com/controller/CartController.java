package gestionInventario.com.controller;

import gestionInventario.com.model.dto.purchasedProduct.BuyDeleteDTO;
import gestionInventario.com.model.dto.purchasedProduct.CartItemRequestDTO;

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
    ICartService purchaseService;

    @GetMapping("")
    public ResponseEntity<?> getBuyCartResponse() {
        UserEntity user = getUserFromToken();
        return ResponseEntity.ok(purchaseService.getCartFromUser(user.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody CartItemRequestDTO cartItemRequestDTO) {
        UserEntity user = getUserFromToken();
        purchaseService.startPurchase(cartItemRequestDTO, user.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/update-stock")
    public ResponseEntity<?> updateStock(@RequestBody CartItemRequestDTO cartItemRequestDTO) {
        UserEntity user = getUserFromToken();
        purchaseService.updateStock(cartItemRequestDTO, user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/delete")
    public ResponseEntity<?> deleteCart(@RequestBody BuyDeleteDTO buyDeleteDTO) {
        UserEntity user = getUserFromToken();
        buyDeleteDTO.setIdCustomer(user.getId());
        purchaseService.cancelPurchased(buyDeleteDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}