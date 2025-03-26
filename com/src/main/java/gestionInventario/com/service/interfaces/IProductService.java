package gestionInventario.com.service.interfaces;


import gestionInventario.com.model.dto.product.ProductRequestDTO;
import gestionInventario.com.model.dto.product.ProductResponseDTO;
import gestionInventario.com.model.entity.CartItem;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IProductService {
     void createProduct(ProductRequestDTO productRequestDTO, MultipartFile image) throws IOException;
     List<ProductResponseDTO> getAllProducts();
     List<ProductResponseDTO> getProductsByCategory(String category);
     void decreaseStock(Set<CartItem> cartItems);
}

