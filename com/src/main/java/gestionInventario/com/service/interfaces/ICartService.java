package gestionInventario.com.service.interfaces;

import gestionInventario.com.model.dto.purchasedProduct.CartItemRequestDTO;
import gestionInventario.com.model.dto.purchasedProduct.CartResponseDTO;

public interface ICartService {
    void addItemToCart(CartItemRequestDTO cartItemRequestDTO, Long customerId);

    CartResponseDTO getCart(Long idUser);
}
