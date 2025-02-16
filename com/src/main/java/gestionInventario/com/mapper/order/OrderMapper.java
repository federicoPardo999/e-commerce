package gestionInventario.com.mapper.order;

import gestionInventario.com.model.dto.order.OrderResponseDTO;
import gestionInventario.com.model.entity.OrderEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public List<OrderResponseDTO> ordersToResponseDTO(Set<OrderEntity> orders){
        return orders.stream().map(this::orderToResponseDTO).collect(Collectors.toList());
    }

    private OrderResponseDTO orderToResponseDTO(OrderEntity order) {
        return  OrderResponseDTO.builder()
                .orderId(order.getId())
                .priceOrder(order.getPriceTotal())
                .customerName(order.getUser().getUsername())
                .orderItems(order.getOrdersItems())
                .orderDate(LocalDate.now())
                .build();
    }
}
