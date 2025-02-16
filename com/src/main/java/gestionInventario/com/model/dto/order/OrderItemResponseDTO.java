package gestionInventario.com.model.dto.order;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemResponseDTO {
    Long orderItemId;
    String productImage;
    String nameProduct;
    Integer quantity;
    Double priceOriginalProduct;
    Double priceTotal;
}
