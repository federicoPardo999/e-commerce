package gestionInventario.com.service.interfaces;

import gestionInventario.com.model.dto.purchasedProduct.BuyDeleteDTO;
import gestionInventario.com.model.dto.purchasedProduct.PurchasedProductResponseDTO;
import gestionInventario.com.model.dto.purchasedProduct.CartItemRequestDTO;

public interface ICartService {

    void addCartItem(CartItemRequestDTO cartItemRequestDTO, Long customerId);

    PurchasedProductResponseDTO getCartFromUser(Long customerId);

    void cancelPurchased(BuyDeleteDTO buyDeleteDTO);

    void updateStock(CartItemRequestDTO cartItemRequestDTO, Long customerId);

}
