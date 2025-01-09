package gestionInventario.com.service.implementations;

import gestionInventario.com.exception.BadRequestException;
import gestionInventario.com.exception.NotFoundException;
import gestionInventario.com.http.BuyCartResponse;
import gestionInventario.com.http.PurchasedProduct;
import gestionInventario.com.mapper.cart.CartMapper;
import gestionInventario.com.model.dto.cart.CartItemRequestDTO;
import gestionInventario.com.model.dto.cart.CartResponseDTO;
import gestionInventario.com.model.entity.Customer;
import gestionInventario.com.model.entity.Cart;
import gestionInventario.com.model.entity.Product;
import gestionInventario.com.model.entity.utils.CustomerProductId;
import gestionInventario.com.model.enumerator.cart.CartStatus;
import gestionInventario.com.repository.ICartRepository;
import gestionInventario.com.repository.ICustomerRepository;
import gestionInventario.com.repository.IProductRepository;
import gestionInventario.com.service.interfaces.ICartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CartServiceImpl implements ICartService {
    IProductRepository productRepository;
    ICustomerRepository customerRepository;
    ICartRepository cartRepository;
    CartMapper cartMapper;

    @Override
    public void createCartItem(CartItemRequestDTO cartItemDTO) {

        Product product = productRepository.findById(cartItemDTO.getProductId())
                .orElseThrow( ()-> new NotFoundException("product not founded"));

        Customer customer = findCustomer(cartItemDTO.getCustomerId());

        Integer stock = product.getStock();
        Integer stockToBuy = cartItemDTO.getQuantityBuyStock();
        Double price = product.getPrice();

        if(sufficientStock(stock,stockToBuy)){

            product.decreaseStock(stockToBuy);
            Double itemCartPrice = price* stockToBuy;

            Cart cart = Cart
                    .builder()
                    .id(new CustomerProductId(product.getId(), customer.getId()))
                    .priceTotal(itemCartPrice)
                    .quantity(stockToBuy)
                    .product(product)
                    .customer(customer)
                    .cartStatus(CartStatus.IN_PROGRESS)
                    .build();

            cartRepository.save(cart);

        }else
            throw new BadRequestException("invalid quantity for stock :(");

    }

    @Override
    public List<CartResponseDTO> getAllCarts(Long idCustomer) {
        return cartMapper.cartsToCartResponseDTO(cartRepository.findCartsInProgress(idCustomer));
    }

    @Override
    public List<CartResponseDTO> getCartsFinished(Long idCustomer) {
        return cartMapper.cartsToCartResponseDTO(cartRepository.getCartsFinished(idCustomer));
    }

    @Override
    public BuyCartResponse getBuyCart(Long idCustomer) {
        List<PurchasedProduct> products = cartRepository.findProductsByCustomer(idCustomer,CartStatus.IN_PROGRESS);

        Customer customer = findCustomer(idCustomer);
        Double totalSpent = cartRepository.findTotalSpentOfCartBuy(idCustomer,CartStatus.IN_PROGRESS);

        return buildCartResponse(customer.getUsername(),products,totalSpent);
    }

    @Override
    public BuyCartResponse getPurchasedHistory(Long idCustomer) {
        List<PurchasedProduct> products = cartRepository.findProductsByCustomer(idCustomer,CartStatus.FINISHED);

        Customer customer = findCustomer(idCustomer);
        Double totalSpent = cartRepository.findTotalSpentOfCartBuy(idCustomer,CartStatus.FINISHED);

        return buildCartResponse(customer.getUsername(),products,totalSpent);
    }

    @Override
    public void deleteCart(Long idCustomer, Long idProduct) {
        cartRepository.deleteCart(idCustomer,idProduct);
    }

    private BuyCartResponse buildCartResponse(String username, List<PurchasedProduct> products,
                                              Double totalSpent ){
        return BuyCartResponse.builder()
                .nameCustomer(username)
                .products(products)
                .totalSpent(totalSpent)
                .build();
    }

    private Customer findCustomer(Long idCustomer) {
        return customerRepository.findById(idCustomer)
                .orElseThrow( ()-> new NotFoundException("customer not founded"));
    }

    private boolean sufficientStock(Integer stockProduct,Integer stockBuy) {
        return stockProduct-stockBuy >= 0;
    }

}