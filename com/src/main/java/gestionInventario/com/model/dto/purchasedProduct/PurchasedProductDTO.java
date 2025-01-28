package gestionInventario.com.model.dto.purchasedProduct;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchasedProductDTO {
    Long idProduct;
    String name;
    Double price;
    Integer stockToBuy;
    Double pricePurchased;
}