package gestionInventario.com.service.implementations;

import gestionInventario.com.exception.NotFoundException;
import gestionInventario.com.exception.StockException;
import gestionInventario.com.model.dto.purchasedProduct.*;
import gestionInventario.com.model.entity.UserEntity;
import gestionInventario.com.model.entity.Cart;
import gestionInventario.com.model.entity.Product;
import gestionInventario.com.model.enumerator.cart.CartStatus;
import gestionInventario.com.repository.IPurchaseRepository;
import gestionInventario.com.repository.IUserRepository;
import gestionInventario.com.repository.IProductRepository;
import gestionInventario.com.service.interfaces.ICartService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartImplService implements ICartService {

    IProductRepository productRepository;
    IUserRepository userRepository;
    IPurchaseRepository purchasedProductRepository;

    @Override
    public void addCartItem(CartItemRequestDTO cartItemRequestDTO, Long customerId) {

        Product product = findProduct(cartItemRequestDTO.getProductId());

        UserEntity customer = findCustomer(customerId);

        validateStock(product.getStock(), cartItemRequestDTO.getQuantityBuyStock());

        Double purchasePrice = product.getPrice() * cartItemRequestDTO.getQuantityBuyStock();

        Cart cart = Cart
                .builder()
                .userEntity(customer)
                .cartStatus(CartStatus.ACTIVE)
                .build();

        purchasedProductRepository.save(cart);
    }

    @Override
    public PurchasedProductResponseDTO getCartFromUser(Long idCustomer) {
        List<PurchasedProductDTO> products = purchasedProductRepository.findProductsByCustomer(idCustomer, CartStatus.IN_PROGRESS);
        Double totalSpent = purchasedProductRepository.findTotalSpentOfCartBuy(idCustomer, CartStatus.IN_PROGRESS);

        return PurchasedProductResponseDTO.builder()
                .products(products)
                .totalSpent(totalSpent)
                .build();
    }

    @Override
    @Transactional
    public void cancelPurchased(BuyDeleteDTO buyDeleteDTO) {
        Cart cart = purchasedProductRepository.findPurchasedProduct(
                buyDeleteDTO.getIdCustomer(), buyDeleteDTO.getIdProduct()
        );
        cart.setCartStatus(CartStatus.CANCELED);

        purchasedProductRepository.save(cart);
    }

    @Transactional
    @Override
    public void updateStock(CartItemRequestDTO cartItemRequestDTO, Long customerId) {
        Cart purchaseProduct = purchasedProductRepository.findPurchasedProduct(
                customerId,
                cartItemRequestDTO.getProductId());

        purchaseProduct.setQuantity(cartItemRequestDTO.getQuantityBuyStock());

        updatePricePurchase(purchaseProduct, cartItemRequestDTO,customerId);

    }

    private void updatePricePurchase(Cart purchase, CartItemRequestDTO cartItemRequestDTO
    , Long customerId) {

        Double purchasePrice = purchasedProductRepository.findTotalSpentOfIndividualBuy(
                customerId,
                cartItemRequestDTO.getProductId(),
                CartStatus.IN_PROGRESS);

        purchase.setPriceTotal(purchasePrice);
        purchasedProductRepository.save(purchase);
    }

    private UserEntity findCustomer(Long idCustomer) {
        return userRepository.findById(idCustomer)
                .orElseThrow(() -> new NotFoundException("EL EL ERROR ES EN EL SERVICE " +
                        " PURCHASE EN EL METODO : 'findCustomer'"));
    }

    private Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("product not founded"));
    }

    private void validateStock(Integer stock, Integer quantityBuyStock) {
        if (stock - quantityBuyStock < 0)
            throw new StockException("quantity to be purchased cannot exceed the available stock");
    }

}