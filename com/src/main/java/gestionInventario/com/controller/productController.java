package gestionInventario.com.controller;

import gestionInventario.com.exception.BadRequestException;
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
        try{
        productService.createProduct(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage()+"url mal escrita");
        }
    }

    @GetMapping("/get-most-expensive")
    public ResponseEntity<List<ProductResponseDTO>> getMostExpensivesProduct() {
        return  ResponseEntity.ok(productService.getMostExpensiveProduct());
    }

    @GetMapping("get-all")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("get-by-category/{category}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(@PathVariable String category){
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }
}
