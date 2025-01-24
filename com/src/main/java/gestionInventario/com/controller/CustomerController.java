package gestionInventario.com.controller;

import gestionInventario.com.model.entity.UserEntity;
import gestionInventario.com.service.interfaces.ICustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class CustomerController {
    ICustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserEntity customer) {
        customerService.createCustomer(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
//
//    @GetMapping("/all-customers")
//    public ResponseEntity<List<CustomerResponseDTO>> getAll() {
//        return ResponseEntity.ok(customerService.getAll());
//    }

//    @GetMapping("/most-buys")
//    public ResponseEntity<CustomerResponseDTO> getCustomerWhitMostBuys() {
//        return ResponseEntity.ok(customerService.getCustomerWhitMostBuys());
//    }
}

