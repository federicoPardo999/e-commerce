package gestionInventario.com.mapper.order;

import gestionInventario.com.model.dto.order.OrderItemResponseDTO;
import gestionInventario.com.model.entity.CartItem;
import gestionInventario.com.model.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderItemMapper {
    public Set<OrderItem> itemCartsToOrderItems(Set<CartItem> items){
        return items.stream().map(this::itemCartToOrderItem).collect(Collectors.toSet());
    }

    private OrderItem itemCartToOrderItem(CartItem cartItem) {
        return  OrderItem.builder()
                .productName(cartItem.getProductName())
                .priceProduct(cartItem.getPriceProduct())
                .image(cartItem.getImage())
                .orderItemPrice(cartItem.getPriceItem())
                .quantity(cartItem.getQuantity())
                .build();
    }

    public List<OrderItemResponseDTO> orderItemsToResponseDTO(Set<OrderItem> items){
        return items.stream().map(this::orderItemToResponseDTO).collect(Collectors.toList());
    }

    private OrderItemResponseDTO orderItemToResponseDTO(OrderItem orderItem) {
        return  OrderItemResponseDTO.builder()
                .orderItemId(orderItem.getId())
                .nameProduct(orderItem.getProductName())
                .priceOriginalProduct(orderItem.getPriceProduct())
                .productImage(orderItem.getImage())
                .priceTotal(orderItem.getOrderItemPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }
}
