package gestionInventario.com.controller;

import gestionInventario.com.http.BuyCartResponse;
import gestionInventario.com.model.dto.cart.CartItemRequestDTO;
import gestionInventario.com.model.dto.cart.CartResponseDTO;
import gestionInventario.com.service.interfaces.ICartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class cartController {

    ICartService cartService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CartItemRequestDTO  cartItemRequestDTO){
        cartService.createCartItem(cartItemRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getCarts/{idCustomer}")
    public ResponseEntity<List<CartResponseDTO>> getCarts(@PathVariable Long idCustomer){
        return new ResponseEntity<>(cartService.getAllCarts(idCustomer),HttpStatus.OK);
    }

    @GetMapping("/getCartsFinished/{idCustomer}")
    public ResponseEntity<List<CartResponseDTO>> getCartsFinished(@PathVariable Long idCustomer){
        return new ResponseEntity<>(cartService.getCartsFinished(idCustomer),HttpStatus.OK);
    }

    @GetMapping("/getBuyCart/{idCustomer}")
    public ResponseEntity<BuyCartResponse> getBuyCartResponse(@PathVariable Long idCustomer){
        return new ResponseEntity<>(cartService.getBuyCart(idCustomer),HttpStatus.OK);
    }

    @GetMapping("/getPurchasedHistory/{idCustomer}")
    public ResponseEntity<BuyCartResponse> getPurchasedHistory(@PathVariable Long idCustomer){
        return new ResponseEntity<>(cartService.getPurchasedHistory(idCustomer),HttpStatus.OK);
    }

}