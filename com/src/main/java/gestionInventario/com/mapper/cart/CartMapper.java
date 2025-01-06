package gestionInventario.com.mapper.cart;

import gestionInventario.com.model.dto.cart.CartResponseDTO;
import gestionInventario.com.model.entity.Cart;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartMapper {

    public CartResponseDTO cartToCartResponseDTO(Cart cart){
        return CartResponseDTO
                .builder()
                .nameProduct(cart.getProduct().getName())
                .quantity(cart.getQuantity())
                .priceTotal(cart.getPriceTotal())
                .build();
    }

    public List<CartResponseDTO> cartsToCartResponseDTO(List<Cart> carts){
        return carts.stream().map(this::cartToCartResponseDTO).toList();
    }
}
