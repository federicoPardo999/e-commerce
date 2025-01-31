package gestionInventario.com.controller;

import gestionInventario.com.model.dto.product.ProductResponseDTO;
import gestionInventario.com.model.dto.purchasedProduct.PurchasedProductDTO;
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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class productController {
    IProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam("name") String name,
                                    @RequestParam("price") Double price,
                                    @RequestParam("stock") Integer stock,
                                    @RequestParam("description") String description
                                    /*@RequestParam("category") Category category*/) {
        try {
            // Llamamos al servicio con los parámetros recibidos
            productService.createProduct(name, price, stock, description);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update-stock")
    public  ResponseEntity<?> updateStock(@RequestBody List<PurchasedProductDTO> purchasedProductsDTO){
        productService.updateStockOfProducts(purchasedProductsDTO);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("get-all")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("get-by-category/{category}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

}
