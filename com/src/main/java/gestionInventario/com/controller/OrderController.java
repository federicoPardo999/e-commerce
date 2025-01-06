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

    @PostMapping("/create/{idCustomer}")
    public ResponseEntity<?> create(@PathVariable Long idCustomer){
        orderService.createOrder(idCustomer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

