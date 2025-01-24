package gestionInventario.com.service.implementations;

import gestionInventario.com.exception.NotFoundException;
import gestionInventario.com.exception.StockException;
import gestionInventario.com.model.dto.purchasedProduct.*;
import gestionInventario.com.mapper.purchasedProduct.PurchasedProductMapper;
import gestionInventario.com.model.entity.UserEntity;
import gestionInventario.com.model.entity.PurchasedProduct;
import gestionInventario.com.model.entity.Product;
import gestionInventario.com.model.enumerator.cart.CartStatus;
import gestionInventario.com.repository.IPurchasedProductRepository;
import gestionInventario.com.repository.IUserRepository;
import gestionInventario.com.repository.IProductRepository;
import gestionInventario.com.service.interfaces.IPurchasedProductService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PurchasedProductServiceImpl implements IPurchasedProductService {
    IProductRepository productRepository;
    IUserRepository customerRepository;
    IPurchasedProductRepository purchasedProductRepository;
    PurchasedProductMapper purchasedProductMapper;

    @Override
    public void createCartItem(PurchasedProductRequestDTO cartItemDTO) {

        Product product = productRepository.findById(cartItemDTO.getProductId())
                .orElseThrow( ()-> new NotFoundException("product not founded"));

        UserEntity customer = findCustomer(cartItemDTO.getCustomerId());

        if(stockToBuyIsValid(product.getStock(),cartItemDTO.getQuantityBuyStock())){

            product.decreaseStock(cartItemDTO.getQuantityBuyStock());
            Double itemCartPrice = product.getPrice() * cartItemDTO.getQuantityBuyStock();

            PurchasedProduct purchasedProduct = PurchasedProduct
                    .builder()
                    .product(product)
                    .priceTotal(itemCartPrice)
                    .quantity(cartItemDTO.getQuantityBuyStock())
                    .product(product)
                    .userEntity(customer)
                    .cartStatus(CartStatus.IN_PROGRESS)
                    .build();

            purchasedProductRepository.save(purchasedProduct);

        }else
            throw new StockException("invalid quantity for stock :(");

    }

    @Override
    public List<CartResponseDTO> getAllCarts(Long idCustomer) {
        return purchasedProductMapper.cartsToCartResponseDTO(purchasedProductRepository.findCartsInProgress(idCustomer));
    }

    @Override
    public List<CartResponseDTO> getCartsFinished(Long idCustomer) {
        return purchasedProductMapper.cartsToCartResponseDTO(purchasedProductRepository.findOrders(idCustomer));
    }

    @Override
    public PurchasedProductResponseDTO getBuyCart(Long idCustomer) {
        List<PurchasedProductDTO> products = purchasedProductRepository.findProductsByCustomer(idCustomer, CartStatus.IN_PROGRESS);
        UserEntity customer = findCustomer(idCustomer);
        Double totalSpent = purchasedProductRepository.findTotalSpentOfCartBuy(idCustomer, CartStatus.IN_PROGRESS);

        return PurchasedProductResponseDTO.builder()
                .nameCustomer(customer.getUsername())
                .products(products)
                .totalSpent(totalSpent)
                .build();
    }

    @Override
    public void deleteCart(BuyDeleteDTO buyDeleteDTO) {
        PurchasedProduct purchasedProduct = purchasedProductRepository.findPurchasedProduct(
                buyDeleteDTO.getIdCustomer(),buyDeleteDTO.getIdProduct()
        );
        purchasedProduct.setCartStatus(CartStatus.CANCELED);

        purchasedProductRepository.save(purchasedProduct);
    }

    @Transactional
    @Override
    public void updateStock(PurchasedProductRequestDTO purchasedProductRequestDTO) {
        PurchasedProduct purchasedProduct = purchasedProductRepository.findPurchasedProduct(
            purchasedProductRequestDTO.getCustomerId(),
                purchasedProductRequestDTO.getProductId());

        purchasedProduct.setQuantity(purchasedProductRequestDTO.getQuantityBuyStock());
        Double totalSpent = purchasedProductRepository.findTotalSpentOfIndividualBuy(
                purchasedProductRequestDTO.getCustomerId(),
                purchasedProductRequestDTO.getProductId(),
                CartStatus.IN_PROGRESS);

        purchasedProduct.setPriceTotal(totalSpent);
        purchasedProductRepository.save(purchasedProduct);
    }

    private UserEntity findCustomer(Long idCustomer) {
        return customerRepository.findById(idCustomer)
                .orElseThrow( ()-> new NotFoundException("customer not founded"));
    }

    private boolean stockToBuyIsValid(Integer stockProduct, Integer stockBuy) {
        return stockProduct-stockBuy >= 0;
    }
}