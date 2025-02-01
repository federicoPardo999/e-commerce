package gestionInventario.com.service.interfaces;


import gestionInventario.com.model.dto.product.ProductRequestDTO;
import gestionInventario.com.model.dto.product.ProductResponseDTO;
import gestionInventario.com.model.dto.purchasedProduct.PurchasedProductDTO;
import gestionInventario.com.model.enumerator.product.Category;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IProductService {
     void createProduct(ProductRequestDTO productRequestDTO, MultipartFile image) throws IOException;
     List<ProductResponseDTO> getMostExpensiveProduct();

     List<ProductResponseDTO> getAllProducts();
     List<ProductResponseDTO> getProductsByCategory(String category);

    void updateStockOfProducts(List<PurchasedProductDTO> purchasedProductDTO);
}

