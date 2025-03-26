package gestionInventario.com.service.implementations;

import gestionInventario.com.config.cloudinary.CloudinaryService;
import gestionInventario.com.exception.NotFoundException;
import gestionInventario.com.mapper.product.ProductMapper;
import gestionInventario.com.model.dto.product.ProductRequestDTO;
import gestionInventario.com.model.dto.product.ProductResponseDTO;
import gestionInventario.com.model.entity.CartItem;
import gestionInventario.com.model.entity.Product;
import gestionInventario.com.repository.IProductRepository;
import gestionInventario.com.service.interfaces.IProductService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ProductServiceImpl implements IProductService {
    IProductRepository productRepository;
    ProductMapper productMapper;
    CloudinaryService cloudinaryService;

    @Override
    @Transactional
    public void createProduct(ProductRequestDTO productRequestDTO, MultipartFile image) throws IOException {
            String urlImage = cloudinaryService.uploadImage(image) ;

            Product product = Product.builder()
                    .name(productRequestDTO.getName())
                    .price(productRequestDTO.getPrice())
                    .stock(productRequestDTO.getStock())
                    .description(productRequestDTO.getDescription())
                    .urlImage(urlImage)
                    .build();
            productRepository.save(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productMapper.ProductsToProductsDTO(productRepository.findAll());
    }

    @Override
    public List<ProductResponseDTO> getProductsByCategory(String category) {
        return productMapper.ProductsToProductsDTO(productRepository.findProductsByCategory(category));
    }

    @Override
    public void decreaseStock(Set<CartItem> cartItems) {
        cartItems.forEach(cartItem ->{
                Product productToDeacreseStock = productRepository.findById(cartItem.getProduct().getId()).orElseThrow(
                        () -> new NotFoundException("producto no encontrado")
                );

                productToDeacreseStock.decreaseStock(cartItem.getQuantity());
        });
    }
}