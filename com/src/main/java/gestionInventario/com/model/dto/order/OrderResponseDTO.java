package gestionInventario.com.model.dto.order;

import gestionInventario.com.model.entity.OrderItem;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponseDTO {
    Long orderId;
    String customerName;
    Set<OrderItem> orderItems;
    LocalDate orderDate;
    Double priceOrder;
}