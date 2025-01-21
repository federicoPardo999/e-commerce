package gestionInventario.com.model.dto.purchasedProduct;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchasedProductResponseDTO {
    String nameCustomer;
    List<PurchasedProductDTO> products;
    Double totalSpent;
}
