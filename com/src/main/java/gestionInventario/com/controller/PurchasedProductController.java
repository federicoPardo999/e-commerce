package gestionInventario.com.controller;

import gestionInventario.com.model.dto.purchasedProduct.PurchasedProductRequestDTO;

import gestionInventario.com.service.interfaces.IPurchasedProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buy")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PurchasedProductController {
    IPurchasedProductService purchasedProductService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PurchasedProductRequestDTO purchasedProductRequestDTO) {
        purchasedProductService.createCartItem(purchasedProductRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/carts/{idCustomer}")
    public ResponseEntity<?> getCarts(@PathVariable Long idCustomer) {
        return ResponseEntity.ok(purchasedProductService.getAllCarts(idCustomer));
    }

    //Este endpoint deberia ir en orderController
    @GetMapping("/carts-finished/{idCustomer}")
    public ResponseEntity<?> getCartsFinished(@PathVariable Long idCustomer) {
        return ResponseEntity.ok(purchasedProductService.getCartsFinished(idCustomer));
    }

    @GetMapping("/get-cart/{idCustomer}")
    public ResponseEntity<?> getBuyCartResponse(@PathVariable Long idCustomer) {
        return ResponseEntity.ok(purchasedProductService.getBuyCart(idCustomer));
    }

    @PatchMapping("/update-stock")
    public ResponseEntity<?> updateStock(@RequestBody  PurchasedProductRequestDTO purchasedProductRequestDTO){
        purchasedProductService.updateStock(purchasedProductRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/delete-cart/{idCustomer}/{idProduct}")
    public ResponseEntity<?> deleteCart(@PathVariable Long idCustomer, @PathVariable Long idProduct) {
        purchasedProductService.deleteCart(idCustomer, idProduct);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
