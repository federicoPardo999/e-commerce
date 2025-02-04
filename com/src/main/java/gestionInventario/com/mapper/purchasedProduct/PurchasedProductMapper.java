package gestionInventario.com.mapper.purchasedProduct;

import gestionInventario.com.model.dto.purchasedProduct.CartResponseDTO;
import gestionInventario.com.model.entity.Cart;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PurchasedProductMapper {

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
