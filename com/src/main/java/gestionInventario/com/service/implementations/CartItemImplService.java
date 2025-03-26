package gestionInventario.com.service.implementations;

import gestionInventario.com.exception.NotFoundException;
import gestionInventario.com.model.dto.purchasedProduct.CartItemRequestDTO;
import gestionInventario.com.model.dto.purchasedProduct.UpdateItemRequestDTO;
import gestionInventario.com.model.entity.Cart;
import gestionInventario.com.model.entity.CartItem;
import gestionInventario.com.model.entity.Product;
import gestionInventario.com.repository.ICartItemRepository;
import gestionInventario.com.repository.ICartRepository;
import gestionInventario.com.repository.IProductRepository;
import gestionInventario.com.service.interfaces.ICartItemService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CartItemImplService implements ICartItemService {

    ICartItemRepository cartItemRepository;
    IProductRepository productRepository;
    ICartRepository cartRepository;

    @Override
    public void addItem(CartItemRequestDTO cartItemRequestDTO, Long cartId) {
        Cart cart = findCart(cartId);
        Product product = findProduct(cartItemRequestDTO.getProductId());

        Integer quantity = cartItemRequestDTO.getQuantityBuyStock();
        Double priceItem = quantity * product.getPrice();

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .productName(product.getName())
                .priceProduct(product.getPrice())
                .image(product.getUrlImage())
                .priceItem(priceItem)
                .build();

        cartItemRepository.save(cartItem);

        updatePriceOfCart(cart);
    }

    @Override
    @Transactional
    public void updateStockItem(UpdateItemRequestDTO updateItemRequestDTO, Long customerId) {
        CartItem item = findItem(updateItemRequestDTO.getItemId());

        Product product = findProduct(updateItemRequestDTO.getProductId());

        Cart cart = findCart(updateItemRequestDTO.getCartId());

        Integer quantity =  updateItemRequestDTO.getQuantityBuyStock();

        item.setQuantity(quantity);

        item.setPriceItem(quantity * product.getPrice());

        updatePriceOfCart(cart);

        cartItemRepository.save(item);

    }

    @Override
    public void updatePriceOfCart(Cart cart) {
        Double total = cartItemRepository.calcAmountCart(cart.getId());
        cart.setCartPrice(total);
    }

    @Override
    @Transactional
    public void deleteItem(Long cartItemID) {
        CartItem cartItem = findItem(cartItemID);
        cartItemRepository.delete(cartItem);
    }

    private Product findProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new NotFoundException("product not founded")
        );
    }

    private Cart findCart(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(
                () -> new NotFoundException("cart not founded")
        );
    }

    private CartItem findItem(Long itemId) {
        return cartItemRepository.findById(itemId).orElseThrow(
                () -> new NotFoundException("cart not founded")
        );
    }
}