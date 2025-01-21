package gestionInventario.com.service.interfaces;

import gestionInventario.com.model.dto.purchasedProduct.PurchasedProductResponseDTO;
import gestionInventario.com.model.dto.purchasedProduct.CartItemRequestDTO;
import gestionInventario.com.model.dto.purchasedProduct.CartResponseDTO;

import java.util.List;

public interface IPurchasedProductService {
     void createCartItem(CartItemRequestDTO saleRequestDTO);
     List<CartResponseDTO> getAllCarts(Long idCustomer);
     List<CartResponseDTO> getCartsFinished(Long idCustomer);
     PurchasedProductResponseDTO getBuyCart(Long idCustomer);
     void deleteCart(Long idCustomer, Long idProduct);

}
