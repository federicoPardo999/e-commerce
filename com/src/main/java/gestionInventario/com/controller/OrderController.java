package gestionInventario.com.controller;

import gestionInventario.com.service.implementations.OrderServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class OrderController {
    OrderServiceImpl orderService;

    //esto deberia ser un request dto o param
    @PostMapping("/create/{idCustomer}")
    public ResponseEntity<?> create(@PathVariable Long idCustomer){
        orderService.createOrder(idCustomer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/purchased-history/{idCustomer}")
    public ResponseEntity<?> getPurchasedHistory(@PathVariable Long idCustomer) {
        return ResponseEntity.ok(orderService.getPurchasedHistory(idCustomer));
    }
}

