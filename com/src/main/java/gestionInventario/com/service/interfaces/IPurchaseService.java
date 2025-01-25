package gestionInventario.com.service.interfaces;

import gestionInventario.com.model.dto.purchasedProduct.BuyDeleteDTO;
import gestionInventario.com.model.dto.purchasedProduct.PurchasedProductResponseDTO;
import gestionInventario.com.model.dto.purchasedProduct.PurchaseRequestDTO;

public interface IPurchaseService {

    void startPurchase(PurchaseRequestDTO saleRequestDTO);

    PurchasedProductResponseDTO getCartFromUser(Long idCustomer);

    void cancelPurchased(BuyDeleteDTO buyDeleteDTO);

    void updateStock(PurchaseRequestDTO purchaseRequestDTO);

}
