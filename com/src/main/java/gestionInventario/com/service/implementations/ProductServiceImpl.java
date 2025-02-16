package gestionInventario.com.service.implementations;

import gestionInventario.com.config.cloudinary.CloudinaryService;
import gestionInventario.com.exception.NotFoundException;
import gestionInventario.com.mapper.product.ProductMapper;
import gestionInventario.com.model.dto.product.ProductRequestDTO;
import gestionInventario.com.model.dto.product.ProductResponseDTO;
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
//
//    @Override
//    @Transactional
//    public void updateStockOfProducts(List<PurchasedProductDTO> purchasedProductsDTO) {
//        purchasedProductsDTO.stream().forEach((productPurchase) -> decreaseStock(
//                productPurchase.getIdProduct(),productPurchase.getStockToBuy()
//        ));
//    }

    private void decreaseStock(Long idProduct, Integer stockToDecrease) {
        Product product = productRepository.findById(idProduct).orElseThrow(
                () -> new NotFoundException("no se encontro el producto"));

        product.decreaseStock(stockToDecrease);
        productRepository.save(product);
    }
}
