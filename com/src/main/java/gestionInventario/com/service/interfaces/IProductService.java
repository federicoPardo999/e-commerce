package gestionInventario.com.service.interfaces;


import gestionInventario.com.model.dto.product.ProductResponseDTO;
import gestionInventario.com.model.enumerator.product.Category;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IProductService {
     void createProduct(String name, Double price, Integer stock, String description, Category category, MultipartFile image) throws IOException;
     List<ProductResponseDTO> getMostExpensiveProduct();

     List<ProductResponseDTO> getAllProducts();
     List<ProductResponseDTO> getProductsByCategory(String category);
}

