package gestionInventario.com.service.interfaces;

import gestionInventario.com.model.dto.purchasedProduct.PurchasedProductResponseDTO;

public interface IOrderService {
     void createOrder(Long idCustomer);
     PurchasedProductResponseDTO getOrderHistory(Long idCustomer);
}
