package gestionInventario.com.service.interfaces;


import gestionInventario.com.model.dto.product.ProductResponseDTO;
import gestionInventario.com.model.entity.Product;

import java.util.List;

public interface IProductService {
     void createProduct(Product product);
     List<ProductResponseDTO> getMostExpensiveProduct();

     List<ProductResponseDTO> getAllProducts();
     List<ProductResponseDTO> getProductsByCategory(String category);
}

