package gestionInventario.com.http;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchasedProduct {
    String name;
    Double price;
    Integer stockToBuy;
    Double pricePurchased;
}