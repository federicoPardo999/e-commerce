package gestionInventario.com.mapper.mapperItemCart;

import gestionInventario.com.model.dto.purchasedProduct.CartItemResponseDTO;
import gestionInventario.com.model.entity.CartItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class itemCartMapper {
    public List<CartItemResponseDTO> itemsToCartItemResponseDTO(List<CartItem> items){
        return items.stream().map(this::itemToProductResponseDTO).toList();
    }

    private CartItemResponseDTO itemToProductResponseDTO(CartItem cartItem) {
        return  CartItemResponseDTO.builder()
                .itemId(cartItem.getId())
                .nameProduct(cartItem.getProductName())
                .priceOriginalProduct(cartItem.getPriceProduct())
                .productImage(cartItem.getImage())
                .priceTotal(cartItem.getPriceItem())
                .quantity(cartItem.getQuantity())
                .build();
    }
}