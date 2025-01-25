package gestionInventario.com.controller;

import gestionInventario.com.model.dto.purchasedProduct.BuyDeleteDTO;
import gestionInventario.com.model.dto.purchasedProduct.PurchaseRequestDTO;

import gestionInventario.com.model.entity.UserEntity;
import gestionInventario.com.service.interfaces.IPurchaseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static gestionInventario.com.controller.contextUser.GetUser.getUserFromToken;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PurchaseController {
    IPurchaseService purchaseService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        assignIdUser(purchaseRequestDTO);
        purchaseService.startPurchase(purchaseRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get-cart")
    public ResponseEntity<?> getBuyCartResponse() {
        UserEntity user = getUserFromToken();
        return ResponseEntity.ok(purchaseService.getCartFromUser(user.getId()));
    }

    @PatchMapping("/update-stock")
    public ResponseEntity<?> updateStock(@RequestBody PurchaseRequestDTO purchaseRequestDTO){
        assignIdUser(purchaseRequestDTO);
        purchaseService.updateStock(purchaseRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/delete")
    public ResponseEntity<?> deleteCart(@RequestBody BuyDeleteDTO buyDeleteDTO) {
        UserEntity user = getUserFromToken();
        buyDeleteDTO.setIdCustomer(user.getId());
        purchaseService.cancelPurchased(buyDeleteDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void assignIdUser(PurchaseRequestDTO purchaseRequestDTO) {
        UserEntity user = getUserFromToken();
        purchaseRequestDTO.setCustomerId(user.getId());
    }

}