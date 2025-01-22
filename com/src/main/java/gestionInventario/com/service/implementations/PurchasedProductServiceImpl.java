package gestionInventario.com.service.implementations;

import gestionInventario.com.exception.NotFoundException;
import gestionInventario.com.exception.StockException;
import gestionInventario.com.model.dto.purchasedProduct.PurchasedProductResponseDTO;
import gestionInventario.com.model.dto.purchasedProduct.PurchasedProductDTO;
import gestionInventario.com.mapper.purchasedProduct.PurchasedProductMapper;
import gestionInventario.com.model.dto.purchasedProduct.PurchasedProductRequestDTO;
import gestionInventario.com.model.dto.purchasedProduct.CartResponseDTO;
import gestionInventario.com.model.entity.Customer;
import gestionInventario.com.model.entity.PurchasedProduct;
import gestionInventario.com.model.entity.Product;
import gestionInventario.com.model.enumerator.cart.CartStatus;
import gestionInventario.com.repository.IPurchasedProductRepository;
import gestionInventario.com.repository.ICustomerRepository;
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
    ICustomerRepository customerRepository;
    IPurchasedProductRepository purchasedProductRepository;
    PurchasedProductMapper purchasedProductMapper;

    @Override
    public void createCartItem(PurchasedProductRequestDTO cartItemDTO) {

        Product product = productRepository.findById(cartItemDTO.getProductId())
                .orElseThrow( ()-> new NotFoundException("product not founded"));

        Customer customer = findCustomer(cartItemDTO.getCustomerId());
        Integer stock = product.getStock();
        Integer stockToBuy = cartItemDTO.getQuantityBuyStock();
        Double price = product.getPrice();

        if(stockToBuyIsValid(stock,stockToBuy)){

            product.decreaseStock(stockToBuy);
            Double itemCartPrice = price* stockToBuy;

            PurchasedProduct purchasedProduct = PurchasedProduct
                    .builder()
                    .product(product)
                    .customer(customer)
                    .priceTotal(itemCartPrice)
                    .quantity(stockToBuy)
                    .product(product)
                    .customer(customer)
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
        return purchasedProductMapper.cartsToCartResponseDTO(purchasedProductRepository.getCartsFinished(idCustomer));
    }

    @Override
    public PurchasedProductResponseDTO getBuyCart(Long idCustomer) {
        List<PurchasedProductDTO> products = purchasedProductRepository.findProductsByCustomer(idCustomer, CartStatus.IN_PROGRESS);
        Customer customer = findCustomer(idCustomer);
        Double totalSpent = purchasedProductRepository.findTotalSpentOfCartBuy(idCustomer, CartStatus.IN_PROGRESS);

        return PurchasedProductResponseDTO.builder()
                .nameCustomer(customer.getUsername())
                .products(products)
                .totalSpent(totalSpent)
                .build();
    }

    @Override
    public void deleteCart(Long idCustomer, Long idProduct) {
        purchasedProductRepository.deleteCart(idCustomer,idProduct);
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

    private Customer findCustomer(Long idCustomer) {
        return customerRepository.findById(idCustomer)
                .orElseThrow( ()-> new NotFoundException("customer not founded"));
    }

    private boolean stockToBuyIsValid(Integer stockProduct, Integer stockBuy) {
        return stockProduct-stockBuy >= 0;
    }
}