package gestionInventario.com.service.interfaces;

import gestionInventario.com.http.BuyCartResponse;

public interface IOrderService {
     void createOrder(Long idCustomer);
     BuyCartResponse getPurchasedHistory(Long idCustomer);
}
