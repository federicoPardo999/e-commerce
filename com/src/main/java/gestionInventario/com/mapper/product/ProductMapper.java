package gestionInventario.com.mapper.product;

import gestionInventario.com.model.dto.product.ProductResponseDTO;
import gestionInventario.com.model.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public ProductResponseDTO productToProductResponseDTO(Product product){
        return ProductResponseDTO
                .builder()
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

    public List<ProductResponseDTO> ProductsToProductsDTO(List<Product> products){
        return products.stream().map(this::productToProductResponseDTO).toList();
    }
}
