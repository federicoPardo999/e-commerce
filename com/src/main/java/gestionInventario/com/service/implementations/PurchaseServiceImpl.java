package gestionInventario.com.service.implementations;

import gestionInventario.com.exception.NotFoundException;
import gestionInventario.com.exception.StockException;
import gestionInventario.com.model.dto.purchasedProduct.*;
import gestionInventario.com.model.entity.UserEntity;
import gestionInventario.com.model.entity.PurchasedProduct;
import gestionInventario.com.model.entity.Product;
import gestionInventario.com.model.enumerator.cart.purchaseStatus;
import gestionInventario.com.repository.IPurchaseRepository;
import gestionInventario.com.repository.IUserRepository;
import gestionInventario.com.repository.IProductRepository;
import gestionInventario.com.service.interfaces.IPurchaseService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PurchaseServiceImpl implements IPurchaseService {

    IProductRepository productRepository;
    IUserRepository userRepository;
    IPurchaseRepository purchasedProductRepository;

    @Override
    public void startPurchase(PurchaseRequestDTO cartItemDTO) {

        Product product = findProduct(cartItemDTO.getProductId());

        UserEntity customer = findCustomer(cartItemDTO.getCustomerId());

        validateStock(product.getStock(), cartItemDTO.getQuantityBuyStock());

        Double purchasePrice = product.getPrice() * cartItemDTO.getQuantityBuyStock();

        PurchasedProduct purchasedProduct = PurchasedProduct
                .builder()
                .product(product)
                .priceTotal(purchasePrice)
                .quantity(cartItemDTO.getQuantityBuyStock())
                .product(product)
                .userEntity(customer)
                .purchaseStatus(purchaseStatus.IN_PROGRESS)
                .build();

        purchasedProductRepository.save(purchasedProduct);
    }

    private void validateStock(Integer stock, Integer quantityBuyStock) {
        if (stock - quantityBuyStock < 0)
            throw new StockException("quantity to be purchased cannot exceed the available stock");
    }

    public PurchasedProductResponseDTO getCartFromUser(Long idCustomer) {
        List<PurchasedProductDTO> products = purchasedProductRepository.findProductsByCustomer(idCustomer, purchaseStatus.IN_PROGRESS);
        UserEntity customer = findCustomer(idCustomer);
        Double totalSpent = purchasedProductRepository.findTotalSpentOfCartBuy(idCustomer, purchaseStatus.IN_PROGRESS);

        return PurchasedProductResponseDTO.builder()
                .nameCustomer(customer.getUsername())
                .products(products)
                .totalSpent(totalSpent)
                .build();
    }

    public void cancelPurchased(BuyDeleteDTO buyDeleteDTO) {
        PurchasedProduct purchasedProduct = purchasedProductRepository.findPurchasedProduct(
                buyDeleteDTO.getIdCustomer(), buyDeleteDTO.getIdProduct()
        );
        purchasedProduct.setPurchaseStatus(purchaseStatus.CANCELED);

        purchasedProductRepository.save(purchasedProduct);
    }

    @Transactional
    @Override
    public void updateStock(PurchaseRequestDTO purchaseRequestDTO) {
        PurchasedProduct purchaseProduct = purchasedProductRepository.findPurchasedProduct(
                purchaseRequestDTO.getCustomerId(),
                purchaseRequestDTO.getProductId());

        purchaseProduct.setQuantity(purchaseRequestDTO.getQuantityBuyStock());

        updatePricePurchase(purchaseProduct, purchaseRequestDTO);

    }

    private void updatePricePurchase(PurchasedProduct purchase, PurchaseRequestDTO purchaseRequestDTO) {

        Double purchasePrice = purchasedProductRepository.findTotalSpentOfIndividualBuy(
                purchaseRequestDTO.getCustomerId(),
                purchaseRequestDTO.getProductId(),
                purchaseStatus.IN_PROGRESS);

        purchase.setPriceTotal(purchasePrice);
        purchasedProductRepository.save(purchase);
    }

    private Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("product not founded"));
    }

    private UserEntity findCustomer(Long idCustomer) {
        return userRepository.findById(idCustomer)
                .orElseThrow(() -> new NotFoundException("customer not founded"));
    }

}