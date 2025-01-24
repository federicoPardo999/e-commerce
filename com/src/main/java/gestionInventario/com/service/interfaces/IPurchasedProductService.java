package gestionInventario.com.service.interfaces;

import gestionInventario.com.model.dto.purchasedProduct.BuyDeleteDTO;
import gestionInventario.com.model.dto.purchasedProduct.PurchasedProductResponseDTO;
import gestionInventario.com.model.dto.purchasedProduct.PurchasedProductRequestDTO;
import gestionInventario.com.model.dto.purchasedProduct.CartResponseDTO;

import java.util.List;

public interface IPurchasedProductService {

    void createCartItem(PurchasedProductRequestDTO saleRequestDTO);

    List<CartResponseDTO> getAllCarts(Long idCustomer);

    List<CartResponseDTO> getCartsFinished(Long idCustomer);

    PurchasedProductResponseDTO getBuyCart(Long idCustomer);

    void deleteCart(BuyDeleteDTO buyDeleteDTO);

    void updateStock(PurchasedProductRequestDTO purchasedProductRequestDTO);

}
