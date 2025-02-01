package gestionInventario.com.service.interfaces;

import gestionInventario.com.model.dto.purchasedProduct.PurchasedProductDTO;

import java.util.List;

public interface IOrderService {
     void createOrder(Long idCustomer);
     List<PurchasedProductDTO> getOrderHistory(Long idCustomer);
}
