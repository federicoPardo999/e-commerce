package gestionInventario.com.service.interfaces;

import gestionInventario.com.model.dto.order.OrderResponseDTO;

import java.util.List;

public interface IOrderService {
    void createOrder(Long cartId, Long idCustomer);
    List<OrderResponseDTO> getOrderHistory(Long idCustomer);
}
