package gestionInventario.com.service.implementations;

import gestionInventario.com.mapper.order.OrderItemMapper;
import gestionInventario.com.model.entity.CartItem;
import gestionInventario.com.model.entity.OrderEntity;
import gestionInventario.com.model.entity.OrderItem;
import gestionInventario.com.repository.IOrderItemRepository;
import gestionInventario.com.service.interfaces.IOrderItemService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Transactional
public class OrderItemImplService implements IOrderItemService {
    OrderItemMapper orderItemMapper;
    IOrderItemRepository orderItemRepository;

    @Override
    public void addOrderItems(Set<CartItem> cartItems, OrderEntity order) {
        Set<OrderItem> orderItems = orderItemMapper.itemCartsToOrderItems(cartItems, order);
        order.setOrdersItems(orderItems);
        orderItemRepository.saveAll(orderItems);
    }
}
