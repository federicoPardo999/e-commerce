package gestionInventario.com.model.dto.purchasedProduct;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateItemRequestDTO {
    //no es necesario tantas ids, se podria sacar la del producto
    Integer quantityBuyStock;
    Long productId;
    Long cartId;
    Long itemId;
}
