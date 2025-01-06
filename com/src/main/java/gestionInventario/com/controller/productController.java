package gestionInventario.com.controller;

import gestionInventario.com.model.dto.product.ProductResponseDTO;
import gestionInventario.com.model.entity.Product;
import gestionInventario.com.service.interfaces.IProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class productController {
    IProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Product product) {
        productService.createProduct(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get-most-expensives-product")
    public ResponseEntity<List<ProductResponseDTO>> getMostExpensivesProduct() {
        return new ResponseEntity<>(productService.getMostExpensiveProduct(),HttpStatus.OK);

    }
}
