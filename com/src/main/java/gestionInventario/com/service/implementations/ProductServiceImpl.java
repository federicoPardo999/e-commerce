package gestionInventario.com.service.implementations;

import gestionInventario.com.mapper.product.ProductMapper;
import gestionInventario.com.model.dto.product.ProductResponseDTO;
import gestionInventario.com.model.entity.Product;
import gestionInventario.com.model.enumerator.product.Category;
import gestionInventario.com.repository.IProductRepository;
import gestionInventario.com.service.interfaces.IProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ProductServiceImpl implements IProductService {
    IProductRepository productRepository;
    ProductMapper productMapper;

    @Override
    public void createProduct(String name, Double price, Integer stock, String description, Category category, MultipartFile image) throws IOException {
        // Guardamos la imagen en un directorio local
        String imagePath = "C:/Users/msi" +
                "/Downloads/Proyecto-e-commerce" +
                "/Back-end/gestionInventario" +
                "/com/src/main/java/gestionInventario/com/images" + image.getOriginalFilename();

        image.transferTo(new File(imagePath));  // Guardamos el archivo en el directorio

        // Creamos el producto con la imagen guardada
        Product product = Product.builder()
                .name(name)
                .price(price)
                .stock(stock)
                .description(description)
                .category(category)  // Usamos String o Enum según lo necesites
                .urlImage(imagePath)  // Almacenamos la ruta de la imagen
                .build();

        // Guardamos el producto en la base de datos
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
