package gestionInventario.com.service.interfaces;

import gestionInventario.com.model.dto.purchasedProduct.CartItemRequestDTO;
import gestionInventario.com.model.dto.purchasedProduct.CartRequestDTO;
import gestionInventario.com.model.dto.purchasedProduct.CartResponseDTO;
import gestionInventario.com.model.entity.Cart;

public interface ICartService {
    void addItemToCart(CartItemRequestDTO cartItemRequestDTO, Long customerId);

    CartResponseDTO getCart(CartRequestDTO cartRequestDTO);
}
