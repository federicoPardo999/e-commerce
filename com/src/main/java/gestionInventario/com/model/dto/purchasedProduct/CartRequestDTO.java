package gestionInventario.com.model.dto.purchasedProduct;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartRequestDTO {
    Long idUser;
    Long idCart;
}
