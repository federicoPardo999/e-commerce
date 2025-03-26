package gestionInventario.com.service.interfaces;

import gestionInventario.com.model.entity.CartItem;
import gestionInventario.com.model.entity.OrderEntity;
import gestionInventario.com.model.entity.OrderItem;

import java.util.List;
import java.util.Set;

public interface IOrderItemService {
    void addOrderItems(Set<CartItem> cartItems, OrderEntity order);
}
