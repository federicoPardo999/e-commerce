package gestionInventario.com.model.dto.purchasedProduct;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemRequestDTO {
    Integer quantityBuyStock;
    Long productId;
}
