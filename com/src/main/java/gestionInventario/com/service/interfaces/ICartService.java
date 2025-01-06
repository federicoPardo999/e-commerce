package gestionInventario.com.service.interfaces;

import gestionInventario.com.http.BuyCartResponse;
import gestionInventario.com.model.dto.cart.CartItemRequestDTO;
import gestionInventario.com.model.dto.cart.CartResponseDTO;

import java.util.List;

public interface ICartService {
     void createCartItem(CartItemRequestDTO saleRequestDTO);
     List<CartResponseDTO> getAllCarts(Long idCustomer);
     List<CartResponseDTO> getCartsFinished(Long idCustomer);
     BuyCartResponse getBuyCart(Long idCustomer);
     BuyCartResponse getPurchasedHistory(Long idCustomer);

}
