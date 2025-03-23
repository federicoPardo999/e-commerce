package gestionInventario.com.mapper.order;

import gestionInventario.com.model.dto.order.OrderResponseDTO;
import gestionInventario.com.model.entity.OrderEntity;
import gestionInventario.com.model.entity.OrderItem;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class OrderMapper {
    OrderItemMapper orderItemMapper;
    public List<OrderResponseDTO> ordersToResponseDTO(Set<OrderEntity> orders){
        return orders.stream().map(this::orderToResponseDTO).collect(Collectors.toList());
    }

    private OrderResponseDTO orderToResponseDTO(OrderEntity order) {
        return  OrderResponseDTO.builder()
                .orderId(order.getId())
                .priceOrder(order.getPriceTotal())
                .customerName(order.getUser().getUsername())
                .orderItems(orderItemMapper.orderItemsToResponseDTO(order.getOrdersItems()))
                .orderDate(LocalDate.now())
                .build();
    }
}