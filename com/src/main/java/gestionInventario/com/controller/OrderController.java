package gestionInventario.com.controller;

import gestionInventario.com.model.entity.UserEntity;
import gestionInventario.com.service.implementations.OrderServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static gestionInventario.com.controller.contextUser.GetUser.getUserFromToken;

@RestController
@RequestMapping("/order")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class OrderController {
    OrderServiceImpl orderService;

    @PostMapping("/create")
    public ResponseEntity<?> create(){
        UserEntity user = getUserFromToken();
        orderService.createOrder(user.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get-orders")
    public ResponseEntity<?> getPurchasedHistory() {
        UserEntity user = getUserFromToken();
        return ResponseEntity.ok(orderService.getOrderHistory(user.getId()));
    }

}

