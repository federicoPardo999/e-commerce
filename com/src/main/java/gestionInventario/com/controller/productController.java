package gestionInventario.com.controller;

import gestionInventario.com.model.dto.product.ProductRequestDTO;
import gestionInventario.com.model.dto.product.ProductResponseDTO;
import gestionInventario.com.service.interfaces.IProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class productController {
    IProductService productService;

    @PostMapping(value = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(@ModelAttribute ProductRequestDTO productRequestDTO,
            @RequestPart("image")MultipartFile image) {
        try {
            productService.createProduct(productRequestDTO,image);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
//
//    @PatchMapping("/update-stock")
//    public  ResponseEntity<?> updateStock(@RequestBody List<PurchasedProductDTO> purchasedProductsDTO){
//        productService.updateStockOfProducts(purchasedProductsDTO);
//        return  new ResponseEntity<>(HttpStatus.OK);
//    }

    @GetMapping("get-all")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("get-by-category/{category}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

}
