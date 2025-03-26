package gestionInventario.com.model.dto.purchasedProduct;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponseDTO {
    Long itemId;
    String productImage;
    String nameProduct;
    Integer quantity;
    Double priceOriginalProduct;
    Double priceTotal;
}