package gestionInventario.com.controller;


import gestionInventario.com.model.dto.cart.CartItemRequestDTO;

import gestionInventario.com.service.interfaces.ICartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class cartController {
    ICartService cartService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CartItemRequestDTO cartItemRequestDTO) {
        cartService.createCartItem(cartItemRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/carts/{idCustomer}")
    public ResponseEntity<?> getCarts(@PathVariable Long idCustomer) {
        return ResponseEntity.ok(cartService.getAllCarts(idCustomer));
    }

    //Este endpoint deberia ir en orderController
    @GetMapping("/carts-finished/{idCustomer}")
    public ResponseEntity<?> getCartsFinished(@PathVariable Long idCustomer) {
        return ResponseEntity.ok(cartService.getCartsFinished(idCustomer));
    }

    @GetMapping("/buy-cart/{idCustomer}")
    public ResponseEntity<?> getBuyCartResponse(@PathVariable Long idCustomer) {
        return ResponseEntity.ok(cartService.getBuyCart(idCustomer));
    }

    @GetMapping("/purchased-history/{idCustomer}")
    public ResponseEntity<?> getPurchasedHistory(@PathVariable Long idCustomer) {
        return ResponseEntity.ok(cartService.getPurchasedHistory(idCustomer));
    }

    @PatchMapping("/delete-cart/{idCustomer}/{idProduct}")
    public ResponseEntity<?> deleteCart(@PathVariable Long idCustomer, @PathVariable Long idProduct) {
        cartService.deleteCart(idCustomer, idProduct);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
