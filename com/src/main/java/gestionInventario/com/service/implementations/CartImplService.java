package gestionInventario.com.service.implementations;

import gestionInventario.com.exception.NotFoundException;
import gestionInventario.com.exception.StockException;
import gestionInventario.com.mapper.mapperItemCart.itemCartMapper;
import gestionInventario.com.model.dto.purchasedProduct.*;
import gestionInventario.com.model.entity.Product;
import gestionInventario.com.model.entity.UserEntity;
import gestionInventario.com.model.entity.Cart;
import gestionInventario.com.model.enumerator.cart.CartStatus;
import gestionInventario.com.repository.ICartRepository;
import gestionInventario.com.repository.IProductRepository;
import gestionInventario.com.repository.IUserRepository;
import gestionInventario.com.service.interfaces.ICartItemService;
import gestionInventario.com.service.interfaces.ICartService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CartImplService implements ICartService{
    ICartRepository cartRepository;
    ICartItemService cartItemService;
    IUserRepository userRepository;
    IProductRepository productRepository;
    itemCartMapper cartMapper;

    @Override
    @Transactional
    public void  addItemToCart(CartItemRequestDTO cartItemRequestDTO,Long customerId) {
        Cart cart = cartRepository.findActiveCartByCustomerId(customerId)
                .orElseGet(() -> createNewCart(customerId));


        Product product = findProduct(cartItemRequestDTO.getProductId());

        validateStock(product.getStock(),cartItemRequestDTO.getQuantityBuyStock());

        cartItemService.addItem(cartItemRequestDTO, cart.getId());

        cartRepository.save(cart);
    }

    @Override
    public CartResponseDTO getCart(CartRequestDTO cartRequestDTO) {
        Cart cart = cartRepository.findById(cartRequestDTO.getIdCart()).orElseThrow();

        List <CartItemResponseDTO> items = cartMapper.itemsToCartItemResponseDTO(
                cartRepository.findItems(cartRequestDTO.getIdCart()));

        return CartResponseDTO.builder()
                .items(items)
                .priceCart(cart.getCartPrice())
                .build();
    }

    private Cart createNewCart(Long customerId) {
        Cart cart = Cart.builder()
                .user(findCustomer(customerId))
                .cartStatus(CartStatus.ACTIVE)
                .build();
        return cartRepository.save(cart);
    }

    private UserEntity findCustomer(Long id){
        return userRepository.findById(id).orElseThrow(()
        -> new NotFoundException("User with the id: "+id+" not founded"));
    }

    private Product findProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new NotFoundException("product not founded")
        );
    }

    private void validateStock(Integer stock, Integer quantityBuyStock) {
        if(stock - quantityBuyStock < 0)
            throw new StockException("la cantidad a comprar tiene que estar disponible");
    }
}