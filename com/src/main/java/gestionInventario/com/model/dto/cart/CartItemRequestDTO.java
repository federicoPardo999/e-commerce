package gestionInventario.com.model.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequestDTO {
    Integer quantityBuyStock;
    Long productId;
    Long customerId;
}
