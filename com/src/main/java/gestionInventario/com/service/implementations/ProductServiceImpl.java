package gestionInventario.com.service.implementations;

import gestionInventario.com.mapper.product.ProductMapper;
import gestionInventario.com.model.dto.product.ProductResponseDTO;
import gestionInventario.com.model.entity.Product;
import gestionInventario.com.repository.IProductRepository;
import gestionInventario.com.service.interfaces.IProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ProductServiceImpl implements IProductService {
    IProductRepository productRepository;
    ProductMapper productMapper;

    @Override
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<ProductResponseDTO> getMostExpensiveProduct() {
        return productMapper.ProductsToProductsDTO(productRepository.getMostExpensivesProducts());
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productMapper.ProductsToProductsDTO(productRepository.findAll());
    }

    @Override
    public List<ProductResponseDTO> getProductsByCategory(String category) {
        return productMapper.ProductsToProductsDTO(productRepository.findProductsByCategory(category));
    }
}
